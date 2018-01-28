package com.example.nadya.mycalc;

import android.os.Build;
import android.support.annotation.RequiresApi;
import java.util.HashMap;

/**
 * Метод рекурсивного спуску для інтерпретації математичних виразів.
 */
public class MathParser {

    private static HashMap<String, Double> var;
    private static boolean isRad;

    public MathParser() {
        var = new HashMap<>();
        setVariable("pi",Math.PI);
        setVariable("e",Math.E);
        isRad=false;
    }


    /**
     * Вставити нову змінну
     * @param varName ім"я змінної
     * @param varValue значення змінної
     */
    public static void setVariable(String varName, Double varValue) {
        var.put(varName, varValue);
    }

    /**
     * замінити значення існуючої змінної
     * @param varName ім"я змінної
     * @param varValue значення змінної
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void replaceVariable(String varName, Double varValue) {
        var.replace(varName, varValue);
    }

    /**
     *
     * @param varName
     * @return Повертає значення змінної varName
     * @throws Exception генерація винятку за відсутності змінної
     */
    public Double getVariable(String varName) throws Exception {
        if(!var.containsKey(varName)) {
            throw new Exception("Error:Try get unexists "+
                    "variable '"+varName+"'" );
        }
        return var.get(varName);
    }

    /**
     * Парсинг математичного виразу
     * @param s математичний вираз
     * @return результат
     * @throws Exception
     */
    public double Parse(String s, boolean r) throws Exception {
        if(s.isEmpty())
            throw new Exception("Empty expression");
        isRad=r;
        Result result = binaryFunc(s);
        if (!result.rest.isEmpty())
            throw new Exception("Error: can't full parse \n "+
                    "rest: " + result.rest);
        return result.acc;
    }


    private Result binaryFunc(String s) throws Exception{

        Result cur;

        if(s.charAt(0) == '~'){
            cur = plusMinus(s.substring(1));

            cur.acc = ~ (int)cur.acc;
            return cur;
        }

        cur = plusMinus(s);
        double acc = cur.acc;

        cur.rest = skipSpaces(cur.rest);

        while(cur.rest.length() > 0){
            if(!(cur.rest.charAt(0) == '&' ||
                    cur.rest.charAt(0) == '|' ||
                    cur.rest.charAt(0) == '~')) break;

            char sign = cur.rest.charAt(0);
            String next = cur.rest.substring(1);
            cur = plusMinus(next);


            if(sign == '&')
                acc = (int)acc & (int)cur.acc;
            else
                acc = (int)acc | (int)cur.acc;
        }

        return new Result(acc,cur.rest);

    }

    private Result plusMinus(String s) throws Exception {

        Result cur = mulDiv(s);
        double acc = cur.acc;

        cur.rest = skipSpaces(cur.rest);

        while(cur.rest.length() > 0){
            if(!(cur.rest.charAt(0) == '+' || cur.rest.charAt(0) == '-'))
                break;

            char sign = cur.rest.charAt(0);
            String next = cur.rest.substring(1);

            cur = binaryFunc(next);

            if(sign == '+')
                acc+=cur.acc;
            else
                acc-=cur.acc;
        }
        return new Result(acc,cur.rest);
    }



    private Result mulDiv(String s) throws Exception{
        Result cur = exponentiation(s);
        double acc = cur.acc;

        cur.rest = skipSpaces(cur.rest);


        while(true){
            if(cur.rest.length() == 0)
                return cur;

            char sign = cur.rest.charAt(0);
            if(sign != '*' && sign != '/' && sign != '%' && sign != '\\')
                return cur;

            String next = cur.rest.substring(1);
            Result right = exponentiation(next);
            switch(sign){
                case '*':
                    acc*=right.acc;
                    break;
                case '/':
                    acc/=right.acc;
                    if (right.acc==0) throw new Exception("Division by 0!");
                    break;
                case '%':
                    if (right.acc!=0)
                        acc%=right.acc;
                    else
                        throw new Exception("Division by 0!");
                    break;
            }
            cur = new Result(acc,right.rest);
        }
    }


    private Result exponentiation(String s) throws Exception{
        Result cur = bracket(s);
        double acc = cur.acc;

        cur.rest = skipSpaces(cur.rest);

        while(true){

            if(cur.rest.length() == 0) return cur;
            if(cur.rest.charAt(0) !='^') break;

            String next = cur.rest.substring(1);
            cur = bracket(next);
            cur.acc = Math.pow(acc,cur.acc);
        }
        return cur;
    }


    private Result bracket(String s) throws Exception{

        s = skipSpaces(s);
        char zeroChar = s.charAt(0);
        if (zeroChar == '(') {
            Result r = binaryFunc(s.substring(1));
            if (!r.rest.isEmpty()) {
                r.rest = r.rest.substring(1);
            } else {
                throw new Exception("Expected closing bracket");
            }
            return r;
        }
        return functionVariable(s);
    }

    private Result functionVariable(String s) throws Exception{
        String f = "";
        int i = 0;
        // шукаємо назву функції
        // назва має починатись з букви
        while (i < s.length() && (Character.isLetter(s.charAt(i)) ||
                ( Character.isDigit(s.charAt(i)) && i > 0 ) )) {
            f += s.charAt(i);
            i++;
        }
        if (!f.isEmpty()) { // якщо знайдено
            if ( s.length() > i && s.charAt( i ) == '(') {
                // і наступний символ дужка - то це функція
                Result r = binaryFunc(s.substring(f.length()+1));

                if(!r.rest.isEmpty() && r.rest.charAt(0) == ','){

                } else {
                    r = closeBracket(r);
                    return processFunction(f, r);
                }
            } else {
                return new Result(getVariable(f), s.substring(f.length()));
            }
        }
        return num(s);
    }
    private Result closeBracket(Result r) throws Exception{
        if(!r.rest.isEmpty() && r.rest.charAt(0) ==')'){
            r.rest = r.rest.substring(1);
        } else
            throw new Exception("Expected closing bracket");
        return r;
    }

    private Result num(String s) throws Exception{
        int i = 0;
        boolean negative = false;
        // число може починатись із -
        if( s.charAt(0) == '-' ){
            negative = true;
            s = s.substring( 1 );
        }
        while (i < s.length() &&
                (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')) {
            i++;
        }
        if( i == 0 ){
            throw new Exception("can't get valid number in '" + s + "'" );
        }

        double dPart = Double.parseDouble(s.substring(0, i));
        if(negative) dPart = -dPart;
        String restPart = s.substring(i);

        return new Result(dPart, restPart);
    }

    private Result processFunction(String func, Result r) throws Exception{
        double ang;
        if (isRad)
            ang=r.acc;
        else
            ang=Math.toRadians(r.acc);
        switch (func) {
            case "sin":
                return new Result(Math.sin(ang), r.rest);
            case "sinh": // гіперболічний синус
                return new Result(Math.sinh(r.acc), r.rest);
            case "cos":
                return new Result(Math.cos(ang), r.rest);
            case "cosh": // гіперболічний косинус
                return new Result(Math.cosh(r.acc), r.rest);
            case "tan":
                return new Result(Math.tan(ang), r.rest);
            case "tanh": // гіперболічний тангенс
                return new Result(Math.tanh(r.acc), r.rest);
            case "sec": // секанс
                if (Math.cos(ang)!=0)
                    return new Result(1/Math.cos(ang), r.rest);
                else
                    throw new Exception("Argument of sec '" + Double.toString(r.acc) + "' is wrong!");
            case "cosec": // косеканс
                if (Math.sin(ang)!=0)
                    return new Result(1/Math.sin(ang), r.rest);
                else
                    throw new Exception("Argument of cosec '" + Double.toString(r.acc) + "' is wrong!");
            case "abs":
                return new Result(Math.abs(r.acc), r.rest);
            case "ln":
                if (r.acc>0)
                    return new Result(Math.log(r.acc), r.rest);
                else
                    throw new Exception("Argument of ln '" + Double.toString(r.acc) + "' is less or equal 0");
            case "lg": // десятковий логарифм
                if (r.acc>0)
                 return new Result(Math.log10(r.acc), r.rest);
                else
                    throw new Exception("Argument of lg '" + Double.toString(r.acc) + "' is less or equal 0");
            case "sqrt":
                if (r.acc>=0)
                    return new Result(Math.sqrt(r.acc), r.rest);
                else
                    throw new Exception("Argument of sqrt '" + Double.toString(r.acc) + "' is less than 0");
            case "fact":
                long tr = Math.round(r.acc);
                if (tr==r.acc && tr>-1)
                    return new Result((double)fact(r.acc), r.rest);
                else
                    throw new Exception("Argument of factorial '" + Double.toString(r.acc) + "' is not correct");
            default:
                throw new Exception("Function '" + func + "' is not defined");
        }
    }


    public static double fact(double num) {
        if (num == 0||num == 1) return 1;
        return num*fact(num-1);
    }

    private String skipSpaces(String s){
        return s.trim();
    }


    private class Result {
        public double acc; // поле-змінна для накопичення результату
        public String rest; // решта рядка, який ще не оброблено
        public Result(double v, String r) {
            this.acc = v;
            this.rest = r;
        }
    }
}

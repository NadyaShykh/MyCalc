package com.example.nadya.mycalc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import java.lang.reflect.Method;


public class Utils {
    private static int sTheme;
    public final static int THEME_LIGTH = 0;

    public static void loadTheme(Activity activity, String styleCalc)
    {
        if (styleCalc.equals("Ligth"))
            activity.setTheme(R.style.AppTheme);
        else
            activity.setTheme(R.style.CustomTheme);
    }

    public static void changeToTheme(Activity activity, int theme)
    {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static int getThemeId(Activity activity) {
        try {
            Class<?> wrapper = Context.class;
            Method method = wrapper.getMethod("getThemeResId");
            method.setAccessible(true);
            return (Integer) method.invoke(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String autoCorrection(String ex) {
        char c='&';
        if (!ex.equals("")){
            c=ex.charAt(ex.length() - 1);
        }
        if ("+-*÷%(".indexOf(c) > -1)
            ex=removeCharAt(ex,ex.length() - 1);
        int brCount1 = ex.split("\\(", -1).length - 1;
        int brCount2 = ex.split("\\)", -1).length - 1;
        for (int i=0, n=brCount1-brCount2; i<n; i++)
            ex+=")";
        return ex;
    }

    public static  String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    public static String funcCorrect(String ex) {
        ex=ex.replaceAll("÷","/");
        ex=ex.replaceAll("√","sqrt");
        ex=ex.replaceAll("π","pi");
        char c='1';
        int ind, ident=ex.indexOf('!');
        while (ident > -1){
            String tmp=ex.substring(0,ident);
            c=tmp.charAt(tmp.length() - 1);
            ex=Utils.removeCharAt(ex, ident);
            if (c==')')
            {
                ind=tmp.lastIndexOf('(');
                ex=ex.substring(0, ind)+"fact"+ex.substring(ind);
            }
            else
            {
                ind=0;
                for (int i=tmp.length()-1; i>=0; i--){
                    if (!Character.isDigit(tmp.charAt(i)))
                    {
                        if (tmp.charAt(i)!='.')
                            ind=i+1;
                        break;
                    }
                }
                ex=ex.substring(0, ident)+")"+ex.substring(ident);
                ex=ex.substring(0, ind)+"fact("+ex.substring(ind);
            }
            ident=ex.indexOf('!');
        }
        return ex;
    }

    public static String getBr(String s) {
        if (s.equals("")) return "(";
        else {
            int brCount1 = s.split("\\(", -1).length - 1;
            int brCount2 = s.split("\\)", -1).length - 1;
            char c=s.charAt(s.length() - 1);
            if ("+-*÷%√".indexOf(c) > -1) return s+"(";
            if (Character.isDigit(c) && brCount1>brCount2) return s+")";
            if (")!eπx".indexOf(c) > -1 && brCount1>brCount2) return s + ")";
            if (c=='(') return s + "(";
        }
        return s;
    }

    public static String getDot(String s) {
        char c=s.charAt(s.length() - 1);
        if (Character.isDigit(c))
        {
            //перевірити чи нема крапки в числі
            if (s.lastIndexOf('.')<0) // крапки в рядку нема
                return s + ".";
            else
            {
                if ((s.lastIndexOf('+')>s.lastIndexOf('.'))||(s.lastIndexOf('-')>s.lastIndexOf('.'))
                        ||(s.lastIndexOf('*')>s.lastIndexOf('.'))||(s.lastIndexOf('÷')>s.lastIndexOf('.'))
                        ||(s.lastIndexOf('%')>s.lastIndexOf('.')))
                    return s + ".";
            }
        }
        return s;
    }

    public static String getFunc(String s, int f) {
        char c='&';
        if (!s.equals("")){
            c=s.charAt(s.length() - 1);
        }
        if (s.equals("")||("+-*÷%(".indexOf(c) > -1))
        {
            switch(f) {
                case 1: return s + "10^(";

                case 2: return s + "1/";

                case 3: return s + "√(";

                case 4: return s + "sin(";

                case 5: return s + "cos(";

                case 6: return s + "tan(";

                case 7: return s + "ln(";

                case 8: return s + "lg(";

                case 11: return s + "sec(";

                case 12: return s + "cosec(";

                case 13: return s + "sinh(";

                case 14: return s + "cosh(";

                case 15: return s + "tanh(";

                case 17: return s + "2^(";

                case 18: return s + "e^(";

                case 19: return s + "abs(";

            }
        }
        if (Character.isDigit(c)||c==')'||c=='x'||c=='e'||c=='π')
        {
            switch(f) {
                case 9: return s + "^(2)";

                case 10: return s + "^(";

                case 16: return s + "!";
            }
        }
        return s;
    }

    public static String getSymb(String s, char symb) {
        char c;
        c = 'a';
        if (s.equals(""))
        {
            if (Character.isDigit(symb)||"-()eπx".indexOf(symb) > -1) // дописати пі
                return s + symb;
        }
        else
        {
            c = s.charAt(s.length() - 1);
            if ("+-*÷%".indexOf(symb) > -1) // вивід знаку операції, тут включити факторіал, e, pi
            {
                if (Character.isDigit(c)||c=='!'||c=='e'||c=='π'||c=='x')
                    return s + symb;
                else
                {
                    if ("(".indexOf(c) > -1 && symb == '-')
                        return s + symb;
                    if (")".indexOf(c) > -1)
                        return s + symb;
                }
            }
            else {
                if (Character.isDigit(c)&&"eπx".indexOf(symb) == -1) return s + symb;
                if (Character.isDigit(c)&&Character.isDigit(symb)) return s + symb;
                if (Character.isDigit(symb)&&"eπx".indexOf(c) == -1) return s + symb;
                if ("+-*÷%(".indexOf(c) > -1) return s + symb;
            }
        }
        return s;
    }


}

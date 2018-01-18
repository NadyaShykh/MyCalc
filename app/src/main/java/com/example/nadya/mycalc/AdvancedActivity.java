package com.example.nadya.mycalc;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class AdvancedActivity extends AppCompatActivity implements View.OnClickListener {

    Button btOne, btTwo, btThree, btFour, btFive, btSix, btSeven, btEight, btNine, btZero;
    Button btPlus, btMinus, btMulti, btDiv, btEqual, btClear, btDot, btAbs, btBack, btBrace;
    Button btExp, btPi, btSin, btSqrt, btSqr, btPow, btCos, btTan, btLn, btLg;
    Button btSec, btCosec, btSinH, btCosH, btTanH, btFact, btFrac, btTwoDeg, btDec, btExpDeg;

    TextView tvLCD;
    TextView etLCD;
    boolean isRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
        isRes=false;

        int[] bt_ids;
        Button[] bt_arr;

        bt_ids = new int[]{R.id.buttonOne, R.id.buttonTwo, R.id.buttonThree, R.id.buttonFour, R.id.buttonFive,
                R.id.buttonSix, R.id.buttonSeven, R.id.buttonEight, R.id.buttonNine, R.id.buttonZero,
                R.id.buttonAdd, R.id.buttonSubstract,R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonEqual,
                R.id.buttonClear, R.id.buttonDot, R.id.buttonAbs, R.id.buttonBack, R.id.buttonBraces, R.id.buttonExp, R.id.buttonPi,
                R.id.buttonSin, R.id.buttonSqrt, R.id.buttonSqr, R.id.buttonPow, R.id.buttonCos, R.id.buttonTan, R.id.buttonLn, R.id.buttonLg};

        bt_arr = new Button[]{btOne, btTwo, btThree, btFour, btFive, btSix, btSeven, btEight, btNine, btZero,
                btPlus, btMinus, btMulti, btDiv, btEqual, btClear, btDot, btAbs, btBack, btBrace, btExp, btPi,
                btSin, btSqrt, btSqr, btPow, btCos, btTan, btLn, btLg};

        for (int i = 0, n = bt_arr.length; i < n; i++)
        {
            bt_arr[i] = (Button) findViewById(bt_ids[i]);
            bt_arr[i].setOnClickListener(this);
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            btSec = (Button) findViewById(R.id.buttonSec);
            btCosec = (Button) findViewById(R.id.buttonCosec);
            btSinH = (Button) findViewById(R.id.buttonSinH);
            btCosH = (Button) findViewById(R.id.buttonCosH);
            btTanH = (Button) findViewById(R.id.buttonTanH);
            btFact = (Button) findViewById(R.id.buttonFact);
            btFrac = (Button) findViewById(R.id.buttonFrac);
            btTwoDeg = (Button) findViewById(R.id.buttonTwoDeg);
            btDec = (Button) findViewById(R.id.buttonDecDeg);
            btExpDeg = (Button) findViewById(R.id.buttonExpDeg);

            btSec.setOnClickListener(this);
            btCosec.setOnClickListener(this);
            btSinH.setOnClickListener(this);
            btCosH.setOnClickListener(this);
            btTanH.setOnClickListener(this);
            btFact.setOnClickListener(this);
            btFrac.setOnClickListener(this);
            btTwoDeg.setOnClickListener(this);
            btDec.setOnClickListener(this);
            btExpDeg.setOnClickListener(this);
        }

        tvLCD = (TextView) findViewById(R.id.infoTextView);
        etLCD = (TextView) findViewById(R.id.editText);

        tvLCD.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.getItem(0).setTitle("Simply");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.itemAdv:
                finish();
                return true;
            //дописати решту

            case R.id.itemHist:
                Intent intent = new Intent(AdvancedActivity.this, HistoryActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonOne:
                ClickSymb('1');
                break;

            case R.id.buttonTwo:
                ClickSymb('2');
                break;

            case R.id.buttonThree:
                ClickSymb('3');
                break;

            case R.id.buttonFour:
                ClickSymb('4');
                break;

            case R.id.buttonFive:
                ClickSymb('5');
                break;

            case R.id.buttonSix:
                ClickSymb('6');
                break;

            case R.id.buttonSeven:
                ClickSymb('7');
                break;

            case R.id.buttonEight:
                ClickSymb('8');
                break;

            case R.id.buttonNine:
                ClickSymb('9');
                break;

            case R.id.buttonZero:
                ClickSymb('0');
                break;

            case R.id.buttonDot:
                ClickDot();
                break;

            case R.id.buttonAdd:
                ClickSymb('+');
                break;

            case R.id.buttonDivide:
                ClickSymb('/');
                break;

            case R.id.buttonMultiply:
                ClickSymb('*');
                break;

            case R.id.buttonSubstract:
                ClickSymb('-');
                break;

            /*case R.id.buttonPers:
                ClickSymb('%');
                break;*/

            case R.id.buttonClear:
                if (etLCD.getText().toString().equals(""))
                    tvLCD.setText("");
                etLCD.setText("");
                isRes=false;
                break;

            case R.id.buttonBack:
                if (!etLCD.getText().toString().equals(""))
                    etLCD.setText(etLCD.getText().toString().substring(0, etLCD.getText().toString().length() - 1));
                break;

            case R.id.buttonBraces:
                ClickBr();
                break;

            case R.id.buttonEqual:
                Calculate();
                break;

            case R.id.infoTextView:
                String s = etLCD.getText().toString();
                char c = s.charAt(s.length() - 1);
                if ("+-*/%".indexOf(c) > -1)
                    etLCD.setText(etLCD.getText().toString()+tvLCD.getText().toString());
                else
                    etLCD.setText(tvLCD.getText().toString());
                isRes=false;
                break;

            case R.id.buttonExp:
                ClickSymb('e');
                break;

            case R.id.buttonPi:
                ClickSymb('π');
                break;

            case R.id.buttonSin:
                ClickFunc(4);
                break;

            case R.id.buttonSqrt:
                ClickFunc(3);
                break;

            case R.id.buttonSqr:
                ClickFunc(9);
                break;

            case R.id.buttonPow:
                ClickFunc(10);
                break;

            case R.id.buttonCos:
                ClickFunc(5);
                break;

            case R.id.buttonTan:
                ClickFunc(6);
                break;

            case R.id.buttonLn:
                ClickFunc(7);
                break;

            case R.id.buttonLg:
                ClickFunc(8);
                break;

                // R.id.buttonLn, R.id.buttonLg
        }
    }

    private void ClickSymb(char symb){
        if (isRes) {
            if (etLCD.getText().toString().equals("Infinity")||etLCD.getText().toString().equals("NaN"))
                etLCD.setText("");
            else {
                if (Character.isDigit((char) symb)) {
                    tvLCD.setText(etLCD.getText().toString());
                    etLCD.setText("");
                }
            }
            isRes=false;
        }
        String s = etLCD.getText().toString();
        char c;
        c = 'a';
        if (s.equals(""))
        {
            if (Character.isDigit(symb)||"-()eπ".indexOf(symb) > -1) // дописати пі
                etLCD.setText(etLCD.getText().toString() + symb);
        }
        else
        {
            c = s.charAt(s.length() - 1);
            if ("+-*/%".indexOf(symb) > -1) // вивід знаку операції, тут включити факторіал, e, pi
            {
                if (Character.isDigit(c)||c=='!'||c=='e'||c=='π')
                    etLCD.setText(etLCD.getText().toString() + symb);
                else
                {
                    if ("(".indexOf(c) > -1 && symb == '-')
                        etLCD.setText(etLCD.getText().toString() + symb);
                    if (")".indexOf(c) > -1)
                        etLCD.setText(etLCD.getText().toString() + symb);
                }
            }
            else {
                String ex=etLCD.getText().toString() + symb;
                ex=ex.replaceAll("eπ","e");
                ex=ex.replaceAll("ππ","π");
                ex=ex.replaceAll("ee","e");
                ex=ex.replaceAll("πe","π");
                etLCD.setText(ex);
            }
        }
    }

    private void ClickBr(){
        AddRes();
        String s = etLCD.getText().toString();
        if (s.equals(""))
            etLCD.setText("(");
        else {
            int brCount1 = s.split("\\(", -1).length - 1;
            int brCount2 = s.split("\\)", -1).length - 1;
            char c=s.charAt(s.length() - 1);
            if ("+-*/%".indexOf(c) > -1) etLCD.setText(etLCD.getText().toString() + "(");
            if (Character.isDigit(c)&&brCount1>brCount2) etLCD.setText(etLCD.getText().toString() + ")");
            if (")!eπ".indexOf(c) > -1&&brCount1>brCount2) etLCD.setText(etLCD.getText().toString() + ")"); // дописати пі
            if (c=='(') etLCD.setText(etLCD.getText().toString() + "(");
        }
    }

    private void ClickDot(){
        String s = etLCD.getText().toString();
        if (!s.equals(""))
        {
            if (isRes)
            {
                if (s.indexOf('.') < 0) etLCD.setText(etLCD.getText().toString() + ".");
                isRes=false;
            }
            else
            {
                char c=s.charAt(s.length() - 1);
                if (Character.isDigit(c))
                {
                    //перевірити чи нема крапки в числі
                    if (s.lastIndexOf('.')<0) // крапки в рядку нема
                        etLCD.setText(etLCD.getText().toString() + ".");
                    else
                    {
                        if ((s.lastIndexOf('+')>s.lastIndexOf('.'))||(s.lastIndexOf('-')>s.lastIndexOf('.'))
                                ||(s.lastIndexOf('*')>s.lastIndexOf('.'))||(s.lastIndexOf('/')>s.lastIndexOf('.'))
                                ||(s.lastIndexOf('%')>s.lastIndexOf('.')))
                            etLCD.setText(etLCD.getText().toString() + ".");
                    }
                }
            }
        }
    }

    private void ClickFunc(int f){
        if (!(f==4||f==5)) AddRes();// замінити нумерацію
        String s = etLCD.getText().toString();
        char c='1';
        if (!s.equals("")){
            c=s.charAt(s.length() - 1);
        }
        if (s.equals("")||("+-*/%".indexOf(c) > -1))
        {
            switch(f) {
                case 1:
                    etLCD.setText(etLCD.getText().toString() + "10^(");
                    break;

                case 2:
                    etLCD.setText(etLCD.getText().toString() + "1/");
                    break;

                case 3:
                    etLCD.setText(etLCD.getText().toString() + "√");
                    break;

                case 4:
                    etLCD.setText(etLCD.getText().toString() + "sin(");
                    break;

                case 5:
                    etLCD.setText(etLCD.getText().toString() + "cos(");
                    break;

                case 6:
                    etLCD.setText(etLCD.getText().toString() + "tan(");
                    break;

                case 7:
                    etLCD.setText(etLCD.getText().toString() + "cos(");
                    break;

                case 8:
                    etLCD.setText(etLCD.getText().toString() + "tan(");
                    break;

            }
        }
        if (Character.isDigit(c)||c==')')
        {
            switch(f) {
                case 9:
                    etLCD.setText(etLCD.getText().toString() + "^(2)");
                    break;

                case 10:
                    etLCD.setText(etLCD.getText().toString() + "^(");
                    break;

                /*case :
                    etLCD.setText(etLCD.getText().toString() + "!");
                    break;*/
            }
        }
    }

    private void AddRes(){
        if (isRes) {
            if (etLCD.getText().toString().equals("Infinity")||etLCD.getText().toString().equals("NaN"))
                etLCD.setText("");
            else {
                tvLCD.setText(etLCD.getText().toString());
                etLCD.setText("");
            }
            isRes=false;
        }
    }


    private void Calculate(){
        MathParser parser = new MathParser();
        String expression = etLCD.getText().toString();
        expression=FuncCorrect(expression);
        try{
            double res =parser.Parse(expression);
            long r = Math.round(res);
            if (r==res) {
                saveFile(etLCD.getText().toString()+"="+Integer.toString((int) r));
                etLCD.setText(Integer.toString((int) r));
            } else
            {
                if (Double.toString(res).equals("Infinity")||Double.toString(res).equals("NaN"))
                    etLCD.setText(Double.toString(res));
                else {
                    String s=etLCD.getText().toString()+"=";
                    DecimalFormat df = new DecimalFormat("#.##########");
                    df.setRoundingMode(RoundingMode.CEILING);
                    etLCD.setText(df.format(res).replace(',', '.'));
                    saveFile(s+etLCD.getText().toString());
                }
            }
            isRes=true;
        } catch(Exception e){
            Toast.makeText(getApplicationContext(),
                    "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private String FuncCorrect(String ex) {
        ex=ex.replaceAll("√","sqrt");
        ex=ex.replaceAll("π","pi");
        char c='1';
        int ind, ident=ex.indexOf('!');
        while (ident > -1){
            String tmp=ex.substring(0,ident);
            c=tmp.charAt(tmp.length() - 1);
            ex=removeCharAt(ex, ident);
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

    private String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1); // Возвращаем подстроку s, которая начиная с нулевой позиции переданной строки (0) и заканчивается позицией символа (pos), который мы хотим удалить, соединенную с другой подстрокой s, которая начинается со следующей позиции после позиции символа (pos + 1), который мы удаляем, и заканчивается последней позицией переданной строки.
    }

    private void saveFile(String text) {
        try {
            String line="";
            File f = new File(getFilesDir() + "/hist.txt");
            if (f.exists())
                line=readFile();
            line="\n"+text+"\n"+line;
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput("hist.txt", MODE_WORLD_READABLE)));
            bw.write(line);
            bw.flush();
            bw.close();

        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private String readFile() {
        try {
            InputStream inputStream = openFileInput("hist.txt");

            if (inputStream != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        openFileInput("hist.txt")));
                String line;
                StringBuilder builder = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    builder.append(line + "\n");
                }

                inputStream.close();
                return builder.toString();
            }
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
        }
        return "";
    }
}

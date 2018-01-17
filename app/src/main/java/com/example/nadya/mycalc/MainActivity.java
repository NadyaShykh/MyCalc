package com.example.nadya.mycalc;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.io.BufferedWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import android.view.Menu;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button btOne, btTwo, btThree, btFour, btFive, btSix, btSeven, btEight, btNine, btZero;
    Button btPlus, btMinus, btMulti, btDiv, btEqual, btClear, btDot, btPer, btBack, btBrace;
    Button btDec, btFrac, btSqr,  btSqrt, btFact;
    TextView tvLCD;
    public TextView etLCD;
    boolean isRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isRes=false;

        int[] bt_ids;
        Button[] bt_arr;

        bt_ids = new int[]{R.id.buttonOne, R.id.buttonTwo, R.id.buttonThree, R.id.buttonFour, R.id.buttonFive,
                R.id.buttonSix, R.id.buttonSeven, R.id.buttonEight, R.id.buttonNine, R.id.buttonZero,
                R.id.buttonAdd, R.id.buttonSubstract,R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonEqual,
                R.id.buttonClear, R.id.buttonDot, R.id.buttonPers, R.id.buttonBack, R.id.buttonBraces};

        bt_arr = new Button[]{btOne, btTwo, btThree, btFour, btFive, btSix, btSeven, btEight, btNine, btZero,
                 btPlus, btMinus, btMulti, btDiv, btEqual, btClear, btDot, btPer, btBack, btBrace};

        for (int i = 0, n = bt_arr.length; i < n; i++)
        {
            bt_arr[i] = (Button) findViewById(bt_ids[i]);
            bt_arr[i].setOnClickListener(this);
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            btDec = (Button) findViewById(R.id.buttonDecDeg);
            btFrac = (Button) findViewById(R.id.buttonFrac);
            btSqr = (Button) findViewById(R.id.buttonSqr);
            btSqrt = (Button) findViewById(R.id.buttonSqrt);
            btFact = (Button) findViewById(R.id.buttonFact);
            btDec.setOnClickListener(this);
            btFrac.setOnClickListener(this);
            btSqr.setOnClickListener(this);
            btSqrt.setOnClickListener(this);
            btFact.setOnClickListener(this);
        }

        tvLCD = (TextView) findViewById(R.id.infoTextView);
        etLCD = (TextView) findViewById(R.id.editText);

        tvLCD.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch(id){
            case R.id.itemAdv:
                intent = new Intent(MainActivity.this, AdvancedActivity.class);
                startActivity(intent);
                return true;

            case R.id.itemHist:
                intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
                return true;
            //дописати решту
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

            case R.id.buttonPers:
                ClickSymb('%');
                break;

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
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            switch(v.getId()) {
                case R.id.buttonDecDeg:
                    ClickFunc(1);
                    break;

                case R.id.buttonFrac:
                    ClickFunc(2);
                    break;

                case R.id.buttonSqrt:
                    ClickFunc(3);
                    break;

                case R.id.buttonSqr:
                    ClickFunc(4);
                    break;

                case R.id.buttonFact:
                    ClickFunc(5);
                    break;
            }
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
            if (Character.isDigit(symb)||"-()".indexOf(symb) > -1)
                etLCD.setText(etLCD.getText().toString() + symb);
        }
        else
        {
            c = s.charAt(s.length() - 1);
            if ("+-*/%".indexOf(symb) > -1) // вивід знаку операції, тут включити факторіал
            {
                if (Character.isDigit(c)||c=='!')
                    etLCD.setText(etLCD.getText().toString() + symb);
                else
                {
                    if ("(".indexOf(c) > -1 && symb == '-')
                        etLCD.setText(etLCD.getText().toString() + symb);
                    if (")".indexOf(c) > -1)
                        etLCD.setText(etLCD.getText().toString() + symb);
                }
            }
            else  etLCD.setText(etLCD.getText().toString() + symb);
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
            if (c==')'&&brCount1>brCount2) etLCD.setText(etLCD.getText().toString() + ")");
            if (c=='(') etLCD.setText(etLCD.getText().toString() + "(");
            if (c=='!'&&brCount1>brCount2) etLCD.setText(etLCD.getText().toString() + ")");
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
        if (!(f==4||f==5)) AddRes();
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

            }
        }
        if (Character.isDigit(c)||c==')')
        {
            switch(f) {
                case 4:
                    etLCD.setText(etLCD.getText().toString() + "^(2)");
                    break;

                case 5:
                    etLCD.setText(etLCD.getText().toString() + "!");
                    break;
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
            String line;
            line=readFile();
            if (line.equals(""))
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
            return "";//Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
        }
        return "";
    }
}

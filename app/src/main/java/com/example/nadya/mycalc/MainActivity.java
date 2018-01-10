package com.example.nadya.mycalc;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import com.example.nadya.mycalc.MathParser;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button btOne, btTwo, btThree, btFour, btFive, btSix, btSeven, btEight, btNine, btZero;
    Button btPlus, btMinus, btMulti, btDiv, btEqual, btClear, btDot, btPer, btBack, btBrace;
    TextView tvLCD, etLCD;
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

        /*btOne = (Button) findViewById(R.id.buttonOne);
        btTwo = (Button) findViewById(R.id.buttonTwo);
        btThree = (Button) findViewById(R.id.buttonThree);
        btFour = (Button) findViewById(R.id.buttonFour);
        btFive = (Button) findViewById(R.id.buttonFive);
        btSix = (Button) findViewById(R.id.buttonSix);
        btSeven = (Button) findViewById(R.id.buttonSeven);
        btEight = (Button) findViewById(R.id.buttonEight);
        btNine = (Button) findViewById(R.id.buttonNine);
        btZero = (Button) findViewById(R.id.buttonZero);
        btPlus = (Button) findViewById(R.id.buttonAdd);
        btMinus = (Button) findViewById(R.id.buttonSubstract);
        btMulti = (Button) findViewById(R.id.buttonMultiply);
        btDiv = (Button) findViewById(R.id.buttonDivide);
        btEqual = (Button) findViewById(R.id.buttonEqual);
        btClear = (Button) findViewById(R.id.buttonClear);
        btDot = (Button) findViewById(R.id.buttonDot);
        btPer = (Button) findViewById(R.id.buttonPers);
        btBack = (Button) findViewById(R.id.buttonBack);
        btBrace = (Button) findViewById(R.id.buttonBraces);*/
        tvLCD = (TextView) findViewById(R.id.infoTextView);
        etLCD = (TextView) findViewById(R.id.editText);

        /*btOne.setOnClickListener(this);
        btTwo.setOnClickListener(this);
        btThree.setOnClickListener(this);
        btFour.setOnClickListener(this);
        btFive.setOnClickListener(this);
        btSix.setOnClickListener(this);
        btSeven.setOnClickListener(this);
        btEight.setOnClickListener(this);
        btNine.setOnClickListener(this);
        btZero.setOnClickListener(this);
        btPlus.setOnClickListener(this);
        btMinus.setOnClickListener(this);
        btMulti.setOnClickListener(this);
        btDiv.setOnClickListener(this);
        btClear.setOnClickListener(this);
        btEqual.setOnClickListener(this);
        btDot.setOnClickListener(this);
        btPer.setOnClickListener(this);
        btBack.setOnClickListener(this);
        btBrace.setOnClickListener(this);*/
        tvLCD.setOnClickListener(this);
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
                //дописати очистку змінних
                break;

            case R.id.buttonBack:
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
            if ("+-*/%".indexOf(symb) > -1)
            {
                if (Character.isDigit(c))
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
        String s = etLCD.getText().toString();
        if (s.equals(""))
            etLCD.setText("(");
        else {
            int brCount1 = s.split("\\(", -1).length - 1;
            int brCount2 = s.split("\\)", -1).length - 1;
            char c=s.charAt(s.length() - 1);
            if (brCount1==0) etLCD.setText(etLCD.getText().toString() + "(");
            else
            {
                if ("+-*/%".indexOf(c) > -1) etLCD.setText(etLCD.getText().toString() + "(");
                if (Character.isDigit(c)&&brCount1>brCount2) etLCD.setText(etLCD.getText().toString() + ")");
                if (c==')'&&brCount1>brCount2) etLCD.setText(etLCD.getText().toString() + ")");
            }

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

    private void Calculate(){
        MathParser parser = new MathParser();
        String expression = etLCD.getText().toString();

            try{
                double res =parser.Parse(expression);
                long r = Math.round(res);
                if (r==res) {
                    etLCD.setText(Integer.toString((int) r));
                } else
                    etLCD.setText(Double.toString(res));
                isRes=true;
            } catch(Exception e){
                System.out.println(e.getMessage());
            }

    }
}
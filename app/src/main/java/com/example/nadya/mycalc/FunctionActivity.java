package com.example.nadya.mycalc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
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
import java.text.DecimalFormat;


public class FunctionActivity extends AppCompatActivity implements View.OnClickListener {
    Button btOne, btTwo, btThree, btFour, btFive, btSix, btSeven, btEight, btNine, btZero;
    Button btPlus, btMinus, btMulti, btDiv, btEqual, btClear, btDot, btAbs, btBack, btBrace;
    Button btExp, btPi, btSin, btSqrt, btX, btPow, btCos, btTan, btLn, btLg;
    Button btSec, btCosec, btSinH, btCosH, btTanH, btSqr, btFrac, btTwoDeg, btDec, btExpDeg;

    TextView tvLCD, etLCD, tvDegRad, tvVar;

    boolean isRes;
    SharedPreferences sp;
    Boolean correct, up;
    String styleCalc;
    static String sThemeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        correct = sp.getBoolean("corr", true);
        styleCalc = sp.getString("app_style", "Ligth");
        Utils.loadTheme(this, styleCalc);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        isRes=false;
        up=true;

        int[] bt_ids;
        Button[] bt_arr;

        bt_ids = new int[]{R.id.buttonOne, R.id.buttonTwo, R.id.buttonThree, R.id.buttonFour, R.id.buttonFive,
                R.id.buttonSix, R.id.buttonSeven, R.id.buttonEight, R.id.buttonNine, R.id.buttonZero,
                R.id.buttonAdd, R.id.buttonSubstract,R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonEqual,
                R.id.buttonClear, R.id.buttonDot, R.id.buttonAbs, R.id.buttonBack, R.id.buttonBraces, R.id.buttonExp, R.id.buttonPi,
                R.id.buttonSin, R.id.buttonSqrt, R.id.buttonX, R.id.buttonPow, R.id.buttonCos, R.id.buttonTan, R.id.buttonLn, R.id.buttonLg};

        bt_arr = new Button[]{btOne, btTwo, btThree, btFour, btFive, btSix, btSeven, btEight, btNine, btZero,
                btPlus, btMinus, btMulti, btDiv, btEqual, btClear, btDot, btAbs, btBack, btBrace, btExp, btPi,
                btSin, btSqrt, btX, btPow, btCos, btTan, btLn, btLg};

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
            btSqr = (Button) findViewById(R.id.buttonSqr);
            btFrac = (Button) findViewById(R.id.buttonFrac);
            btTwoDeg = (Button) findViewById(R.id.buttonTwoDeg);
            btDec = (Button) findViewById(R.id.buttonDecDeg);
            btExpDeg = (Button) findViewById(R.id.buttonExpDeg);

            btSec.setOnClickListener(this);
            btCosec.setOnClickListener(this);
            btSinH.setOnClickListener(this);
            btCosH.setOnClickListener(this);
            btTanH.setOnClickListener(this);
            btSqr.setOnClickListener(this);
            btFrac.setOnClickListener(this);
            btTwoDeg.setOnClickListener(this);
            btDec.setOnClickListener(this);
            btExpDeg.setOnClickListener(this);
        }

        tvLCD = (TextView) findViewById(R.id.infoTextView);
        etLCD = (TextView) findViewById(R.id.editText);
        tvDegRad = (TextView) findViewById(R.id.infoDegRad);
        tvVar = (TextView) findViewById(R.id.editVar);

        tvLCD.setOnClickListener(this);
        etLCD.setOnClickListener(this);
        tvDegRad.setOnClickListener(this);
        tvVar.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        correct = sp.getBoolean("corr", true);
        styleCalc = sp.getString("app_style", "Ligth");
        if (styleCalc.equals("Ligth")) sThemeId="2131689478";
        else sThemeId="2131689645";
        if (!Integer.toString(Utils.getThemeId(this)).equals(sThemeId))
            Utils.changeToTheme(this, Utils.THEME_LIGTH);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.getItem(0).setTitle("Simply");
        menu.getItem(1).setTitle("Advanced");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch(id){
            case R.id.itemAdv:
                intent = new Intent(FunctionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.itemFunc:
                intent = new Intent(FunctionActivity.this, AdvancedActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.itemHist:
                intent = new Intent(FunctionActivity.this, HistoryActivity.class);
                startActivity(intent);
                return true;

            case R.id.itemSet:
                intent = new Intent(FunctionActivity.this, PrefActivity.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("result", etLCD.getText().toString());
        outState.putString("memory", tvLCD.getText().toString());
        outState.putString("angle", tvDegRad.getText().toString());
        outState.putString("var", tvVar.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        etLCD.setText(savedInstanceState.getString("result"));
        tvLCD.setText(savedInstanceState.getString("memory"));
        tvDegRad.setText(savedInstanceState.getString("angle"));
        tvVar.setText(savedInstanceState.getString("var"));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonOne:
                clickSymb('1');
                break;

            case R.id.buttonTwo:
                clickSymb('2');
                break;

            case R.id.buttonThree:
                clickSymb('3');
                break;

            case R.id.buttonFour:
                clickSymb('4');
                break;

            case R.id.buttonFive:
                clickSymb('5');
                break;

            case R.id.buttonSix:
                clickSymb('6');
                break;

            case R.id.buttonSeven:
                clickSymb('7');
                break;

            case R.id.buttonEight:
                clickSymb('8');
                break;

            case R.id.buttonNine:
                clickSymb('9');
                break;

            case R.id.buttonZero:
                clickSymb('0');
                break;

            case R.id.buttonDot:
                clickDot();
                break;

            case R.id.buttonAdd:
                clickSymb('+');
                break;

            case R.id.buttonDivide:
                clickSymb('÷');
                break;

            case R.id.buttonMultiply:
                clickSymb('*');
                break;

            case R.id.buttonSubstract:
                clickSymb('-');
                break;

            case R.id.buttonClear:
                if (etLCD.getText().toString().equals("")) tvVar.setText("");
                if (tvVar.getText().toString().equals("")) etLCD.setText("");
                if (up) etLCD.setText("");
                else tvVar.setText("");
                tvLCD.setText("");
                isRes=false;
                break;

            case R.id.buttonBack:
                if (up) {
                    if (!etLCD.getText().toString().equals(""))
                        etLCD.setText(etLCD.getText().toString().substring(0, etLCD.getText().toString().length() - 1));
                }
                else {
                    if (!tvVar.getText().toString().equals(""))
                        tvVar.setText(tvVar.getText().toString().substring(0, tvVar.getText().toString().length() - 1));
                }
                break;

            case R.id.buttonBraces:
                clickBr();
                break;

            case R.id.buttonEqual:
                calcExpr();
                break;

            case R.id.editText:
                up=true;
                isRes=false;
                break;

            case R.id.editVar:
                up=false;
                isRes=false;
                break;

            case R.id.buttonExp:
                clickSymb('e');
                break;

            case R.id.buttonPi:
                clickSymb('π');
                break;

            case R.id.buttonSin:
                clickFunc(4);
                break;

            case R.id.buttonSqrt:
                clickFunc(3);
                break;

            case R.id.buttonPow:
                clickFunc(10);
                break;

            case R.id.buttonCos:
                clickFunc(5);
                break;

            case R.id.buttonTan:
                clickFunc(6);
                break;

            case R.id.buttonLn:
                clickFunc(7);
                break;

            case R.id.buttonLg:
                clickFunc(8);
                break;

            case R.id.buttonAbs:
                clickFunc(19);
                break;

            case R.id.buttonX:
                clickSymb('x');
                break;

            case R.id.infoDegRad:
                if (tvDegRad.getText().toString().equals("Rad"))
                {
                    tvDegRad.setText("Deg");
                }
                else {
                    tvDegRad.setText("Rad");
                }

                break;
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            switch(v.getId()) {
                case R.id.buttonSec:
                    clickFunc(11);
                    break;

                case R.id.buttonCosec:
                    clickFunc(12);
                    break;

                case R.id.buttonSinH:
                    clickFunc(13);
                    break;

                case R.id.buttonCosH:
                    clickFunc(14);
                    break;

                case R.id.buttonTanH:
                    clickFunc(15);
                    break;

                case R.id.buttonSqr:
                    clickFunc(9);
                    break;

                case R.id.buttonFrac:
                    clickFunc(2);
                    break;

                case R.id.buttonTwoDeg:
                    clickFunc(17);
                    break;

                case R.id.buttonDecDeg:
                    clickFunc(1);
                    break;

                case R.id.buttonExpDeg:
                    clickFunc(18);
                    break;
            }
        }
    }

    public boolean isRad()
    {
        return (tvDegRad.getText().toString().equals("Rad"));
    }

    private void clickSymb(char symb){
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
        String s;
        if (up) {
            s = etLCD.getText().toString();
            etLCD.setText(Utils.getSymb(s, symb));
        }
        else {
            s = tvVar.getText().toString();
            if (Character.isDigit((char) symb)) {
                tvVar.setText(Utils.getSymb(s, symb)); // доробити перевірку вводу числа
            }
            else {
                if ((symb=='-')&& s.equals("")) tvVar.setText("-");
                if ((symb=='e'||symb=='π')&& (s.equals("")||s.equals("-"))) tvVar.setText(Utils.getSymb(s, symb));

            }
        }
    }

    private void clickBr(){
        addRes();
        etLCD.setText(Utils.getBr(etLCD.getText().toString()));
    }


    private void clickDot(){
        String s;
        if (up) s = etLCD.getText().toString();
        else s = tvVar.getText().toString();
        if (!s.equals(""))
        {
            if (up) etLCD.setText(Utils.getDot(s));
            else tvVar.setText(Utils.getDot(s));
        }
    }

    private void clickFunc(int f){
        if (!(f==9||f==10||f==16)) addRes();// замінити нумерацію
        String s = etLCD.getText().toString();
        etLCD.setText(Utils.getFunc(s, f));
    }

    private void addRes(){
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


    private void calcExpr(){
        if (correct)  etLCD.setText(Utils.autoCorrection(etLCD.getText().toString()));
        MathParser parser = new MathParser();
        String expression = etLCD.getText().toString();
        expression=Utils.funcCorrect(expression);
        String x=tvVar.getText().toString();
        if (!x.equals("")) {
            if (Double.parseDouble(x)<0) x = "(" + x + ")";
            expression=expression.replaceAll("x",x);
            try {
                double res = parser.Parse(expression, isRad());
                res = ((double) Math.round(res * 10000000000L)) / 10000000000L;
                long r = Math.round(res);
                if (r == res) {
                    tvLCD.setText("f("+tvVar.getText().toString()+")="+Integer.toString((int) r));
                } else {
                    if (Double.toString(res).equals("Infinity") || Double.toString(res).equals("NaN"))
                        tvLCD.setText("f("+tvVar.getText().toString()+")="+Double.toString(res));
                    else {
                        String s = etLCD.getText().toString() + "=";
                        tvLCD.setText("f("+tvVar.getText().toString()+")="+Double.toString(res));
                    }
                }
                isRes = true;
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else
            Toast.makeText(getApplicationContext(),
                    "Enter x-value! ", Toast.LENGTH_LONG).show();
    }


}

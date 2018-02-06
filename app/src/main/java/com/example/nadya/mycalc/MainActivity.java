package com.example.nadya.mycalc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.io.BufferedWriter;
import java.io.File;
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
    SharedPreferences sp;
    Boolean correct;
    String styleCalc;
    static String sThemeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        correct = sp.getBoolean("corr", true);
        styleCalc = sp.getString("app_style", "Ligth");
        Utils.loadTheme(this, styleCalc);
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

            case R.id.itemFunc:
                intent = new Intent(MainActivity.this, FunctionActivity.class);
                startActivity(intent);
                return true;

            case R.id.itemHist:
                intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
                return true;

            case R.id.itemSet:
                intent = new Intent(MainActivity.this, PrefActivity.class);
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
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        etLCD.setText(savedInstanceState.getString("result"));
        tvLCD.setText(savedInstanceState.getString("memory"));
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

            case R.id.buttonPers:
                clickSymb('%');
                break;

            case R.id.buttonClear:
                if (etLCD.getText().toString().equals("")) tvLCD.setText("");
                etLCD.setText("");
                isRes=false;
                break;

            case R.id.buttonBack:
                if (!etLCD.getText().toString().equals(""))
                    etLCD.setText(etLCD.getText().toString().substring(0, etLCD.getText().toString().length() - 1));
                break;

            case R.id.buttonBraces:
                clickBr();
                break;

            case R.id.buttonEqual:
                calcExpr();
                break;

            case R.id.infoTextView:
                String s = etLCD.getText().toString();
                char c = s.charAt(s.length() - 1);
                if ("+-*÷%(".indexOf(c) > -1)
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
                    clickFunc(1);
                    break;

                case R.id.buttonFrac:
                    clickFunc(2);
                    break;

                case R.id.buttonSqrt:
                    clickFunc(3);
                    break;

                case R.id.buttonSqr:
                    clickFunc(9);
                    break;

                case R.id.buttonFact:
                    clickFunc(16);
                    break;
            }
        }
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
        String s = etLCD.getText().toString();
        etLCD.setText(Utils.getSymb(s, symb));
    }

    private void clickBr(){
        addRes();
        etLCD.setText(Utils.getBr(etLCD.getText().toString()));
    }

    private void clickDot(){
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
                etLCD.setText(Utils.getDot(s));
            }
        }
    }

    private void clickFunc(int f){
        if (!(f==9||f==16)) addRes();
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
            try{
                double res =parser.Parse(expression, true);
                res = ((double)Math.round(res * 10000000000L)) / 10000000000L; //
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
                        etLCD.setText(Double.toString(res));
                        saveFile(s+etLCD.getText().toString());
                    }
                }
                isRes=true;
            } catch(Exception e){
                Toast.makeText(getApplicationContext(),
                        "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

    }


    private void saveFile(String text) {
        try {
            String line="";
            File f = new File(getFilesDir() + "/hist.txt");
            if (f.exists())
                line=readFile();
            line="\n"+text+"\n"+line;
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput("hist.txt", Context.MODE_WORLD_READABLE)));
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

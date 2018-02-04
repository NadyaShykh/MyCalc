package com.example.nadya.mycalc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class HistoryActivity extends AppCompatActivity {

    TextView tvHist;
    SharedPreferences sp;
    String styleCalc;
    static String sThemeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        styleCalc = sp.getString("app_style", "Ligth");
        if (styleCalc.equals("Ligth"))
            setTheme(R.style.AppTheme_NoActionBar);
        else
            setTheme(R.style.CustomTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvHist.setText("");
                clearFile();
            }
        });

        tvHist = (TextView) findViewById(R.id.histTextView);
        tvHist.setMovementMethod(new ScrollingMovementMethod());
        File f = new File(getFilesDir() + "/hist.txt");
        if (f.exists()) openFile();
    }

    @Override
    protected void onResume() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        styleCalc = sp.getString("app_style", "Ligth");
        if (styleCalc.equals("Ligth")) sThemeId="2131689480";
        else sThemeId="2131689646";
        if (!Integer.toString(Utils.getThemeId(this)).equals(sThemeId))
            Utils.changeToTheme(this, Utils.THEME_LIGTH);
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFile() {
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
                tvHist.setText(builder);
            }
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void clearFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput("hist.txt", MODE_WORLD_READABLE)));
            bw.write("");
            bw.flush();
            bw.close();
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }
}

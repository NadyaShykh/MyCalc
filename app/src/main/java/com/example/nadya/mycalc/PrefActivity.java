package com.example.nadya.mycalc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;


public class PrefActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{

    SharedPreferences sp;
    String styleCalc;
    static String sThemeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        styleCalc = sp.getString("app_style", "Ligth");
        Utils.loadTheme(this, styleCalc);
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        sp = PreferenceManager.getDefaultSharedPreferences(PrefActivity.this);
        styleCalc = sp.getString("app_style", "Ligth");
        if (styleCalc.equals("Ligth"))
            sThemeId="2131689478";
        else
            sThemeId="2131689645";
        if (!Integer.toString(Utils.getThemeId(this)).equals(sThemeId))
            Utils.changeToTheme(PrefActivity.this, Utils.THEME_LIGTH);
    }


}

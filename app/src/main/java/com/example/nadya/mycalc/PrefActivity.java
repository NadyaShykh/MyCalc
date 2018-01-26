package com.example.nadya.mycalc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import java.lang.reflect.Method;

public class PrefActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{

    SharedPreferences sp;
    String style_calc;
    static String themeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        style_calc = sp.getString("app_style", "Ligth");
        if (style_calc.equals("Ligth"))
            setTheme(R.style.AppTheme);
        else
            setTheme(R.style.CustomTheme);
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
        style_calc = sp.getString("app_style", "Ligth");
        if (style_calc.equals("Ligth"))
            themeId="2131689478";
        else
            themeId="2131689645";
        if (!Integer.toString(getThemeId()).equals(themeId))
            Utils.changeToTheme(PrefActivity.this, Utils.THEME_LIGTH);
    }


    int getThemeId() {
        try {
            Class<?> wrapper = Context.class;
            Method method = wrapper.getMethod("getThemeResId");
            method.setAccessible(true);
            return (Integer) method.invoke(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}

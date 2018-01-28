package com.example.nadya.mycalc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import java.lang.reflect.Method;


public class Utils {
    private static int sTheme;
    public final static int THEME_LIGTH = 0;
    //public final static int THEME_DARK = 1;

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



}

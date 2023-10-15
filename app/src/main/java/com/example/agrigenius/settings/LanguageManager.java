package com.example.agrigenius.settings;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageManager {

    private Context ct;
    public LanguageManager(Context ctx)
    {
        ct=ctx;
    }

    public void updateResource(String code)
    {
        Locale locale=new Locale(code);
        Locale.setDefault(locale);
        Resources resources=ct.getResources();
        Configuration configuration=resources.getConfiguration();
        configuration.locale=locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());


    }

    public String getCurrentLanguage() {
        Configuration configuration = ct.getResources().getConfiguration();
        return configuration.locale.getLanguage();
    }
}
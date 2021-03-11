package ru.kirea.androidcalculator.core;

import android.content.Context;
import android.content.SharedPreferences;

import ru.kirea.androidcalculator.R;

public class Preferences {
    //настройки приложения
    private static final String PREFERENCE_NAME = "appSettings";
    public static final String THEME_ID = "themeId";

    //темы
    private static final int DEFAULT_THEME = 0; //тема по умолчанию
    private static final int DARK_THEME = 1; //темная тема

    private Context context;

    public Preferences(Context context) {
        this.context = context;
    }

    //сохранить числовой параметр
    public void setSetting(String key, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    //получить числовой параметр
    public int getSetting(String key, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    //получить тему приложения
    public int getAppTheme() {
        int themeId = getSetting(THEME_ID, DEFAULT_THEME);
        return themeId == DEFAULT_THEME ? R.style.AppTheme : R.style.AppThemeDark;
    }

    //темная тема или нет
    public boolean isDarkTheme() {
        return getSetting(THEME_ID, DEFAULT_THEME) == DARK_THEME;
    }
}

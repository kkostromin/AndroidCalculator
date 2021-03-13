package ru.kirea.androidcalculator.uimodel;

import android.content.Context;

import ru.kirea.androidcalculator.core.Preferences;

public class SettingPresenter {
    private Preferences preferences;
    private BaseView baseView;

    public SettingPresenter(Context context, BaseView baseView) {
        this.baseView = baseView;
        preferences = new Preferences(context);
    }

    //сохранение темы приложения
    public void saveTheme(boolean isDarkTheme) {
        preferences.setSetting(Preferences.THEME_ID, isDarkTheme ? 1 : 0);
        baseView.recreateActivity();
    }

    public boolean isDarkTheme() {
        return preferences.isDarkTheme();
    }
}

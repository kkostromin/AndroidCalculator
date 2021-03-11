package ru.kirea.androidcalculator.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.kirea.androidcalculator.AppConstants;
import ru.kirea.androidcalculator.R;

public class BaseActivity extends AppCompatActivity {
    private int themeId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme());//задаем тему
    }

    //получить тему
    private int getAppTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.PREFERENCE_NAME, MODE_PRIVATE);
        themeId = sharedPreferences.getInt(AppConstants.THEME_ID, AppConstants.DEFAULT_THEME);
        return themeId == AppConstants.DEFAULT_THEME ? R.style.AppTheme : R.style.AppThemeDark;
    }

    public boolean isDarkTheme() {
        return themeId == AppConstants.DARK_THEME;
    }
}

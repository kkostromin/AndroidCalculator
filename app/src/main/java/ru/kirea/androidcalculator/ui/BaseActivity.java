package ru.kirea.androidcalculator.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.kirea.androidcalculator.core.Preferences;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme());//задаем тему
    }

    //получить тему
    private int getAppTheme() {
        return new Preferences(getApplicationContext()).getAppTheme();
    }
}

package ru.kirea.androidcalculator.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import ru.kirea.androidcalculator.R;
import ru.kirea.androidcalculator.uimodel.BaseView;
import ru.kirea.androidcalculator.uimodel.SettingPresenter;

public class ActivitySetting extends BaseActivity implements BaseView {

    private SettingPresenter settingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        settingPresenter = new SettingPresenter(this, this);
        initButton();
    }

    @Override
    public void recreateActivity() {
        recreate();
    }

    @Override
    public void runActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void initButton() {
        Switch darkThemeEnabled = findViewById(R.id.dark_theme_id);
        //проставляем переключатель в зависимости от текущей темы
        darkThemeEnabled.setChecked(settingPresenter.isDarkTheme());

        //вешаем обработчик на переключатель
        darkThemeEnabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingPresenter.saveTheme(((Switch) v).isChecked());
            }
        });
    }
}

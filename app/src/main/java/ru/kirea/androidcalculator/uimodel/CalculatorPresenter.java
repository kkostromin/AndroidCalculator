package ru.kirea.androidcalculator.uimodel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import ru.kirea.androidcalculator.R;
import ru.kirea.androidcalculator.core.Preferences;
import ru.kirea.androidcalculator.core.StringCalculatorHelper;
import ru.kirea.androidcalculator.ui.ActivitySetting;

public class CalculatorPresenter {
    private final String KEY_FIRST_VALE = "firstValue";
    private final String KEY_SECOND_VALE = "secondValue";
    private final String KEY_OPERATION = "operation";

    private StringCalculatorHelper stringCalculatorHelper;
    private CalculationView calculateView;
    private Preferences preferences;

    private int appTheme;

    public CalculatorPresenter(Context context, CalculationView calculateView) {
        this.calculateView = calculateView;
        stringCalculatorHelper = new StringCalculatorHelper();
        preferences = new Preferences(context);

        //запоминаем тему приложения
        appTheme = preferences.getSetting(Preferences.THEME_ID, Preferences.DEFAULT_THEME);
    }

    //сохранить настройки
    public void saveInstanceState(Bundle outState) {
        stringCalculatorHelper.saveInstanceState(outState);
    }

    //загрузить настройки
    public void restoreInstanceState(Bundle savedInstanceState) {
        stringCalculatorHelper.restoreInstanceState(savedInstanceState);
    }

    public void calculationOperation(View v, String value) {
        int id = v.getId();

        if (id == R.id.button_clear_id) {
            stringCalculatorHelper = new StringCalculatorHelper();
        } else if (id == R.id.button_total_id) {
            stringCalculatorHelper.calculate();
        } else if (id == R.id.button_percent_id) {
            stringCalculatorHelper.setPercent();
        } else if (id == R.id.button_zero_id
                || id == R.id.button_one_id
                || id == R.id.button_two_id
                || id == R.id.button_three_id
                || id == R.id.button_four_id
                || id == R.id.button_five_id
                || id == R.id.button_sex_id
                || id == R.id.button_seven_id
                || id == R.id.button_eight_id
                || id == R.id.button_nine_id
                || id == R.id.button_point_id) {
            stringCalculatorHelper.setValue(value);
        } else {
            stringCalculatorHelper.setOperation(value);
        }
    }

    //получить историю операций
    public String getHistory() {
        return stringCalculatorHelper.getHistory();
    }

    //получить результат вычисления
    public String getResult() {
        return stringCalculatorHelper.getResult();
    }

    //обработка меню
    public boolean optionMenuSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_value_setting) {
            calculateView.runActivity(new Intent(calculateView.getContext(), ActivitySetting.class));
            return true;
        }
        return false;
    }

    public void onResume() {
        //проверим, поменялась ли тема
        if (appTheme != preferences.getSetting(Preferences.THEME_ID, Preferences.DEFAULT_THEME)) {
            calculateView.recreateActivity();
        }
    }

    //проставить первоначальные параметры
    public void setFirstValues(Bundle extras) {
        if (extras != null) {
            double firstValue = extras.getDouble(KEY_FIRST_VALE);
            double secondValue = extras.getDouble(KEY_SECOND_VALE);
            char operation = extras.getChar(KEY_OPERATION);

            //записываем первое число
            if (firstValue != 0) {
                stringCalculatorHelper = new StringCalculatorHelper();
                stringCalculatorHelper.setValue(String.valueOf(firstValue));

                //записываем операцию
                if (stringCalculatorHelper.isAvailableOperation(operation)) {
                    stringCalculatorHelper.setOperation(String.valueOf(operation));
                }

                //записываем второе число
                if (secondValue != 0) {
                    stringCalculatorHelper.setValue(String.valueOf(secondValue));
                }

                calculateView.showResult();
            }
        }
    }
}

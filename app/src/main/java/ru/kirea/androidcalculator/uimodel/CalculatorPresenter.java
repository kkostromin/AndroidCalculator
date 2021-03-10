package ru.kirea.androidcalculator.uimodel;

import android.os.Bundle;
import android.view.View;

import ru.kirea.androidcalculator.R;
import ru.kirea.androidcalculator.core.StringCalculatorHelper;

public class CalculatorPresenter {
    private StringCalculatorHelper stringCalculatorHelper;

    public CalculatorPresenter() {
        stringCalculatorHelper = new StringCalculatorHelper();
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
}

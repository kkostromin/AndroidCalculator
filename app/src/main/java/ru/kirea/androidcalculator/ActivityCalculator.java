package ru.kirea.androidcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityCalculator extends AppCompatActivity {

    private StringCalculatorHelper stringCalculatorHelper;
    private EditText history;
    private EditText result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        history = findViewById(R.id.history_id);
        result = findViewById(R.id.result_id);

        stringCalculatorHelper = new StringCalculatorHelper();

        //Задание 1 - вешаем обаботчик на кнопки
        initButton();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        stringCalculatorHelper.saveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        stringCalculatorHelper.restoreInstanceState(savedInstanceState);
    }

    private void initButton() {
        findViewById(R.id.button_clear_id).setOnClickListener(clickListener); //кнопка очистки

        findViewById(R.id.button_zero_id).setOnClickListener(clickListener); //кнопка 0
        findViewById(R.id.button_one_id).setOnClickListener(clickListener); //кнопка 1
        findViewById(R.id.button_two_id).setOnClickListener(clickListener); //кнопка 2
        findViewById(R.id.button_three_id).setOnClickListener(clickListener); //кнопка 3
        findViewById(R.id.button_four_id).setOnClickListener(clickListener); //кнопка 4
        findViewById(R.id.button_five_id).setOnClickListener(clickListener); //кнопка 5
        findViewById(R.id.button_sex_id).setOnClickListener(clickListener); //кнопка 6
        findViewById(R.id.button_seven_id).setOnClickListener(clickListener); //кнопка 7
        findViewById(R.id.button_eight_id).setOnClickListener(clickListener); //кнопка 8
        findViewById(R.id.button_nine_id).setOnClickListener(clickListener); //кнопка 9
        findViewById(R.id.button_point_id).setOnClickListener(clickListener); //кнопка .

        findViewById(R.id.button_plus_id).setOnClickListener(clickListener); //кнопка +
        findViewById(R.id.button_minus_id).setOnClickListener(clickListener); //кнопка -
        findViewById(R.id.button_multiply_id).setOnClickListener(clickListener); //кнопка *
        findViewById(R.id.button_divide_id).setOnClickListener(clickListener); //кнопка ÷
        findViewById(R.id.button_percent_id).setOnClickListener(clickListener); //кнопка %
        findViewById(R.id.button_total_id).setOnClickListener(clickListener); //кнопка =
    }

    //обработка нажатий
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calculationOperation(((Button) v).getText().toString());
        }
    };

    private void calculationOperation(String buttonText) {
        if (buttonText.equals(getString(R.string.button_clear))) {
            stringCalculatorHelper = new StringCalculatorHelper();
        } else if (buttonText.equals(getString(R.string.button_total))) {
            stringCalculatorHelper.calculate();
        } else if (buttonText.equals(getString(R.string.button_percent))) {
            stringCalculatorHelper.setPercent();
        } else if (buttonText.equals(getString(R.string.button_zero))
                || buttonText.equals(getString(R.string.button_one))
                || buttonText.equals(getString(R.string.button_two))
                || buttonText.equals(getString(R.string.button_three))
                || buttonText.equals(getString(R.string.button_four))
                || buttonText.equals(getString(R.string.button_five))
                || buttonText.equals(getString(R.string.button_sex))
                || buttonText.equals(getString(R.string.button_seven))
                || buttonText.equals(getString(R.string.button_eight))
                || buttonText.equals(getString(R.string.button_nine))
                || buttonText.equals(getString(R.string.button_point))) {
            stringCalculatorHelper.setValue(buttonText);
        } else {
            stringCalculatorHelper.setOperation(buttonText);
        }
        showResult();
    }

    private void showResult() {
        history.setText(stringCalculatorHelper.getHistory());
        result.setText(stringCalculatorHelper.getResult());

        history.setSelection(history.getText().length());
        result.setSelection(result.getText().length());
    }
}

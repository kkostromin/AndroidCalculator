package ru.kirea.androidcalculator.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import ru.kirea.androidcalculator.R;
import ru.kirea.androidcalculator.uimodel.CalculatorPresenter;
import ru.kirea.androidcalculator.uimodel.BaseView;

public class ActivityCalculator extends BaseActivity implements BaseView {

    private CalculatorPresenter calculatorPresenter;
    private SparseArray<String> buttonValueMapping;
    private EditText history;
    private EditText result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        history = findViewById(R.id.history_id);
        result = findViewById(R.id.result_id);
        calculatorPresenter = new CalculatorPresenter(this, this);

        buttonValueMapping = new SparseArray<>();
        initButtonValueMapping();

        //вешаем обаботчик на кнопки
        initButton();
    }

    @Override
    public void onResume() {
        super.onResume();
        calculatorPresenter.onResume();
    }

    //связка кнопок с их значениями
    private void initButtonValueMapping() {
        buttonValueMapping.put(R.id.button_clear_id, getString(R.string.button_clear));

        buttonValueMapping.put(R.id.button_zero_id, getString(R.string.button_zero));
        buttonValueMapping.put(R.id.button_one_id, getString(R.string.button_one));
        buttonValueMapping.put(R.id.button_two_id, getString(R.string.button_two));
        buttonValueMapping.put(R.id.button_three_id, getString(R.string.button_three));
        buttonValueMapping.put(R.id.button_four_id, getString(R.string.button_four));
        buttonValueMapping.put(R.id.button_five_id, getString(R.string.button_five));
        buttonValueMapping.put(R.id.button_sex_id, getString(R.string.button_sex));
        buttonValueMapping.put(R.id.button_seven_id, getString(R.string.button_seven));
        buttonValueMapping.put(R.id.button_eight_id, getString(R.string.button_eight));
        buttonValueMapping.put(R.id.button_nine_id, getString(R.string.button_nine));
        buttonValueMapping.put(R.id.button_point_id, getString(R.string.button_point));

        buttonValueMapping.put(R.id.button_plus_id, getString(R.string.button_plus));
        buttonValueMapping.put(R.id.button_minus_id, getString(R.string.button_minus));
        buttonValueMapping.put(R.id.button_multiply_id, getString(R.string.button_multiply));
        buttonValueMapping.put(R.id.button_divide_id, getString(R.string.button_divide));
        buttonValueMapping.put(R.id.button_percent_id, getString(R.string.button_percent));
        buttonValueMapping.put(R.id.button_total_id, getString(R.string.button_total));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        calculatorPresenter.saveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculatorPresenter.restoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //обработка нажатия на меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return calculatorPresenter.optionMenuSelected(item);
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
            calculatorPresenter.calculationOperation(v, buttonValueMapping.get(v.getId(), null));
            showResult();
        }
    };

    private void showResult() {
        history.setText(calculatorPresenter.getHistory());
        result.setText(calculatorPresenter.getResult());

        history.setSelection(history.getText().length());
        result.setSelection(result.getText().length());
    }
}

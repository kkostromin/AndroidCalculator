package ru.kirea.androidcalculator;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Задание 2 - данные и операции калькулятора
public class StringCalculatorHelper {
    private final String KEY_BUNDLE = "calculationSetting";
    private final String KEY_OPERATION_TYPE = "o";
    private final String KEY_VALUE = "v";

    List<CalculationItem> history; //история операций
    private Double result;

    public StringCalculatorHelper() {
        result = null;
        history = new ArrayList<>();
    }

    //задать операцию
    public void setOperation(String value) {
        CalculationItem item = getLastItem();
        boolean asLast = !(item != null && item.getItemType() == CalculationItem.ITEM_OPERATION);
        //в начале формулы можно только минус вводить
        if (!history.isEmpty() || value.equals("-")) {
            setLastItem(new ItemOperation(value), asLast);
        }
    }

    //задать значение
    public void setValue(String value) {
        CalculationItem item = getLastItem();
        String oldValue = "";
        boolean asLast = true;
        if (item != null && item.getItemType() == CalculationItem.ITEM_VALUE) {
            asLast = false;
            oldValue = item.getValue();
        }

        //две точки не может быть
        if (!value.equals(".") || !oldValue.contains(".")) {
            setLastItem(new ItemValue(oldValue + value), asLast);
        }
    }

    //задать процент
    public void setPercent() {
        CalculationItem item = getLastItem();
        boolean asLastNumber = item != null && item.getItemType() == CalculationItem.ITEM_VALUE;
        //процент можно добавлять только после чисел
        if (asLastNumber) {
            setLastItem(new ItemPercent(), true);
        }
    }

    //получить историю операций
    public String getHistory() {
        StringBuilder stringBuilder = new StringBuilder();
        for (CalculationItem item: history) {
            stringBuilder.append(item.getValue());
        }
        return stringBuilder.toString();
    }

    //получить результат
    public String getResult() {
        return result == null ? null : result.isInfinite() ? "ОШИБКА" : result.toString();
    }

    //посчитать формулу
    public void calculate() {
        calculate(true);
    }
    private void calculate(boolean clearHistory) {
        result = 0d;
        double curValue = 0;
        String oldOper = null;
        for (int ind = 0; ind < history.size(); ind ++) {
            CalculationItem item = history.get(ind);

            if (item.getItemType() == CalculationItem.ITEM_VALUE) {
                curValue = Double.parseDouble(item.getValue());
                if (ind == 0) {
                    result = curValue;
                }

                //проверим проценты
                if (ind != history.size() -1 && history.get(ind +1).getItemType() == CalculationItem.ITEM_PERCENT) {
                    curValue = result * curValue / 100;
                }

                //выполняем операцию
                if (oldOper != null) {
                    if (oldOper.equals("+")) {
                        plus(curValue);
                    } else if (oldOper.equals("-")) {
                        minus(curValue);
                    } else if (oldOper.equals("*")) {
                        multiply(curValue);
                    } else if (oldOper.equals("÷")) {
                        divide(curValue);
                    }
                }
            }

            //запоминаем операцию
            if (item.getItemType() == CalculationItem.ITEM_OPERATION) {
                oldOper = item.getValue();
            }

        }

        if (clearHistory) {
            history = new ArrayList<>();
            setLastItem(new ItemValue(String.valueOf(result)), true);
            setLastItem(new ItemOperation("+"), true);
        }
    }

    //сохранить настройки
    public void saveInstanceState(Bundle outState) {
        try {
            JSONArray jsonArray = new JSONArray();
            for (CalculationItem item: history) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(KEY_OPERATION_TYPE, item.getItemType());
                jsonObject.put(KEY_VALUE, item.getValue());
                jsonArray.put(jsonObject);
            }
            outState.putString(KEY_BUNDLE, jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //загрузить настройки
    public void restoreInstanceState(Bundle savedInstanceState) {
        history = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(savedInstanceState.getString(KEY_BUNDLE));
            for (int ind = 0; ind < jsonArray.length(); ind++) {
                JSONObject jsonObject = jsonArray.getJSONObject(ind);
                CalculationItem item;
                if (jsonObject.has(KEY_OPERATION_TYPE) && jsonObject.has(KEY_VALUE)) {
                    int operationType = jsonObject.getInt(KEY_OPERATION_TYPE);
                    String value = jsonObject.getString(KEY_VALUE);
                    if (operationType == CalculationItem.ITEM_OPERATION) {
                        item = new ItemOperation(value);
                    } else if (operationType == CalculationItem.ITEM_PERCENT) {
                        item = new ItemPercent();
                    } else {
                        item = new ItemValue(value);
                    }

                    setLastItem(item, true);
                }
            }
            calculate(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //сложить
    private void plus(double value) {
        result += value;
    }

    //вычесть
    private void minus(double value) {
        result -= value;
    }

    //умножить
    private void multiply(double value) {
        result *= value;
    }

    //делить
    private void divide(double value) {
        result /= value;
    }

    //получить последнее значение в строке калькулятора
    private CalculationItem getLastItem() {
        return history.isEmpty() ? null : history.get(history.size()-1);
    }

    //задать последнее значение в строке калькулятора
    private void setLastItem(CalculationItem item, boolean asLast) {
        if (asLast) {
            history.add(item);
        } else {
            history.set(history.size() -1, item);
        }
        calculate(false);
    }

    //вспомогательный класс для хранения операций и значений калькулятора
    private class Item implements CalculationItem {
        String value = "";
        private int type;

        private Item(String value, int type) {
            this.value = value;
            this.type = type;
        }

        @Override
        public int getItemType() {
            return type;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    private class ItemValue extends Item {
        private ItemValue(String value) {
            super(value, CalculationItem.ITEM_VALUE);
        }
    }

    private class ItemOperation extends Item {
        private ItemOperation(String value) {
            super(value, CalculationItem.ITEM_OPERATION);
        }
    }

    private class ItemPercent extends Item {
        private ItemPercent() {
            super("%", CalculationItem.ITEM_PERCENT);
        }
    }
}

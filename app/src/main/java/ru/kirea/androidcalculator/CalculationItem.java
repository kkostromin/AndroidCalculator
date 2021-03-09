package ru.kirea.androidcalculator;

public interface CalculationItem {
    int ITEM_VALUE = 1; //значение в калькуляторе
    int ITEM_OPERATION = 2; //операция
    int ITEM_PERCENT = 3; //проценты

    int getItemType();

    String getValue();
}

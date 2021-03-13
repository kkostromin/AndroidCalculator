package ru.kirea.androidcalculator.uimodel;

import android.content.Context;
import android.content.Intent;

public interface BaseView {
    void recreateActivity();

    void runActivity(Intent intent);

    Context getContext();
}

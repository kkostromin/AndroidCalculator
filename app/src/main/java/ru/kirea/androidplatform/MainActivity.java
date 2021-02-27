package ru.kirea.androidplatform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson1_1); //1 4 задание (ems - задает ширину поля, равной ширине самой широкой буквы в шрифте * число ems)
        //setContentView(R.layout.activity_lesson1_2_and_3); //2 и 3 задание
    }
}

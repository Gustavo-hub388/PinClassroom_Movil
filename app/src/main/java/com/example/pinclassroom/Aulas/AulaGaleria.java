package com.example.pinclassroom.Aulas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.pinclassroom.R;

public class AulaGaleria extends AppCompatActivity {

    private ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula_galeria);

        pager = findViewById(R.id.galeria_aula);



    }
}

package com.example.pinclassroom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TabHost;

public class AulasActivity extends AppCompatActivity {

    TabHost contenedor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aulas);

        contenedor = findViewById(R.id.contenedor_tabs);
        contenedor.setup();

        TabHost.TabSpec spec1 = contenedor.newTabSpec("Aulas");
        spec1.setContent(R.id.tab_aula);
        spec1.setIndicator("Aula");
        contenedor.addTab(spec1);

        TabHost.TabSpec spec2 = contenedor.newTabSpec("Mapas");
        spec2.setContent(R.id.tab_mapa);
        spec2.setIndicator("Mapa");
        contenedor.addTab(spec2);

        contenedor.setCurrentTab(0);

    }
}

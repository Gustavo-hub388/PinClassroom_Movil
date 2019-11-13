package com.example.pinclassroom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TabHost;

public class EventosActivity extends AppCompatActivity {

    TabHost contenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        contenedor = findViewById(R.id.contenedor_tabs3);
        contenedor.setup();

        TabHost.TabSpec spec1 = contenedor.newTabSpec("Eventos");
        spec1.setContent(R.id.tab_eventos_generales);
        spec1.setIndicator("Eventos");
        contenedor.addTab(spec1);

        TabHost.TabSpec spec2 = contenedor.newTabSpec("Eventos1");
        spec2.setContent(R.id.tab_eventos_facultad);
        spec2.setIndicator("Eventos por facultad");
        contenedor.addTab(spec2);

        contenedor.setCurrentTab(0);
    }
}

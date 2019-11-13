package com.example.pinclassroom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TabHost;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

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

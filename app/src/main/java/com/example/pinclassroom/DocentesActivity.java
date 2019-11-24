package com.example.pinclassroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DocentesActivity extends AppCompatActivity {

    TabHost contenedor;
    //RecyclerView recyclerView;
    //List<Docentes> docente;

    //Adapater adapater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docentes);

        //recyclerView = findViewById(R.id.recycler_lista_docentes);

        contenedor = findViewById(R.id.contenedor_tabs1);
        contenedor.setup();

        TabHost.TabSpec spec1 = contenedor.newTabSpec("Docentes");
        spec1.setContent(R.id.tab_docente);
        spec1.setIndicator("Docentes");
        contenedor.addTab(spec1);

        TabHost.TabSpec spec2 = contenedor.newTabSpec("Favoritos");
        spec2.setContent(R.id.tab_fav_docente);
        spec2.setIndicator("Favoritos");
        contenedor.addTab(spec2);

        contenedor.setCurrentTab(0);
/*


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        docente = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        adapater = new Adapater(docente);

        recyclerView.setAdapter(adapater);

        database.getReference().child(FirebaseReferences.TEACHERS_REFERENCES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                docente.removeAll(docente);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Docentes docentes = snapshot.getValue(Docentes.class);
                    docente.add(docentes);
                }
                adapater.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

 */
    }
}

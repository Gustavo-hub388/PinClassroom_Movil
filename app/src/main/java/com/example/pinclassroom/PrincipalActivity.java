package com.example.pinclassroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView cardAulas, cardDocentes, cardEventos, cardConfiguraciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //definiendo las tarjetas
        cardAulas = findViewById(R.id.card_view_aulas);
        cardDocentes = findViewById(R.id.card_view_docentes);
        cardEventos = findViewById(R.id.card_view_eventos);
        cardConfiguraciones = findViewById(R.id.card_view_config);

        //agregando el evento click a las tarjetas
        cardAulas.setOnClickListener(this);
        cardDocentes.setOnClickListener(this);
        cardEventos.setOnClickListener(this);
        cardConfiguraciones.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.card_view_aulas : i = new Intent(this, AulasActivity.class); startActivity(i); break;
            case R.id.card_view_docentes : i = new Intent(this, DocentesActivity.class); startActivity(i); break;
            case R.id.card_view_eventos : i = new Intent(this, EventosActivity.class); startActivity(i); break;
            case R.id.card_view_config : i = new Intent(this, ConfigActivity.class); startActivity(i); break;
            default:break;
        }
    }
}

package com.example.pinclassroom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView cardAulas, cardDocentes, cardEventos, cardConfiguraciones;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private ImageView logout;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Quieres cerrar sesión?");
        builder.setMessage("No podras recibir notificaciones al tener tu sesión cerrada");
        builder.setCancelable(true);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Intent out = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(out);
                finish();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });


        logout = findViewById(R.id.log_out);

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



    public void on_click_log_out(View view){
        alertDialog = builder.create();
        alertDialog.show();

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

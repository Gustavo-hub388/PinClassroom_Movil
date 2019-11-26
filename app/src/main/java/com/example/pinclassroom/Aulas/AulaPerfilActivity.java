package com.example.pinclassroom.Aulas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinclassroom.R;

public class AulaPerfilActivity extends AppCompatActivity {

    private String recibirIDAula;
    private String recibirNombreAula;
    private String recibirEdificioAula;
    private String recibirPlantaAula;
    private String recibirCapacidadAula;
    // String recibirFoto;


    private TextView nombreAula;
    private TextView edificioAula;
    private TextView nivelAula;
    private TextView capacidadAula;
    //private ImageView fotoPerfilAula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula_perfil);

        recibirIDAula = getIntent().getExtras().get("vistaAulaID").toString();
        recibirNombreAula = getIntent().getExtras().get("vistaAulaNombre").toString();
        recibirEdificioAula = getIntent().getExtras().get("vistaAulaEdificio").toString();
        recibirPlantaAula = getIntent().getExtras().get("vistaAulaPlanta").toString();
        recibirCapacidadAula = getIntent().getExtras().get("vistaAulaCapacidad").toString();
        //recibirFoto = getIntent().getExtras().get("vistaAulaFoto").toString();

        nombreAula = findViewById(R.id.nomProfileAula);
        edificioAula = findViewById(R.id.edifProfileAula);
        nivelAula = findViewById(R.id.plantProfileAula);
        capacidadAula = findViewById(R.id.cantProfileAula);
        //fotoPerfilAula = findViewById(R.id.fotoProfileAula);


        try {
            nombreAula.setText(recibirNombreAula);
            edificioAula.setText(recibirEdificioAula);
            nivelAula.setText(recibirPlantaAula);
            capacidadAula.setText(recibirCapacidadAula);
            //fotoPerfilAula.setImageDrawable(Drawable.createFromPath(recibirFoto));
        } catch (Exception err) {
            Toast.makeText(this, "error: "+ err, Toast.LENGTH_LONG).show();
        }


        //Toast.makeText(this, "User ID: " + recibirIDAula + " " + recibirNombreAula, Toast.LENGTH_LONG).show();


    }

    public void IrListaAulas(View view) {
    }
}

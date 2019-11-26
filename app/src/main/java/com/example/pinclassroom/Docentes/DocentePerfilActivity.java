package com.example.pinclassroom.Docentes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinclassroom.R;

public class DocentePerfilActivity extends AppCompatActivity {

    private String recibirIDDocente;
    private String recibirNombreDocente;
    private String recibirFacultadDocente;
    private String recibirInfoAcadDocente;
    private String recibirContactosDocente;
    //private String recibirFoto;


    private TextView nombreDocente;
    private TextView FacultadDocente;
    private TextView InfoAcadDocente;
    private TextView ContactosDocente;
    //private ImageView fotoPerfilAula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente_perfil);

        recibirIDDocente = getIntent().getExtras().get("vistaDocenteID").toString();
        recibirNombreDocente = getIntent().getExtras().get("vistaDocenteNombre").toString();
        recibirFacultadDocente = getIntent().getExtras().get("vistaDocenteFacultad").toString();
        recibirInfoAcadDocente = getIntent().getExtras().get("vistaDocenteInfoAcad").toString();
        recibirContactosDocente = getIntent().getExtras().get("vistaDocenteContacto").toString();
        //recibirFoto = getIntent().getExtras().get("vistaAulaFoto").toString();

        nombreDocente = findViewById(R.id.nomProfileDocente);
        FacultadDocente = findViewById(R.id.facultadProfileDocente);
        InfoAcadDocente = findViewById(R.id.forAcaProfileDocente);
        ContactosDocente= findViewById(R.id.contactosProfileDocente);
        //fotoPerfilAula = findViewById(R.id.fotoProfileAula);


        try {
            nombreDocente.setText(recibirNombreDocente);
            FacultadDocente.setText(recibirFacultadDocente);
            InfoAcadDocente.setText(recibirInfoAcadDocente);
            ContactosDocente.setText(recibirContactosDocente);
            //fotoPerfilAula.setImageDrawable(Drawable.createFromPath(recibirFoto));
        } catch (Exception err) {
            Toast.makeText(this, "error: "+ err, Toast.LENGTH_LONG).show();
        }
    }
}

package com.example.pinclassroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
    }

    public void IrInicioConfig(View view) {
        startActivity(new Intent(this, PrincipalActivity.class));
        finish();
    }

    public void IrEnviarSMS(View view) {
        startActivity(new Intent(this, AyudaMejorarActivity.class));
    }

    public void IrEnviarAcercaDe(View view) {
        startActivity(new Intent(this, AboutActivity.class));
    }

    public void IrEnviarPerfil(View view) {
        startActivity(new Intent(this, PerfilUsuarioActivity.class));
    }
}

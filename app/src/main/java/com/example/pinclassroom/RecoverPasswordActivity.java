package com.example.pinclassroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.pinclassroom.RecupPass.RecupPassInterface;
import com.example.pinclassroom.RecupPass.RecupPassPresenter;
import com.google.android.material.textfield.TextInputEditText;

import mehdi.sakout.fancybuttons.FancyButton;

public class RecoverPasswordActivity extends AppCompatActivity implements RecupPassInterface.View {

    private TextInputEditText txtEmailRecover;
    private FancyButton btnEnviarEmail;
    private MaterialDialog mProgress;
    private RecupPassInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        setViews();
    }

    private void setViews() {
        presenter = new RecupPassPresenter(this);
        txtEmailRecover = findViewById(R.id.txtCorreoRecover);
        btnEnviarEmail = findViewById(R.id.btnEnviar);

        btnEnviarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRecupPass();
            }
        });

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("Cargando")
                .content("Espere por favor...")
                .cancelable(false)
                .progress(true,0);
        mProgress = builder.build();
    }

    public void IrInicio(View view){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void disableInputs() {
        setEnable(false);
    }

    private void setEnable(boolean b) {
        btnEnviarEmail.setEnabled(b);
        txtEmailRecover.setEnabled(b);
    }

    @Override
    public void enableInputs() {
        setEnable(true);
    }

    @Override
    public void showProgress() {
        mProgress.show();
    }

    @Override
    public void hideProgress() {
        mProgress.dismiss();
    }

    @Override
    public void handleRecupPass() {
        if(!isValidEmailLRecupPass()){
            Toast.makeText(this, "No es un correo válido",Toast.LENGTH_SHORT).show();
        } else {
            presenter.toRecupPass(txtEmailRecover.getText().toString());
        }
    }

    @Override
    public boolean isValidEmailLRecupPass() {
        return Patterns.EMAIL_ADDRESS.matcher(txtEmailRecover.getText().toString()).matches();
    }

    @Override
    public void onLogin() {
        finish();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this,error, Toast.LENGTH_SHORT).show();
        hideProgress();
        enableInputs();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}

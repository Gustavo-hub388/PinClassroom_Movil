package com.example.pinclassroom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.UserDictionary;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private EditText nombrePerfil;
    private EditText fechaPerfil;
    private EditText correoPerfil;
    private EditText carreraPerfil;
    private EditText sexoPerfil;
    private EditText txtPasswordOld;
    private EditText txtPasswordNew;
    private String Correo;
    private ImageView nombreEdit;
    private ImageView fechaEdit;
    private ImageView nombreSave;
    private ImageView fechaSave;
    private CircleImageView fotoPerfil;
    private ImageView FotoEdit;
    private ImageView PasswordEdit;
    private Button btnCambiarPassword;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private Uri mImgUriPerfil;
    ;


    private final int PICK_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        try {
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mStorage = FirebaseStorage.getInstance().getReference();

            mProgress = new ProgressDialog(this);
            mProgress.setMessage("Espero un momento...");
            mProgress.setCancelable(false);
            mProgress.show();


            //TextViews
            nombrePerfil = findViewById(R.id.nombrePerfil);
            fechaPerfil = findViewById(R.id.fechaPerfil);
            sexoPerfil = findViewById(R.id.sexoPerfil);
            correoPerfil = findViewById(R.id.correoPerfil);
            //carreraPerfil = findViewById(R.id.carreraPerfil);

            txtPasswordOld = findViewById(R.id.txtPasswordOld);
            txtPasswordNew = findViewById(R.id.txtPasswordNew);


            //ImageView Edit
            nombreEdit = findViewById(R.id.nombreEdit);
            fechaEdit = findViewById(R.id.fechaEdit);
            FotoEdit = findViewById(R.id.fotoEdit);
            PasswordEdit = findViewById(R.id.passwordEdit);

            //ImageView Save
            nombreSave = findViewById(R.id.nombreSave);
            fechaSave = findViewById(R.id.fechaSave);

            //ImageView FotoPerfil
            fotoPerfil = findViewById(R.id.fotoPefil);

            //Button's
            btnCambiarPassword = findViewById(R.id.btnCambiarPassword);

            //Correo
            Correo = user.getEmail();

            if (user.getPhotoUrl() != null) {
                Glide.with(this).load(user.getPhotoUrl()).into(fotoPerfil);
            }

            mDatabase.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        try {
                            String nombre = dataSnapshot.child("name").getValue().toString();
                            nombrePerfil.setText(nombre);
                        } catch (Exception err) {
                            Toast.makeText(getApplicationContext(), "No a ingresado su nombre en estos momentos, puede agregarlo aqui o puede ir a nuestra página web.", Toast.LENGTH_LONG).show();
                        }
                        try {
                            String fechaN = dataSnapshot.child("date").getValue().toString();
                            fechaPerfil.setText(fechaN);
                        } catch (Exception err) {
                            Toast.makeText(getApplicationContext(), "No a ingresado su fecha de naciemiento en estos momentos, puede agregarlo aqui o puede ir a nuestra página web.", Toast.LENGTH_LONG).show();
                        }
                        try {
                            String sexo = dataSnapshot.child("sex").getValue().toString();
                            sexoPerfil.setText(sexo);
                        } catch (Exception err) {
                            Toast.makeText(getApplicationContext(), "No a seleccionado su sexo en esos momentos, si desea selccionarlo dirijase a nuestra pagina web.", Toast.LENGTH_LONG).show();
                        }
                        /*try {
                            String carrera = dataSnapshot.child("carrera").getValue().toString();
                            carreraPerfil.setText(carrera);
                        } catch (Exception err) {
                            Toast.makeText(getApplicationContext(), "No a seleccionado su carrera en esos momentos, si desea selccionarla dirijase a nuestra pagina web.", Toast.LENGTH_LONG).show();
                        }*/
                        correoPerfil.setText(user.getEmail());
                        mProgress.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception err) {
            Toast.makeText(getApplicationContext(), "Error: Interfaz" + err, Toast.LENGTH_SHORT).show();
        }

        FotoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirGaleria();
            }
        });

        btnCambiarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CambiarPasswordUser();
            }
        });

    }

    private void CambiarPasswordUser() {
        if (TextUtils.isEmpty(txtPasswordOld.getText().toString())) {
            //Toast.makeText(this, "No es una contraseña válida",Toast.LENGTH_SHORT).show();
            txtPasswordOld.setError("Ingrese su contraseña actual");
        } else if (TextUtils.isEmpty(txtPasswordNew.getText().toString())) {
            txtPasswordNew.setError("Ingrese la contraseña nueva");
        } else {
            mProgress = new ProgressDialog(this);
            mProgress.setMessage("Cambiando contraseña...");
            mProgress.setCancelable(false);
            mProgress.show();
            final FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(Correo, txtPasswordOld.getText().toString());
            usuario.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    usuario.updatePassword(txtPasswordNew.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mProgress.dismiss();
                            Toast.makeText(PerfilUsuarioActivity.this, "Su contraseña fue cambiada exitosamente", Toast.LENGTH_LONG).show();
                            passwordEditFinish();
                        }
                    });
                }
            });
        }

    }

    public void IrConfigPeril(View view) {
        startActivity(new Intent(this, ConfigActivity.class));
        finish();
    }

    public void nombreEdit(View view) {
        nombrePerfil.setEnabled(true);
        nombrePerfil.setFocusableInTouchMode(true);
        nombreEdit.setVisibility(view.INVISIBLE);
        fechaEdit.setEnabled(false);
        FotoEdit.setEnabled(false);
        PasswordEdit.setEnabled(false);
        nombreSave.setVisibility(view.VISIBLE);
    }

    public void fechaEdit(View view) {
        fechaPerfil.setEnabled(true);
        fechaPerfil.setFocusableInTouchMode(true);
        fechaEdit.setVisibility(view.INVISIBLE);
        nombreEdit.setEnabled(false);
        FotoEdit.setEnabled(false);
        PasswordEdit.setEnabled(false);
        fechaSave.setVisibility(view.VISIBLE);
    }

    public void nombreSave(View view) {

        try {
            Map<String, Object> nombreUser = new HashMap<>();
            nombreUser.put("name", nombrePerfil.getText().toString().trim());

            mDatabase.child("users").child(user.getUid()).updateChildren(nombreUser);

            nombrePerfil.setEnabled(false);
            nombrePerfil.setFocusableInTouchMode(false);
            nombreEdit.setVisibility(view.VISIBLE);
            fechaEdit.setEnabled(true);
            FotoEdit.setEnabled(true);
            PasswordEdit.setEnabled(true);
            nombreSave.setVisibility(view.INVISIBLE);

            Toast.makeText(getApplicationContext(), "Nombre ingresado exitosamente", Toast.LENGTH_LONG).show();

        } catch (Exception err) {
            Toast.makeText(getApplicationContext(), "Error: no se guardo su nombre" + err, Toast.LENGTH_LONG).show();
        }


    }

    public void fechaSave(View view) {
        try {
            Map<String, Object> fechaNUser = new HashMap<>();
            fechaNUser.put("date", fechaPerfil.getText().toString().trim());

            mDatabase.child("users").child(user.getUid()).updateChildren(fechaNUser);

            fechaPerfil.setEnabled(false);
            fechaPerfil.setFocusableInTouchMode(false);
            fechaEdit.setVisibility(view.VISIBLE);
            nombreEdit.setEnabled(true);
            FotoEdit.setEnabled(true);
            PasswordEdit.setEnabled(true);
            fechaSave.setVisibility(view.INVISIBLE);
        } catch (Exception err) {
            Toast.makeText(getApplicationContext(), "Error: no se guardan" + err, Toast.LENGTH_LONG).show();
        }

    }

    public void AbrirGaleria() {
        Intent intentGaleria = new Intent();
        intentGaleria.setType("image/*");
        intentGaleria.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentGaleria, "Seleccione una imagen"), PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //muestra la imagen previa en un ImageView
        if (requestCode == PICK_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {

            mImgUriPerfil = data.getData();

            try {
                Bitmap bitmapPerfil = MediaStore.Images.Media.getBitmap(getContentResolver(), mImgUriPerfil);
                fotoPerfil.setImageBitmap(bitmapPerfil);
            } catch (IOException e) {
                Logger.getLogger("context" + e);
            }
        }

        //sube la imagen a firebase
        if (mImgUriPerfil != null) {
            mProgress = new ProgressDialog(this);
            mProgress.setTitle("Subiendo...");
            mProgress.show();

            final StorageReference refPhoto = mStorage.child("users/" + UUID.randomUUID().toString());
            refPhoto.putFile(mImgUriPerfil)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            mProgress.dismiss();
                            try {
                                refPhoto.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        UserProfileChangeRequest profileUpdateView = new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();

                                        user.updateProfile(profileUpdateView).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(PerfilUsuarioActivity.this, "Foto cambiada", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                });
                            } catch (Exception err) {
                                Toast.makeText(PerfilUsuarioActivity.this, "Erro: " + err, Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(PerfilUsuarioActivity.this, "Imagen de perfil cambiada exitosamente", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mProgress.dismiss();
                            Toast.makeText(PerfilUsuarioActivity.this, "Error al cargar imagen ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgress.setMessage("Subiendo " + (int) progress + "%");
                        }
                    });
        }
    }

    public void passwordEdit(View view) {
        PasswordEdit.setVisibility(View.INVISIBLE);
        FotoEdit.setEnabled(false);
        nombreEdit.setEnabled(false);
        fechaEdit.setEnabled(false);
        txtPasswordOld.setEnabled(true);
        txtPasswordOld.setFocusableInTouchMode(true);
        txtPasswordNew.setEnabled(true);
        txtPasswordNew.setFocusableInTouchMode(true);
        btnCambiarPassword.setEnabled(true);
    }

    public void passwordEditFinish(){
        PasswordEdit.setVisibility(View.VISIBLE);
        FotoEdit.setEnabled(true);
        nombreEdit.setEnabled(true);
        fechaEdit.setEnabled(true);
        txtPasswordOld.setEnabled(false);
        txtPasswordOld.setFocusableInTouchMode(false);
        txtPasswordNew.setEnabled(false);
        txtPasswordNew.setFocusableInTouchMode(false);
        btnCambiarPassword.setEnabled(false);
    }
}

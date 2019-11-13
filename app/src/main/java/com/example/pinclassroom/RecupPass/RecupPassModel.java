package com.example.pinclassroom.RecupPass;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RecupPassModel implements RecupPassInterface.Model{

    private RecupPassInterface.TaskListener listener;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public RecupPassModel(RecupPassInterface.TaskListener listener){
        this.listener = listener;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    @Override
    public void doRecupPass(String emailLogin) {
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(emailLogin).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if (!user.isEmailVerified())
                        listener.onError("No se puede restablecer su contraseña porque el correo "+ user.getEmail() +" aun no se a verificado aun.");
                    else {
                        listener.onSucess();
                        listener.onError("Se ha enviado el correo para restablecer su contraseña");
                    }
                }else{
                    listener.onError(task.getException().getMessage());
                }
            }
        });
    }
}

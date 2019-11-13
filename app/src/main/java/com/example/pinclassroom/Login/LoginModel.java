package com.example.pinclassroom.Login;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginModel implements LoginInterface.Model {

    private LoginInterface.TaskListener listener;
    private FirebaseAuth mAuth;
    private FirebaseUser user;


    public LoginModel(LoginInterface.TaskListener listener){
        this.listener = listener;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    @Override
    public void doLogin(String emailLogin, final String passwordLogin) {
        mAuth.signInWithEmailAndPassword(emailLogin, passwordLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if (!user.isEmailVerified()) {
                        listener.onError("El correo " + user.getEmail() + " aun no se a verificado.");
                    } else {
                        listener.onSucess();
                    }
                } else {
                    if (task.getException().getMessage()!=null)
                        listener.onError(task.getException().getMessage());


                }
            }
        });
    }
}

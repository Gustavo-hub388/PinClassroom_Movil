package com.example.pinclassroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.data.DataBufferRef;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mehdi.sakout.fancybuttons.FancyButton;

public class AyudaMejorarActivity extends AppCompatActivity implements OnClickListener{

    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    EditText reciep, sub, msg;
    String rec, subject, textMessage;



    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mdatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda_mejorar);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        context = this;

        FancyButton login = findViewById(R.id.btn_submit);
        //reciep = (EditText) findViewById(R.id.et_to);
        //sub = (EditText) findViewById(R.id.et_sub);
        msg = (EditText) findViewById(R.id.et_text);
        //sub.setText(user.getEmail());

        login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        rec = "pinclassroom.2019@gmail.com";
        subject = user.getEmail();
        textMessage = msg.getText().toString();

        if (TextUtils.isEmpty(msg.getText().toString())) {
            //Toast.makeText(this, "No es una contraseña válida",Toast.LENGTH_SHORT).show();
            msg.setError("Escribenos tu opinión");
        } else {

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("pinclassroom.2019@gmail.com", "classroom.gmail2019");
                }
            });

            pdialog = ProgressDialog.show(context, "", "Enviando mensaje...", true);

            RetreiveFeedTask task = new RetreiveFeedTask();
            task.execute();
        }
    }

    public void IrConfigAyuda(View view) {
        startActivity(new Intent(this, ConfigActivity.class));
    }

    private class RetreiveFeedTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("pinclassroom.2019@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();
            finish();
            Toast.makeText(getApplicationContext(), "Tu mensaje fue enviado", Toast.LENGTH_LONG).show();
        }
    }
}

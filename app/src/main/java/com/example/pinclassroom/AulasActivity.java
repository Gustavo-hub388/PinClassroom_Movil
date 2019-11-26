package com.example.pinclassroom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinclassroom.Aulas.AulaObjetos;
import com.example.pinclassroom.Aulas.AulaPerfilActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AulasActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    private RecyclerView mAulasRecycler;
    private FirebaseRecyclerAdapter<AulaObjetos, AulasViewHolder> mAulasAdapter;

    TabHost contenedor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aulas);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Espero un momento...");
        mProgress.setCancelable(false);
        mProgress.show();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("aulas");
        mDatabase.keepSynced(true);

        contenedor = findViewById(R.id.contenedor_tabs);
        contenedor.setup();

        TabHost.TabSpec spec1 = contenedor.newTabSpec("Aulas");
        spec1.setContent(R.id.tab_aula);
        spec1.setIndicator("Aula");
        contenedor.addTab(spec1);

        TabHost.TabSpec spec2 = contenedor.newTabSpec("Mapas");
        spec2.setContent(R.id.tab_mapa);
        spec2.setIndicator("Mapa");
        contenedor.addTab(spec2);

        contenedor.setCurrentTab(0);
//-------------------------------------------------------------------------------------------------


        mAulasRecycler = (RecyclerView) findViewById(R.id.RecyclerAulas);

        DatabaseReference aulasRef = FirebaseDatabase.getInstance().getReference().child("aulas");
        Query aulasQuery = aulasRef.orderByKey();

        mAulasRecycler.hasFixedSize();
        mAulasRecycler.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<AulaObjetos>().setQuery(aulasQuery, AulaObjetos.class).build();

        mAulasAdapter = new FirebaseRecyclerAdapter<AulaObjetos, AulasActivity.AulasViewHolder>(personsOptions) {

            @Override
            protected void onBindViewHolder(final AulasActivity.AulasViewHolder holder, final int position, final AulaObjetos model) {

                final String AulasID = getRef(position).getKey();
                mDatabase.child(AulasID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        try {
                            if (dataSnapshot.exists()){
                                final String retName = dataSnapshot.child("name").getValue().toString();
                                final String retEdificio = dataSnapshot.child("edificio").getValue().toString();
                                final String retPlanta = dataSnapshot.child("planta").getValue().toString();
                                final String retCapacidad = dataSnapshot.child("capacidad").getValue().toString();
                                //final String retImage = dataSnapshot.child("images").child("0").getValue().toString();

                                holder.setName(model.getName());
                                holder.setEdificio(model.getEdificio());
                                //holder.setImage(getBaseContext(), model.getImage());
                                mProgress.dismiss();
                                holder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent intentAulaPerfil = new Intent(getApplicationContext(), AulaPerfilActivity.class);
                                        intentAulaPerfil.putExtra("vistaAulaID", AulasID);
                                        intentAulaPerfil.putExtra("vistaAulaNombre", retName);
                                        intentAulaPerfil.putExtra("vistaAulaEdificio", retEdificio);
                                        intentAulaPerfil.putExtra("vistaAulaPlanta", retPlanta);
                                        intentAulaPerfil.putExtra("vistaAulaCapacidad", retCapacidad);
                                        //intentAulaPerfil.putExtra("vistaAulaFoto", retImage);
                                        startActivity(intentAulaPerfil);
                                    }
                                });
                            }
                        } catch (Exception e) {
                            Toast.makeText(AulasActivity.this,"Error: "+e,Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public AulasActivity.AulasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_lista_aulas, parent, false);

                return new AulasActivity.AulasViewHolder(view);
            }
        };
        mAulasRecycler.setAdapter(mAulasAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAulasAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAulasAdapter.stopListening();


    }

    public static class AulasViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public AulasViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name) {
            TextView nombre_aula = (TextView) mView.findViewById(R.id.nombre_aula);
            nombre_aula.setText(name);
        }

        public void setEdificio(String edificio) {
            TextView edificio_aula = (TextView) mView.findViewById(R.id.edificio_aula);
            edificio_aula.setText(edificio);
        }
        /*public void setImage(Context ctx, String images){
            ImageView post_image = (ImageView) mView.findViewById(R.id.foto_aula);
            Picasso.with(ctx).load(images).into(post_image);
        }*/
    }
}


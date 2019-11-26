package com.example.pinclassroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinclassroom.Aulas.AulaObjetos;
import com.example.pinclassroom.Aulas.AulaPerfilActivity;
import com.example.pinclassroom.Docentes.DocenteObjetos;
import com.example.pinclassroom.Docentes.DocentePerfilActivity;
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

import java.util.ArrayList;
import java.util.List;

public class DocentesActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    private RecyclerView mDocentesRecycler;
    private FirebaseRecyclerAdapter<DocenteObjetos, DocenteViewHolder> mDocentesAdapter;

    TabHost contenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docentes);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Espero un momento...");
        mProgress.setCancelable(false);
        mProgress.show();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("teachers");
        mDatabase.keepSynced(true);

        //recyclerView = findViewById(R.id.recycler_lista_docentes);

        contenedor = findViewById(R.id.contenedor_tabs1);
        contenedor.setup();

        TabHost.TabSpec spec1 = contenedor.newTabSpec("Docentes");
        spec1.setContent(R.id.tab_docente);
        spec1.setIndicator("Docentes");
        contenedor.addTab(spec1);

        TabHost.TabSpec spec2 = contenedor.newTabSpec("Favoritos");
        spec2.setContent(R.id.tab_fav_docente);
        spec2.setIndicator("Favoritos");
        contenedor.addTab(spec2);

        contenedor.setCurrentTab(0);
//-------------------------------------------------------------------------------------------------


        mDocentesRecycler = (RecyclerView) findViewById(R.id.RecyclerDocentes);

        DatabaseReference docentesRef = FirebaseDatabase.getInstance().getReference().child("teachers");
        Query docentesQuery = docentesRef.orderByKey();

        mDocentesRecycler.hasFixedSize();
        mDocentesRecycler.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<DocenteObjetos>().setQuery(docentesQuery, DocenteObjetos.class).build();

        mDocentesAdapter = new FirebaseRecyclerAdapter<DocenteObjetos, DocentesActivity.DocenteViewHolder>(personsOptions) {

            @Override
            protected void onBindViewHolder(final DocentesActivity.DocenteViewHolder holder, final int position, final DocenteObjetos model) {

                final String DocentesID = getRef(position).getKey();
                mDatabase.child(DocentesID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        try {
                            if (dataSnapshot.exists()){
                                final String retName = dataSnapshot.child("nombre").getValue().toString();
                                final String retFacultad = dataSnapshot.child("facultad").getValue().toString();
                                final String retInfoAcad = dataSnapshot.child("historial").child("0").getValue().toString();
                                final String retContactos = dataSnapshot.child("contacto").child("0").getValue().toString();
                                //final String retImage = dataSnapshot.child("images").child("0").getValue().toString();

                                holder.setNombre(model.getNombre());
                                holder.setFacultad(model.getFacultad());
                                //holder.setImage(getBaseContext(), model.getImage());
                                mProgress.dismiss();
                                holder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent intentDocentePerfil = new Intent(getApplicationContext(), DocentePerfilActivity.class);
                                        intentDocentePerfil.putExtra("vistaDocenteID", DocentesID);
                                        intentDocentePerfil.putExtra("vistaDocenteNombre", retName);
                                        intentDocentePerfil.putExtra("vistaDocenteFacultad", retFacultad);
                                        intentDocentePerfil.putExtra("vistaDocenteInfoAcad", retInfoAcad);
                                        intentDocentePerfil.putExtra("vistaDocenteContacto", retContactos);
                                        //intentDocentePerfil.putExtra("vistaAulaFoto", retImage);
                                        startActivity(intentDocentePerfil);
                                    }
                                });
                            }
                        } catch (Exception e) {
                            Toast.makeText(DocentesActivity.this,"Error: "+e,Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public DocentesActivity.DocenteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_lista_docentes, parent, false);

                return new DocentesActivity.DocenteViewHolder(view);
            }
        };
        mDocentesRecycler.setAdapter(mDocentesAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        mDocentesAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mDocentesAdapter.stopListening();


    }

    public static class DocenteViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public DocenteViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setNombre(String nombre) {
            TextView nombre_docente = (TextView) mView.findViewById(R.id.nombre_docente);
            nombre_docente.setText(nombre);
        }

        public void setFacultad(String facultad) {
            TextView facultad_docente = (TextView) mView.findViewById(R.id.facultad_docente);
            facultad_docente.setText(facultad);
        }
        /*public void setImage(Context ctx, String images){
            ImageView post_image = (ImageView) mView.findViewById(R.id.foto_aula);
            Picasso.with(ctx).load(images).into(post_image);
        }*/
    }
}

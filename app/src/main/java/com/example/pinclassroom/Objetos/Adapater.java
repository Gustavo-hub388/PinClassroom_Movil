package com.example.pinclassroom.Objetos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pinclassroom.R;

import java.util.List;

public class Adapater extends RecyclerView.Adapter<Adapater.DocentesViewHolder>{

    List<Docentes> docente;

    public Adapater(List<Docentes> docente) {
        this.docente = docente;
    }

    @NonNull
    @Override
    public DocentesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_lista_docentes, parent, false);
        DocentesViewHolder holder = new DocentesViewHolder(view );

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DocentesViewHolder holder, int position) {
        Docentes docentes = docente.get(position);
        holder.textViewDocente.setText(docentes.getNombre());
        holder.textViewFacultad.setText(docentes.getFacultad());
    }

    @Override
    public int getItemCount() {
        return docente.size();
    }

    public static class DocentesViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDocente, textViewFacultad;

        public DocentesViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDocente = itemView.findViewById(R.id.txt_docente);
            textViewFacultad = itemView.findViewById(R.id.txt_facultad);
        }
    }
}

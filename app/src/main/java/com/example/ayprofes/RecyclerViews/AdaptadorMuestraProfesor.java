package com.example.ayprofes.RecyclerViews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayprofes.ProfesorActivity;
import com.example.ayprofes.R;

import java.util.ArrayList;

public class AdaptadorMuestraProfesor extends RecyclerView.Adapter<AdaptadorMuestraProfesor.MuestraProfeViewHolder> {

    ArrayList<MuestraProfesor> profesores;


    public AdaptadorMuestraProfesor(Context context, ArrayList<MuestraProfesor> profesores){
        this.profesores=profesores;
    }


    @NonNull
    @Override
    public MuestraProfeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_profesores,parent,false);
        return new MuestraProfeViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull MuestraProfeViewHolder holder, int position) {
        final MuestraProfesor profesor=profesores.get(position);
        holder.txtvCVNombre.setText(profesor.getNombre());
        holder.txtvCVMateria.setText(profesor.getMateria());
        holder.txtvCVCalificacion.setText(profesor.getCalificacion());

        holder.btnCVPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(v.getContext(), ProfesorActivity.class);
                v.getContext().startActivity(in);

            }
        });

    }

    @Override
    public int getItemCount() {
        return profesores.size();
    }

    public static class MuestraProfeViewHolder extends RecyclerView.ViewHolder{

        TextView txtvCVNombre;
        TextView txtvCVMateria;
        TextView txtvCVCalificacion;
        Button btnCVPerfil;

        public MuestraProfeViewHolder(@NonNull View itemView) {
            super(itemView);

            txtvCVNombre=itemView.findViewById(R.id.txtvCVNombre);
            txtvCVMateria=itemView.findViewById(R.id.txtvCVMateria);
            txtvCVCalificacion=itemView.findViewById(R.id.txtvCVCalificacion);
            btnCVPerfil=itemView.findViewById(R.id.btnCVPerfil);
        }
    }
}

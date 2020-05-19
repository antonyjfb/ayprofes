package com.example.ayprofes.RecyclerViews;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayprofes.ProfesorActivity;
import com.example.ayprofes.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;

public class AdaptadorMuestraProfesor extends FirestoreRecyclerAdapter<MuestraProfesor,AdaptadorMuestraProfesor.MuestraProfeViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdaptadorMuestraProfesor(@NonNull FirestoreRecyclerOptions<MuestraProfesor> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MuestraProfeViewHolder holder, int position, @NonNull final MuestraProfesor muestraProfesor) {

        holder.txtvCVNombre.setText(muestraProfesor.getNombre());
        holder.txtvCVMateria.setText(muestraProfesor.getMateria());
        holder.txtvCVCalificacion.setText(String.valueOf(muestraProfesor.getCalificacion()));
        holder.btnCVPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(v.getContext(),ProfesorActivity.class);
                Log.d("Transito", muestraProfesor.getNombre());
                in.putExtra("nombreProfe", muestraProfesor.getNombre());
                v.getContext().startActivity(in);
            }
        });
    }

    @NonNull
    @Override
    public MuestraProfeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_profesores,parent,false);
        return new MuestraProfeViewHolder(vista);
    }

    public class MuestraProfeViewHolder extends RecyclerView.ViewHolder{
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





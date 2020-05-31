package com.example.ayprofes.RecyclerViews;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayprofes.ManejoProfesores.ProfesorActivity;
import com.example.ayprofes.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

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

       //Asigna los datos al cardview
        holder.txtvCVMateria.setText("Materia: "+muestraProfesor.getMateria());
        holder.txtvCVCalificacion.setText("Calificación: "+String.valueOf(muestraProfesor.getCalificacion()));
        holder.btnCVPerfil.setText(muestraProfesor.getNombre());
        //Método de manejo del botón de profesor en cada cardview
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

    //Infla los cardview
    @NonNull
    @Override
    public MuestraProfeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_profesores,parent,false);
        return new MuestraProfeViewHolder(vista);
    }

    public class MuestraProfeViewHolder extends RecyclerView.ViewHolder{
        //Asigna los atributos al cardview
        TextView txtvCVMateria;
        TextView txtvCVCalificacion;
        Button btnCVPerfil;

        public MuestraProfeViewHolder(@NonNull View itemView) {
            super(itemView);

            txtvCVMateria=itemView.findViewById(R.id.txtvCVMateria);
            txtvCVCalificacion=itemView.findViewById(R.id.txtvCVCalificacion);
            btnCVPerfil=itemView.findViewById(R.id.btnCVPerfil);
        }
    }
}
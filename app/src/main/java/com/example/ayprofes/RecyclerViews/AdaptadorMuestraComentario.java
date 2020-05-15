package com.example.ayprofes.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayprofes.R;

import java.util.ArrayList;

public class AdaptadorMuestraComentario extends RecyclerView.Adapter<AdaptadorMuestraComentario.MuestraComentarioViewHolder> {

    ArrayList<MuestraComentario> comentarios;

    public AdaptadorMuestraComentario(Context context, ArrayList<MuestraComentario> comentarios){
        this.comentarios=comentarios;
    }

    @NonNull
    @Override
    public MuestraComentarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_comentario,parent,false);
        return new MuestraComentarioViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull MuestraComentarioViewHolder holder, int position) {
        MuestraComentario comentario=comentarios.get(position);
        holder.txtvCVComentario.setText(comentario.getComentario());

    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public static class MuestraComentarioViewHolder extends RecyclerView.ViewHolder{

        TextView txtvCVComentario;

        public MuestraComentarioViewHolder(@NonNull View itemView) {
            super(itemView);

            txtvCVComentario=itemView.findViewById(R.id.txtvCVComentario);
        }
    }
}

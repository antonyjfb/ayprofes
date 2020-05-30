package com.example.ayprofes.RecyclerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayprofes.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AdaptadorMuestraComentario extends FirestoreRecyclerAdapter<MuestraComentario,AdaptadorMuestraComentario.MuestraComentarioViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public AdaptadorMuestraComentario(@NonNull FirestoreRecyclerOptions<MuestraComentario> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MuestraComentarioViewHolder holder, int i, @NonNull MuestraComentario muestraComentario) {
        //Coloca el comentario obtenido de la base de datos en el cardview
        holder.txtvCVComentario.setText(muestraComentario.getComentario());
    }

    @NonNull
    @Override
    public MuestraComentarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Infla el cardview en el reciclerview
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_comentario,parent,false);
        return new MuestraComentarioViewHolder(vista);
    }






    public class MuestraComentarioViewHolder extends RecyclerView.ViewHolder{

        TextView txtvCVComentario;

        public MuestraComentarioViewHolder(@NonNull View itemView) {
            super(itemView);

            txtvCVComentario=itemView.findViewById(R.id.txtvCVComentario);
        }
    }
}

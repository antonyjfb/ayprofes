package com.example.ayprofes;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayprofes.RecyclerViews.AdaptadorMuestraProfesor;
import com.example.ayprofes.RecyclerViews.MuestraProfesor;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class BuscarActivity extends AppCompatActivity {

    RecyclerView rcvBuscarProfesor;
    FirebaseFirestore db;
    AdaptadorMuestraProfesor adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar); //activity main
        rcvBuscarProfesor=findViewById(R.id.rcvBuscarProfesor);
        LinearLayoutManager llm=new LinearLayoutManager(getBaseContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rcvBuscarProfesor.setLayoutManager(llm);
        db=FirebaseFirestore.getInstance();

        Query query=db.collection("Profesores");
        FirestoreRecyclerOptions<MuestraProfesor> firestoreRecyclerOptions=new FirestoreRecyclerOptions.Builder<MuestraProfesor>().setQuery(query,MuestraProfesor.class).build();
        adaptador=new AdaptadorMuestraProfesor(firestoreRecyclerOptions);
        adaptador.notifyDataSetChanged();
        rcvBuscarProfesor.setAdapter(adaptador);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adaptador.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptador.stopListening();
    }
}


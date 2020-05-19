package com.example.ayprofes;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayprofes.RecyclerViews.AdaptadorMuestraProfesor;
import com.example.ayprofes.RecyclerViews.MuestraProfesor;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class BuscarActivity extends AppCompatActivity {

    RecyclerView rcvBuscarProfesor;
    FirebaseFirestore db;
    AdaptadorMuestraProfesor adaptador;
    Spinner spnMaterias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar); //activity main
        rcvBuscarProfesor=findViewById(R.id.rcvBuscarProfesor);
        LinearLayoutManager llm=new LinearLayoutManager(getBaseContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rcvBuscarProfesor.setLayoutManager(llm);
        db=FirebaseFirestore.getInstance();

        //Adaptador para el spiner
        spnMaterias = findViewById(R.id.spnMaterias);
        ArrayAdapter<CharSequence> adaptadorSpinnerMaterias=ArrayAdapter.createFromResource(this,R.array.spnMaterias,android.R.layout.simple_spinner_item);
        spnMaterias.setAdapter(adaptadorSpinnerMaterias);

        spnMaterias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //
                //Codigo para realizar otra consulta a la base de datos, ahora con lo que el usuario selecciono
                //
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Consulta de la base de datos para llenar cardview
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


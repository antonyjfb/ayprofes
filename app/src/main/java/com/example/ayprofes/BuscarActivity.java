package com.example.ayprofes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayprofes.RecyclerViews.AdaptadorMuestraProfesor;
import com.example.ayprofes.RecyclerViews.MuestraProfesor;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BuscarActivity extends AppCompatActivity {

    RecyclerView rcvBuscarProfesor;
    FirebaseFirestore db;
    AdaptadorMuestraProfesor adaptador;
    Spinner spnMaterias;
    SearchView buscador;
    DatabaseReference databaseReference;
    private Toolbar toolbar;
    ArrayList<MuestraProfesor> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar); //activity main
        rcvBuscarProfesor=findViewById(R.id.rcvBuscarProfesor);
        LinearLayoutManager llm=new LinearLayoutManager(getBaseContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rcvBuscarProfesor.setLayoutManager(llm);
        db=FirebaseFirestore.getInstance();
        toolbar=findViewById(R.id.toolbar);/*
        try {
            db.collection("Profesores").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {


                                for (QueryDocumentSnapshot document : task.getResult()) {

                                }
                            } else {
                                Log.w("Hola", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Ha ocurrido un error, intente de nuevo", Toast.LENGTH_SHORT).show();
        }*/
        //Adaptador para el spiner
        spnMaterias = findViewById(R.id.spnMaterias);
        buscador=findViewById(R.id.searchView);
        ArrayAdapter<CharSequence> adaptadorSpinnerMaterias=ArrayAdapter.createFromResource(this,R.array.spnMaterias,android.R.layout.simple_spinner_item);
        spnMaterias.setAdapter(adaptadorSpinnerMaterias);
        arrayList = new ArrayList<>();
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

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Consulta de la base de datos para llenar cardview
        Query query=db.collection("Profesores");
        FirestoreRecyclerOptions<MuestraProfesor> firestoreRecyclerOptions=new FirestoreRecyclerOptions.Builder<MuestraProfesor>().setQuery(query,MuestraProfesor.class).build();
        adaptador=new AdaptadorMuestraProfesor(firestoreRecyclerOptions);
        adaptador.notifyDataSetChanged();
        rcvBuscarProfesor.setAdapter(adaptador);

        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //se oculta el EditText
                buscador.setQuery("", false);
                buscador.setIconified(true);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //do this
                if(!newText.isEmpty())
                {
                    search(newText);
                }
                else
                {
                    search("");
                }
                return true;
            }
            });
    }

    private void search(String newText) {
        Log.d("Search", newText);
        adaptador.stopListening();
        //Query query = db.collection("Profesores").whereEqualTo("nombre", newText);   // Si se quiere el nombre exacto
        Query query = db.collection("Profesores").whereGreaterThanOrEqualTo("nombre", newText);  //Si se quiere similar
        FirestoreRecyclerOptions<MuestraProfesor> firestoreRecyclerOptions=new FirestoreRecyclerOptions.Builder<MuestraProfesor>().setQuery(query,MuestraProfesor.class).build();
        adaptador=new AdaptadorMuestraProfesor(firestoreRecyclerOptions);
        adaptador.notifyDataSetChanged();
        rcvBuscarProfesor.setAdapter(adaptador);
        adaptador.startListening();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        switch (id){
            case R.id.ab_home:
                Intent in = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(in);
                break;
            case R.id.ab_login:
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}


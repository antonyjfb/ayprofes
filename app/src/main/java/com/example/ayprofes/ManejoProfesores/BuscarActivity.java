package com.example.ayprofes.ManejoProfesores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayprofes.MainActivity;
import com.example.ayprofes.ManejoUsuarios.LoginActivity;
import com.example.ayprofes.R;
import com.example.ayprofes.RecyclerViews.AdaptadorMuestraProfesor;
import com.example.ayprofes.RecyclerViews.MuestraProfesor;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class BuscarActivity extends AppCompatActivity {

    RecyclerView rcvBuscarProfesor;
    FirebaseFirestore db;
    AdaptadorMuestraProfesor adaptador;
    SearchView buscador;
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
        toolbar=findViewById(R.id.toolbar);
        buscador=findViewById(R.id.searchView);
        arrayList = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Consulta de la base de datos para llenar cardview
        Query query=db.collection("Profesores");
        FirestoreRecyclerOptions<MuestraProfesor> firestoreRecyclerOptions=new FirestoreRecyclerOptions.Builder<MuestraProfesor>().setQuery(query,MuestraProfesor.class).build();
        adaptador=new AdaptadorMuestraProfesor(firestoreRecyclerOptions);
        adaptador.notifyDataSetChanged();
        rcvBuscarProfesor.setAdapter(adaptador);

        //Método que maneja la searchView
        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //se oculta el EditText
                buscador.setQuery("", false);
                buscador.setIconified(true);
                return true;
            }
            //Al escribir en el campo de búsqueda se busca en las cardview con base a ello
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
    //Método de actualización de query con base al texto de búsqueda
    private void search(String newText) {
        Log.d("Search", newText);
        adaptador.stopListening();
        Query query = db.collection("Profesores").whereGreaterThanOrEqualTo("nombre", newText);  //Si se quiere similar
        FirestoreRecyclerOptions<MuestraProfesor> firestoreRecyclerOptions=new FirestoreRecyclerOptions.Builder<MuestraProfesor>().setQuery(query,MuestraProfesor.class).build();
        adaptador=new AdaptadorMuestraProfesor(firestoreRecyclerOptions);
        adaptador.notifyDataSetChanged();
        rcvBuscarProfesor.setAdapter(adaptador);
        adaptador.startListening();
    }

    //Método del recycleView
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

    //Métodos de creación de menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar,menu);
        ComprobarLinea(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        switch (id){
            case R.id.ab_home:
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
                break;
            case R.id.ab_login:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    //Método descrito anteriormente
    public void ComprobarLinea(final Menu menu)
    {
        SharedPreferences sharedPreferences=getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String usuarioShared=sharedPreferences.getString("Usuario","No hay info");
        if(usuarioShared=="No hay info"){
            MenuItem user = menu.getItem(1);
            user.setIcon(R.drawable.ic_person_outline_black_24dp);
        }
        else
        {
            MenuItem user = menu.getItem(1);
            user.setIcon(R.drawable.ic_person_black_24dp);
        }

    }

}


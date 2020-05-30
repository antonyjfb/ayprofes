package com.example.ayprofes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ayprofes.RecyclerViews.MuestraProfesor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NuevoProfeActivity extends AppCompatActivity {

    FirebaseFirestore db;
    MuestraProfesor profe;
    private Toolbar toolbar;

    //Antony se la come


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevoprofe); //activity main
        final EditText cuadroNombre, cuadroMateria;
        cuadroMateria = findViewById(R.id.edtNombreMateria);
        cuadroNombre = findViewById(R.id.edtNombreProfe);
        toolbar=findViewById(R.id.toolbar);

        Button submitButton = (Button) findViewById(R.id.btnEnviarComentario);
        // perform click event on button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get values and then displayed in a toast
                db = FirebaseFirestore.getInstance();
                profe = new MuestraProfesor(cuadroNombre.getText().toString(),cuadroMateria.getText().toString());
                Map<String, Object> filler = new HashMap<>();
                filler.put("nombre", profe.getNombre());
                filler.put("materia", profe.getMateria());
                db.collection("Profesores").document(profe.getNombre()).set(filler);
                Intent in=new Intent(v.getContext(),ComentarioActivity.class);
                in.putExtra("nombreProfe", profe.getNombre());
                v.getContext().startActivity(in);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

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

package com.example.ayprofes.ManejoProfesores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ayprofes.MainActivity;
import com.example.ayprofes.ManejoUsuarios.LoginActivity;
import com.example.ayprofes.R;
import com.example.ayprofes.RecyclerViews.MuestraProfesor;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NuevoProfeActivity extends AppCompatActivity {

    FirebaseFirestore db;
    MuestraProfesor profe;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevoprofe); //activity main
        final EditText cuadroNombre, cuadroMateria;
        cuadroMateria = findViewById(R.id.edtNombreMateria);
        cuadroNombre = findViewById(R.id.edtNombreProfe);
        toolbar=findViewById(R.id.toolbar);

        Button submitButton = (Button) findViewById(R.id.btnEnviarComentario);
        // Evento del botón enviar profesor
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Obtiene los valores de los edit text de nombre y materia del profesor a agregar y los añade a la base de datos si pasan la validación
                db = FirebaseFirestore.getInstance();
                profe = new MuestraProfesor(cuadroNombre.getText().toString(),cuadroMateria.getText().toString());
                Map<String, Object> filler = new HashMap<>();
                if(profe.getNombre().length()>3&&profe.getMateria().length()>3){
                    filler.put("nombre", profe.getNombre());
                    filler.put("materia", profe.getMateria());
                    db.collection("Profesores").document(profe.getNombre()).set(filler);
                    Intent in=new Intent(v.getContext(), ComentarioActivity.class);
                    in.putExtra("nombreProfe", profe.getNombre());
                    v.getContext().startActivity(in);
                }
                else{
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastIngresoNada), Toast.LENGTH_SHORT).show();
                }

            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    //Métodos siguientes comentados previamente
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

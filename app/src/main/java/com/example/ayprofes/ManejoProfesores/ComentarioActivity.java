 package com.example.ayprofes.ManejoProfesores;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ayprofes.MainActivity;
import com.example.ayprofes.ManejoUsuarios.LoginActivity;
import com.example.ayprofes.R;
import com.example.ayprofes.RecyclerViews.MuestraProfesor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ComentarioActivity extends AppCompatActivity {

    private long backPressedTime;

    FirebaseFirestore db;
    MuestraProfesor profe;
    TextView txtvProfesor;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);
        toolbar=findViewById(R.id.toolbar);
        final RatingBar facilidadRatingBar, claridadRatingBar, ayudaRatingBar, workloadRatingBar, recommendationRatingBar;
        final EditText comentarioBox;
        TextView nombreDelProfesor=findViewById(R.id.txtvProfesor);
        comentarioBox = findViewById(R.id.edtComentario);
        facilidadRatingBar = findViewById(R.id.facilidadRatingBar);
        claridadRatingBar = findViewById(R.id.claridadRatingBar);
        ayudaRatingBar = findViewById(R.id.ayudaRatingBar);
        workloadRatingBar = findViewById(R.id.workloadRatingBar);
        recommendationRatingBar = findViewById(R.id.recommendationRatingBar);
        Button submitButton = (Button) findViewById(R.id.btnEnviarComentario);
        Bundle bundle = getIntent().getExtras();
        txtvProfesor = findViewById(R.id.txtvProfesor);
        final String nombreProfe = bundle.getString("nombreProfe");
        nombreDelProfesor.setText(nombreProfe);
        // botón de agregar el comentario
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtiene los valores de las ratingBars y el comentario escrito y añade las cosas como un comentario a la base de datos
                db = FirebaseFirestore.getInstance();
                profe = new MuestraProfesor(comentarioBox.getText().toString(),claridadRatingBar.getRating(),facilidadRatingBar.getRating(),ayudaRatingBar.getRating(),workloadRatingBar.getRating(),recommendationRatingBar.getRating());
                String direccion = nombreProfe;

                    db.collection("Profesores").document(direccion).collection("Comentarios").add(profe).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Firebase", "DocumentSnapshot written with ID: " + documentReference.getId());
                            Toast.makeText(getApplicationContext(), "Se ha añadido el comentario correctamente", Toast.LENGTH_SHORT).show();
                            //Una vez agregado el comentario te regresa a la vista del profesor
                            Intent in = new Intent(ComentarioActivity.this, ProfesorActivity.class);
                            Bundle miBundle=new Bundle();
                            miBundle.putString("nombreProfe",txtvProfesor.getText().toString());
                            in.putExtras(miBundle);
                            startActivity(in);
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("Firebase", "Error adding document", e);
                                }
                            });

            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    //Métodos de creación del menu
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

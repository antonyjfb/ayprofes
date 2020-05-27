package com.example.ayprofes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RecoverActivity extends AppCompatActivity {
    //Atributos del html
    Button btnVerificar;
    Button btnNuevaContraseña;
    EditText edtRecuperarUsuario;
    EditText edtRespuestaContraseña;
    EditText edtNuevaContraseña;
    TextView txtvPregunta;
    Button btnOk;
    private Toolbar toolbar;

    //FireStore
    FirebaseFirestore db;

    //Atributos para los datos ingresados por el usuario
    String usuario;
    String nuevaContraseña;
    String respuesta;

    String nombreAux;
    String respuestaAux;
    String preguntaAux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperarcontrasena); //activity main

        btnVerificar = findViewById(R.id.btnVerificar);
        btnNuevaContraseña = findViewById(R.id.btnNuevaContraseña);
        edtRecuperarUsuario = findViewById(R.id.edtRecuperarUsuario);
        edtRespuestaContraseña  = findViewById(R.id.edtRespuestaContraseña);
        edtNuevaContraseña = findViewById(R.id.edtNuevaContraseña);
        txtvPregunta = findViewById(R.id.txtvPregunta);
        btnOk=findViewById(R.id.btnOK);
        toolbar=findViewById(R.id.toolbar);

        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = FirebaseFirestore.getInstance();

                usuario=edtRecuperarUsuario.getText().toString();

                try
                {
                    db.collection("Usuarios").document(usuario).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            nombreAux = documentSnapshot.getString("nombre");
                            preguntaAux = documentSnapshot.getString("pregunta");
                            respuestaAux = documentSnapshot.getString("respuesta");
                            if(nombreAux!=null) {
                                txtvPregunta.setVisibility(View.VISIBLE);
                                edtRespuestaContraseña.setVisibility(View.VISIBLE);
                                edtNuevaContraseña.setVisibility(View.VISIBLE);
                                btnNuevaContraseña.setVisibility(View.VISIBLE);

                                edtRecuperarUsuario.setEnabled(false);

                                btnVerificar.setVisibility(View.GONE);

                                txtvPregunta.setText(preguntaAux);

                                btnNuevaContraseña.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        respuesta=edtRespuestaContraseña.getText().toString();

                                        if(respuesta.equals(respuestaAux))
                                        {
                                            nuevaContraseña=edtNuevaContraseña.getText().toString();

                                            if (nuevaContraseña.length() < 9) {
                                                Toast.makeText(getApplicationContext(), "La contraseña debe tener al menos 10 caracteres", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                Usuario miUsuario = new Usuario(usuario, nuevaContraseña, preguntaAux, respuestaAux);
                                                db.collection("Usuarios").document(usuario).set(miUsuario).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                       edtRespuestaContraseña.setVisibility(View.GONE);
                                                        edtNuevaContraseña.setVisibility(View.GONE);
                                                        btnNuevaContraseña.setVisibility(View.GONE);
                                                        txtvPregunta.setText("Se ha cambiado la contraseña con exito");
                                                        btnOk.setVisibility(View.VISIBLE);
                                                        btnOk.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                Intent in=new Intent(v.getContext(),LoginActivity.class);
                                                                v.getContext().startActivity(in);
                                                            }
                                                        });


                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getApplicationContext(), "Fallo la conexión, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(), "La respuesta no coincide", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                });
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Ese nombre de usuario no existe", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "No ha ingresado usuario", Toast.LENGTH_SHORT).show();
                }

            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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



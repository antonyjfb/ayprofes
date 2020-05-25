package com.example.ayprofes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RecoverActivity extends AppCompatActivity {
    //Atributos del xml
    Button btnVerificar;
    Button btnNuevaContraseña;
    EditText edtRecuperarUsuario;
    EditText edtRespuestaContraseña;
    EditText edtNuevaContraseña;
    TextView txtvPregunta;
    Button btnOk;

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

        //Relacionamos atributos de java con su xml
        btnVerificar = findViewById(R.id.btnVerificar);
        btnNuevaContraseña = findViewById(R.id.btnNuevaContraseña);
        edtRecuperarUsuario = findViewById(R.id.edtRecuperarUsuario);
        edtRespuestaContraseña  = findViewById(R.id.edtRespuestaContraseña);
        edtNuevaContraseña = findViewById(R.id.edtNuevaContraseña);
        txtvPregunta = findViewById(R.id.txtvPregunta);
        btnOk=findViewById(R.id.btnOK);

        //Evento para verificar que el usuario existe
        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Verificar();
            }
        });

    }
    public void Verificar()
    {
        //Instancia de firebase
        db = FirebaseFirestore.getInstance();

        usuario=edtRecuperarUsuario.getText().toString();

        //Consulta a la base de datos para obtener un documento especifico
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

                                //Validacion de la respuesta de recuperación
                                if(respuesta.equals(respuestaAux))
                                {
                                    nuevaContraseña=edtNuevaContraseña.getText().toString();

                                    if (nuevaContraseña.length() < 9) {
                                        Toast.makeText(getApplicationContext(), "La contraseña debe tener al menos 10 caracteres", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Usuario miUsuario = new Usuario(usuario, nuevaContraseña, preguntaAux, respuestaAux);
                                        //Cambio de contraseña en la base de datos
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
}



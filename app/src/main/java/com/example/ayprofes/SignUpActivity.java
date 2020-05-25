package com.example.ayprofes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    //Atributos de la clase
    Button btnRegistrar;
    EditText edtUsuario;
    EditText edtContraseña;
    EditText edtContraseña2;
    Spinner spnPreguntas;
    EditText edtRespuesta;

    //Cadenas para obtener la informacion del usuario
    String usuario;
    String contraseña;
    String contraseña2;
    String pregunta;
    String respuesta;

    //Booleanos para validar la informacion del usuario
    boolean llaveNombre;
    boolean llaveContraseña;
    boolean llaveContraseña2;
    boolean llaveRespuesta;
    boolean llaveNombre2;
    boolean llavePregunta;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup); //activity main

        //Relacionamos los elementos de java con xml
        edtUsuario=findViewById(R.id.edtUsuario);
        edtContraseña=findViewById(R.id.edtContraseña);
        edtContraseña2=findViewById(R.id.edtContraseña2);
        edtRespuesta=findViewById(R.id.edtRespuesta);
        btnRegistrar =findViewById(R.id.btnRegistrar);
        spnPreguntas =findViewById(R.id.spnPreguntas);

        //Adaptador del spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.spnPreguntas,android.R.layout.simple_spinner_item);
        spnPreguntas.setAdapter(adapter);

        //Obtener el valor del spinner
        spnPreguntas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pregunta = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Evento del boton
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrar();
            }
        });
    }

    public void Registrar()
    {
        //Obtenermos los datos ingredaso por el usuario
        usuario = edtUsuario.getText().toString();
        contraseña = edtContraseña.getText().toString();
        contraseña2 = edtContraseña2.getText().toString();
        respuesta = edtRespuesta.getText().toString();

        //Inicializamos las variables
        llaveNombre = false;
        llaveNombre2 = false;
        llaveContraseña = false;
        llaveContraseña2 = false;
        llaveRespuesta = false;
        llavePregunta =false;

        db = FirebaseFirestore.getInstance();
        String id = edtUsuario.getText().toString();

        //Abrimos base de datos para validar que no existe el nombre de usuario
        try {
            db.collection("Usuarios").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String usuarioAux = documentSnapshot.getString("nombre");
                    if (usuario.equalsIgnoreCase(usuarioAux)) {
                        Toast.makeText(getApplicationContext(), "Ese nombre de usuario ya existe", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        llaveNombre2 = true;

                        //Validacion del nombre de usuario
                        if (usuario.length() < 9) {
                            Toast.makeText(getApplicationContext(), "El nombre de usuarios debe tener al menos 10 caracteres", Toast.LENGTH_SHORT).show();
                        } else {
                            llaveNombre = true;
                        }

                        //Validacion para el formato de la contraseña
                        if (contraseña.length() < 9) {
                            Toast.makeText(getApplicationContext(), "La contraseña debe tener al menos 10 caracteres", Toast.LENGTH_SHORT).show();
                        } else {
                            llaveContraseña = true;
                        }

                        //Validacion para confirmar contraseña
                        if (contraseña.equals(contraseña2)) {
                            llaveContraseña2 = true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        }

                        //Validar que selecciono una pregunta
                        if(pregunta.equals("Seleccione")) {
                            Toast.makeText(getApplicationContext(), "Selecione una pregunta", Toast.LENGTH_SHORT).show();
                        } else {
                            llavePregunta=true;
                        }

                        //Validar que respondio su pregunta
                        if (respuesta.length() < 1) {
                            Toast.makeText(getApplicationContext(), "No has contestardo  la pregunta", Toast.LENGTH_SHORT).show();
                        } else {
                            llaveRespuesta = true;
                        }

                        //Validamos todas las llaves necesarias para registrarse
                        if (llaveNombre == true && llaveContraseña == true && llaveRespuesta == true && llaveContraseña2 == true && llaveNombre2 == true && llavePregunta ==true) {
                            Usuario miUsuario = new Usuario(usuario, contraseña, pregunta, respuesta);
                            Log.d("Prueba", "Entro en el evento");
                            //Abrimos y escribimos en la base de datos el nuevo usuario
                            db.collection("Usuarios").document(usuario).set(miUsuario).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override

                                public void onSuccess(Void aVoid) {

                                    edtUsuario.setText("");
                                    edtContraseña.setText("");
                                    edtContraseña2.setText("");
                                    edtRespuesta.setText("");
                                    Toast.makeText(getApplicationContext(), "Se agregaron los datos correctamente", Toast.LENGTH_SHORT).show();

                                    //
                                    //Codigo para conectarse
                                    //

                                    Usuario miLinea = new Usuario(usuario);
                                    db.collection("Enlinea").document(usuario).set(miLinea).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(), "Ha iniciado sesión", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });

                                    Toast.makeText(getApplicationContext(), "Se ha iniciado sesión", Toast.LENGTH_SHORT).show();

                                    Intent in = new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(in);


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Prueba", "Entro en la base de datos");
                                    Toast.makeText(getApplicationContext(), "Hubo un problema con la conexion, vuelve a intentarlo", Toast.LENGTH_SHORT).show(); }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Hubo un problema con la conexion, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No has ingresado nada", Toast.LENGTH_SHORT).show();
        }


        //Intent para registrar
        // Intent in = new Intent(getApplicationContext(),BuscarActivity.class);
        // startActivity(in);
    }
}



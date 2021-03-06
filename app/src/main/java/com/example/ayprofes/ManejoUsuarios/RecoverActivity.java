package com.example.ayprofes.ManejoUsuarios;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.ayprofes.MainActivity;
import com.example.ayprofes.ManejoUsuarios.LoginActivity;
import com.example.ayprofes.ManejoUsuarios.Usuario;
import com.example.ayprofes.R;
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
        //Manejo de evento al presionar el botón de verifiación de pregunta
        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = FirebaseFirestore.getInstance();

                usuario=edtRecuperarUsuario.getText().toString();

                try
                {
                    //Obtiene los datos del usuario del cual se quiere recuperar la contraseña
                    db.collection("Usuarios").document(usuario).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            nombreAux = documentSnapshot.getString("nombre");
                            preguntaAux = documentSnapshot.getString("pregunta");
                            respuestaAux = documentSnapshot.getString("respuesta");
                            //Validacion de si existe el usuario indicado
                            if(nombreAux!=null) {
                                //Cambio de vista
                                txtvPregunta.setVisibility(View.VISIBLE);
                                edtRespuestaContraseña.setVisibility(View.VISIBLE);
                                edtNuevaContraseña.setVisibility(View.VISIBLE);
                                btnNuevaContraseña.setVisibility(View.VISIBLE);
                                edtRecuperarUsuario.setEnabled(false);
                                btnVerificar.setVisibility(View.GONE);
                                txtvPregunta.setText(preguntaAux);

                                //Manejo de evento al presionar el botón de restablecer contraseña
                                btnNuevaContraseña.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        respuesta=edtRespuestaContraseña.getText().toString();
                                        //Si la respuesta de verificación de usuario conincide con la escrita en la base
                                        //de datos nodifica la contraseña del usuario por la nueva
                                        if(respuesta.equals(respuestaAux))
                                        {
                                            nuevaContraseña=edtNuevaContraseña.getText().toString();
                                            if (nuevaContraseña.length() < 9) {
                                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastConstrasena10caracter), Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                //Si la nueva contraseñá cumple la validación se regresa la vista a la normalidad
                                                Usuario miUsuario = new Usuario(usuario, nuevaContraseña, preguntaAux, respuestaAux);
                                                db.collection("Usuarios").document(usuario).set(miUsuario).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                       edtRespuestaContraseña.setVisibility(View.GONE);
                                                        edtNuevaContraseña.setVisibility(View.GONE);
                                                        btnNuevaContraseña.setVisibility(View.GONE);
                                                        txtvPregunta.setText(getResources().getString(R.string.toastCambioConstraseña));
                                                        btnOk.setVisibility(View.VISIBLE);
                                                        btnOk.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                Intent in=new Intent(v.getContext(), LoginActivity.class);
                                                                v.getContext().startActivity(in);
                                                            }
                                                        });


                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastProblemaConexion), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.toastRespuestaNoCoincide), Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                });
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastNombreUsuarioNoExiste), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastNoingresoUsuario), Toast.LENGTH_SHORT).show();
                }

            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    //Métodos de creación de menu explicados previamente
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
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    //Método explicado previamente
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



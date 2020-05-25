package com.example.ayprofes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    //Atributos
    EditText edtUsuario;
    EditText edtContraseña;
    Button btnEntrar;
    Button btnLogin;
    Button btnCerrar;
    TextView linkOlvide;
    TextView txtvUsuario;

    FirebaseFirestore db;

    String usuario;
    String contraseña;
    String usuarioAux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); //activity main

        //Relacionamos atributos de java al xml
        edtUsuario = findViewById(R.id.edtUsuarioLogin);
        edtContraseña = findViewById(R.id.edtContraseñaLogin);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnLogin = findViewById(R.id.btnCrear);
        btnCerrar=findViewById(R.id.btnCerrar);
        linkOlvide = findViewById(R.id.txtvOlvide);
        txtvUsuario=findViewById(R.id.txtvUsuario);

        //Comprueba si el usuario esta en linea
        ComprobarLinea();
        /*SharedPreferences sharedPreferences=getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        usuario=sharedPreferences.getString("Usuario","No hay info");*/

        //Evento para iniciar sesión
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IniciarSesion();
            }
        });

        //Evento para registrarse
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abre el SingUpActivity
                Intent in = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(in);
            }
        });

        //Evento para olvido de contraseña
        linkOlvide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abre el RecoverActivity
                Intent in = new Intent(getApplicationContext(),RecoverActivity.class);
                startActivity(in);
            }
        });

        //Evento para cerrar sesión
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CerrarSesion();
            }
        });
    }

    public void ComprobarLinea()
    {
        //Instancia de base de datos
        db = FirebaseFirestore.getInstance();

        try {
            //Consulta a la base de datos
            db.collection("Enlinea")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                //For que recorre los documentos de nuestra colección
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    usuarioAux =document.getData().get("nombre").toString();
                                    }

                                //Validación de la existencia de un usuario en linea
                                if(usuarioAux==null) {
                                    btnCerrar.setVisibility(View.GONE);
                                    txtvUsuario.setVisibility(View.GONE);

                                }
                                else
                                {
                                    edtUsuario.setVisibility(View.GONE);
                                    edtContraseña.setVisibility(View.GONE);
                                    btnEntrar.setVisibility(View.GONE);
                                    btnLogin.setVisibility(View.GONE);
                                    linkOlvide.setVisibility(View.GONE);
                                    txtvUsuario.setText(usuarioAux);
                                    txtvUsuario.setEnabled(false);
                                }

                            } else {
                                Log.w("Hola", "Error getting documents.", task.getException());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Ha ocurrido un error, intente de nuevo", Toast.LENGTH_SHORT).show();
        }
        /*
        db = FirebaseFirestore.getInstance();
        db.collection("Enlinea").document().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String usuarioAux = documentSnapshot.getString("nombre");
                Toast.makeText(getApplicationContext(), usuarioAux, Toast.LENGTH_SHORT).show();
                if(usuarioAux==null) {
                    btnCerrar.setVisibility(View.GONE);
                    txtvUsuario.setVisibility(View.GONE);

                }
                else
                {
                    edtUsuario.setVisibility(View.GONE);
                    edtContraseña.setVisibility(View.GONE);
                    btnEntrar.setVisibility(View.GONE);
                    btnLogin.setVisibility(View.GONE);
                    linkOlvide.setVisibility(View.GONE);
                    txtvUsuario.setText(usuarioAux);
                    txtvUsuario.setEnabled(false);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
*/
    }

    public void CerrarSesion()
    {
        /* SharedPreferences sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Usuario","No hay nadie chavo");
                editor.commit();
*/
        //Instancia de la base de datos
        db = FirebaseFirestore.getInstance();
        //Elimina el documento correspondiente que valida si el usuario esta en linea
        db.collection("Enlinea").document(txtvUsuario.getText().toString()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Se ha cerrado sesión", Toast.LENGTH_SHORT).show();

                btnCerrar.setVisibility(View.GONE);
                txtvUsuario.setVisibility(View.GONE);

                edtUsuario.setVisibility(View.VISIBLE);
                edtContraseña.setVisibility(View.VISIBLE);
                btnEntrar.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.VISIBLE);
                linkOlvide.setVisibility(View.VISIBLE);
                txtvUsuario.setText("");
                txtvUsuario.setEnabled(true);

                edtUsuario.setText("");
                edtContraseña.setText("");
            }
        });
    }

    public void IniciarSesion()
    {
        usuario=edtUsuario.getText().toString();
        contraseña=edtContraseña.getText().toString();

        //Instancia de la base de datos
        db = FirebaseFirestore.getInstance();
        String id=edtUsuario.getText().toString();


        //Valida que el usuario y contraseña existen y coincidan para iniciar sesión
        try {
            db.collection("Usuarios").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String usuarioAux = documentSnapshot.getString("nombre");
                    if(usuarioAux!="")
                    {
                        String contraseñaAux = documentSnapshot.getString("contraseña");
                        try {
                            if (contraseñaAux.equals(contraseña)) {
                                Toast.makeText(getApplicationContext(), "Ha iniciado sesion, Bienvenido "+usuarioAux, Toast.LENGTH_SHORT).show();

                                Usuario miLinea = new Usuario(usuario);
                                //Inicia sesion a travez de la base de datos
                                db.collection("Enlinea").document(usuario).set(miLinea).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        edtUsuario.setVisibility(View.GONE);
                                        edtContraseña.setVisibility(View.GONE);
                                        btnEntrar.setVisibility(View.GONE);
                                        btnLogin.setVisibility(View.GONE);
                                        linkOlvide.setVisibility(View.GONE);
                                        txtvUsuario.setText(usuario);
                                        txtvUsuario.setEnabled(false);

                                        btnCerrar.setVisibility(View.VISIBLE);
                                        txtvUsuario.setVisibility(View.VISIBLE);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });


                            } else {
                                Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Ese nombre de usuario no existe", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Ese nombre de usuario no existe", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Ingrese los datos", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.ayprofes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
        edtUsuario = findViewById(R.id.edtUsuarioLogin);
        edtContraseña = findViewById(R.id.edtContraseñaLogin);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnLogin = findViewById(R.id.btnCrear);
        btnCerrar=findViewById(R.id.btnCerrar);
        linkOlvide = findViewById(R.id.txtvOlvide);
        txtvUsuario=findViewById(R.id.txtvUsuario);

        ComprobarLinea();
       /* SharedPreferences sharedPreferences=getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        usuario=sharedPreferences.getString("Usuario","No hay info");
*/



        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario=edtUsuario.getText().toString();
                contraseña=edtContraseña.getText().toString();

                db = FirebaseFirestore.getInstance();
                final String id=edtUsuario.getText().toString();


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
                                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastHainiciadoSesion) +usuarioAux, Toast.LENGTH_SHORT).show();

                                        Usuario miLinea = new Usuario(usuario);

                                        SharedPreferences sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
                                        String usuarioShared=edtUsuario.getText().toString();
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("Usuario",usuarioShared);
                                        editor.commit();
                                        Intent in = new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(in);


                                    } else {
                                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastWrongPass), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastNombreUsuarioNoExiste), Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastNombreUsuarioNoExiste), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastIngreseDatos), Toast.LENGTH_SHORT).show();
                }


                //Intent in = new Intent(getApplicationContext(), BuscarActivity.class);
                //startActivity(in);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(in);
            }
        });
        linkOlvide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),RecoverActivity.class);
                startActivity(in);
            }
        });
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();


                db = FirebaseFirestore.getInstance();
                db.collection("Enlinea").document(txtvUsuario.getText().toString()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastSeCerroSesion), Toast.LENGTH_SHORT).show();

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
        });
    }

    public void ComprobarLinea()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String usuarioShared=sharedPreferences.getString("Usuario","No hay info");
        if(usuarioShared=="No hay info"){
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
                txtvUsuario.setText(usuarioShared);
                txtvUsuario.setEnabled(false);
            }

    }
}

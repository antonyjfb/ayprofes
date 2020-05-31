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
import com.example.ayprofes.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsuario;
    EditText edtContraseña;
    Button btnEntrar;
    Button btnLogin;
    Button btnCerrar;
    TextView linkOlvide;
    TextView txtvUsuario;
    private Toolbar toolbar;

    FirebaseFirestore db;

    String usuario;
    String contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUsuario = findViewById(R.id.edtUsuarioLogin);
        edtContraseña = findViewById(R.id.edtContraseñaLogin);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnLogin = findViewById(R.id.btnCrear);
        btnCerrar=findViewById(R.id.btnCerrar);
        linkOlvide = findViewById(R.id.txtvOlvide);
        txtvUsuario=findViewById(R.id.txtvUsuario);
        toolbar=findViewById(R.id.toolbar);
        //Evento del botón entrar que inicia sesión
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario=edtUsuario.getText().toString();
                contraseña=edtContraseña.getText().toString();

                db = FirebaseFirestore.getInstance();
                final String id=edtUsuario.getText().toString();
                //Conecta a la base de datos para verificar el usuario y contraseña
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
                                        //Si se valida el inicio de sesión guarda las credenciales del usuario en el celular para su uso en otros activities
                                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastHainiciadoSesion) +usuarioAux, Toast.LENGTH_SHORT).show();
                                        SharedPreferences sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
                                        String usuarioShared=edtUsuario.getText().toString();
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("Usuario",usuarioShared);
                                        editor.commit();
                                        //Una vez iniciada sesión te regresa a la vista principal
                                        Intent in = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(in);


                                    } else {
                                        //Si la contraseña es incorrecta pero el usuario existe te indica que la contraseña es incorrecta
                                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastWrongPass), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    //Si falla el try
                                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastNombreUsuarioNoExiste), Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                //Si el usuario no existe entonces te lo indica
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastNombreUsuarioNoExiste), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    //Si no hay datos en los campos
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastIngreseDatos), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Evento de boton para ir a la vista de crear una cuenta
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(in);
            }
        });
        //Evento de boton para ir a la vista de recuperar tu contraseña
        linkOlvide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), RecoverActivity.class);
                startActivity(in);
            }
        });

        //Evento de botón que cierra la sesión
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Elimina las credenciales del usuario guardadas en el celular y cambia la vista
                SharedPreferences sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    //Si hay un usuario con sesión ya iniciada cambia el aspecto del layout
    public void ComprobarLinea(final Menu menu)
    {
        SharedPreferences sharedPreferences=getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String usuarioShared=sharedPreferences.getString("Usuario","No hay info");
        if(usuarioShared=="No hay info"){
                btnCerrar.setVisibility(View.GONE);
                txtvUsuario.setVisibility(View.GONE);
            MenuItem user = menu.getItem(1);
            user.setIcon(R.drawable.ic_person_outline_black_24dp);

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

                MenuItem user = menu.getItem(1);
                user.setIcon(R.drawable.ic_person_black_24dp);
            }

    }

    //Funciones de menu previamente ya descritas
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
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
}

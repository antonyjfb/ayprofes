package com.example.ayprofes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ayprofes.ManejoProfesores.BuscarActivity;
import com.example.ayprofes.ManejoProfesores.ComentarioActivity;
import com.example.ayprofes.ManejoProfesores.NuevoProfeActivity;
import com.example.ayprofes.ManejoUsuarios.LoginActivity;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //activity main
        Button btnBuscar, btnLogin, btnAgregar;
        btnBuscar = findViewById(R.id.btnMainBuscar);
        btnLogin = findViewById(R.id.btnMainLogin);
        btnAgregar = findViewById(R.id.btnAgregarProfe);
        menu=findViewById(R.id.menu);
        toolbar=findViewById(R.id.toolbar);
        //Botón buscar manda al buscador de profesores
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), BuscarActivity.class);
                startActivity(in);
            }
        });

        //Boton login te manda a la vista para iniciar sesión o crear cuenta
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(in);
            }
        });
        //Boton agregar te manda a la vista de agregar nuevo profesor
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences=getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
                String usuarioShared=sharedPreferences.getString("Usuario","No hay info");

                //Si no existe una sesión iniciada no permite añadir un nuevo profesor
                if(usuarioShared=="No hay info") {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastRequiereSesionComentar), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent in = new Intent(getApplicationContext(), NuevoProfeActivity.class);
                    startActivity(in);
                }
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    //Ambos métodos sirven para crear el menu superrior
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar,menu);
        ComprobarLinea(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        //Botones del menu para ir a la vista principal o a la vista de login de manera rápida
        switch (id){
            case R.id.ab_home:

                break;
            case R.id.ab_login:
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    //Comprueba si hay un usuario conectado en el celular y con base a esto decide si poner el ícono de usuario del menú relleno o no
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
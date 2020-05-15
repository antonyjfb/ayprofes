package com.example.ayprofes;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayprofes.RecyclerViews.AdaptadorMuestraProfesor;
import com.example.ayprofes.RecyclerViews.MuestraProfesor;

import java.util.ArrayList;

public class BuscarActivity extends AppCompatActivity {

    RecyclerView rcvBuscarProfesor;
    ArrayList<MuestraProfesor> profesores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar); //activity main
        rcvBuscarProfesor=findViewById(R.id.rcvBuscarProfesor);
        LinearLayoutManager llm=new LinearLayoutManager(getBaseContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rcvBuscarProfesor.setLayoutManager(llm);
        InicializarProfesores();
        InicializarAdaptador();
    }

    private void InicializarAdaptador(){
        AdaptadorMuestraProfesor adaptador=new AdaptadorMuestraProfesor(this.getApplicationContext(),profesores);
        rcvBuscarProfesor.setAdapter(adaptador);
    }

    private void InicializarProfesores() {
        profesores=new ArrayList<>();
        profesores.add(new MuestraProfesor("María del Pilar Corona Lira","Electrónica Básica","Calificación promedio: 9.5"));
        profesores.add(new MuestraProfesor("Jorge Armando Rodríguez Vera", "Temas Selectos de Programación", "Calificación promedio: 9.8"));
        profesores.add(new MuestraProfesor("Francisco Cuenca Jimenez","Mecanismos", "Calificación promedio: 9"));
        profesores.add(new MuestraProfesor("Alejandro César López Bolaños", "Introducción a la economía", "Calificación promedio: 8.7"));
        profesores.add(new MuestraProfesor("Armando Sánchez Guzmán","Ingeniería de Manufactura", "Calificación promedio: 8"));
        profesores.add(new MuestraProfesor("Álvaro Núñez Flores", "Modelado de sistemas físicos","Calificación promedio: 8.5"));
    }
}


package com.example.ayprofes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ayprofes.RecyclerViews.AdaptadorMuestraComentario;
import com.example.ayprofes.RecyclerViews.MuestraComentario;

import java.util.ArrayList;

public class ProfesorActivity extends AppCompatActivity {

    RecyclerView rcvComentario;
    ArrayList<MuestraComentario> comentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);
        rcvComentario=findViewById(R.id.rcvComentario);
        LinearLayoutManager llm=new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rcvComentario.setLayoutManager(llm);
        InicializarComentarios();
        InicializarAdaptador();

        TextView txtvCarga, txtvRecomendacion, txtvAyuda, txtvClaridad, txtvNombre, txtvProfesor,txtvFacilidad;
        Button btnAñadir;
        btnAñadir=findViewById(R.id.btnAñadir);
        txtvCarga=findViewById(R.id.txtvCarga);
        txtvRecomendacion=findViewById(R.id.txtvRecomendacion);
        txtvAyuda=findViewById(R.id.txtvAyuda);
        txtvClaridad=findViewById(R.id.txtvClaridad);
        txtvNombre=findViewById(R.id.txtvNombre);
        txtvProfesor=findViewById(R.id.txtvProfesor);
        txtvFacilidad=findViewById(R.id.txtvFacilidad);
        Bundle bundle = getIntent().getExtras();
        String nombreProfe = bundle.getString("nombreProfe");
        txtvProfesor.setText(nombreProfe);


        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),ComentarioActivity.class);
                Bundle bundle = getIntent().getExtras();
                String nombreProfe = bundle.getString("nombreProfe");
                Log.d("Transito",nombreProfe);
                in.putExtra("nombreProfe", nombreProfe);
                startActivity(in);
            }
        });

    }

    private void InicializarAdaptador() {
        AdaptadorMuestraComentario adaptador=new AdaptadorMuestraComentario(this.getApplicationContext(),comentarios);
        rcvComentario.setAdapter(adaptador);
    }

    private void InicializarComentarios() {
        comentarios=new ArrayList<>();
        comentarios.add(new MuestraComentario("Muy buen profesor"));
        comentarios.add(new MuestraComentario("Este profesor deja mucha tarea"));
        comentarios.add(new MuestraComentario("Sus exámenes son fáciles pero muy largos"));
        comentarios.add(new MuestraComentario("El profe siempre llega a tiempo y sus clases son muy divertidas"));
        comentarios.add(new MuestraComentario("Sólo califica con exámenes parciales y tareas"));
        comentarios.add(new MuestraComentario("Si pudiera volvería a tomar clases con este profesor"));
    }
}

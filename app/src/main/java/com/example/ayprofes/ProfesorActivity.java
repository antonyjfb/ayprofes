package com.example.ayprofes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayprofes.RecyclerViews.AdaptadorMuestraComentario;
import com.example.ayprofes.RecyclerViews.MuestraComentario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProfesorActivity extends AppCompatActivity {

    RecyclerView rcvComentario;
    ArrayList<MuestraComentario> comentarios;

    TextView txtvCarga, txtvRecomendacion, txtvAyuda, txtvClaridad, txtvNombre, txtvProfesor,txtvFacilidad;
    Button btnAñadir;
    RatingBar facilidadRatingBarProfe, claridadRatingBarProfe, ayudaRatingBarProfe, cargaRatingBarProfe, recomendacionRatingBarProfe;
    FirebaseFirestore db;

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

        btnAñadir=findViewById(R.id.btnAñadir);
        txtvCarga=findViewById(R.id.txtvCarga);
        txtvRecomendacion=findViewById(R.id.txtvRecomendacion);
        txtvAyuda=findViewById(R.id.txtvAyuda);
        txtvClaridad=findViewById(R.id.txtvClaridad);
        txtvNombre=findViewById(R.id.txtvNombre);
        txtvProfesor=findViewById(R.id.txtvProfesor);
        txtvFacilidad=findViewById(R.id.txtvFacilidad);

        facilidadRatingBarProfe=findViewById(R.id.facilidadRatingBarProfe);
        claridadRatingBarProfe=findViewById(R.id.claridadRatingBarProfe);
        ayudaRatingBarProfe=findViewById(R.id.ayudaRatingBarProfe);
        cargaRatingBarProfe=findViewById(R.id.cargaRatingBarProfe);
        recomendacionRatingBarProfe=findViewById(R.id.recomendacionRatingBarProfe);

        Bundle bundle = getIntent().getExtras();
        String nombreProfe = bundle.getString("nombreProfe");
        txtvProfesor.setText(nombreProfe);

        db = FirebaseFirestore.getInstance();


        db.collection("Profesores").document(nombreProfe).collection("Comentarios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i=0;
                            float ayudaAux;
                            float resultadoAyuda=0;
                            float facilidadAux;
                            float resultadoFacilidad=0;
                            float claridadAux;
                            float resultadoClaridad=0;
                            float cargaAux;
                            float resultadoCarga=0;
                            float recomendacionAux;
                            float resultadoRecomendacion=0;

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ayudaAux=Float.parseFloat(document.getData().get("ayuda").toString());
                                cargaAux=Float.parseFloat(document.getData().get("carga").toString());
                                facilidadAux=Float.parseFloat(document.getData().get("facilidad").toString());
                                claridadAux=Float.parseFloat(document.getData().get("claridad").toString());
                                recomendacionAux=Float.parseFloat(document.getData().get("recomendacion").toString());

                                resultadoAyuda=resultadoAyuda+ayudaAux;
                                resultadoCarga=resultadoCarga+cargaAux;
                                resultadoClaridad=resultadoClaridad+claridadAux;
                                resultadoFacilidad=resultadoFacilidad+facilidadAux;
                                resultadoRecomendacion=resultadoRecomendacion+recomendacionAux;
                                i++;
                            }
                            resultadoAyuda=resultadoAyuda/i;
                            resultadoCarga=resultadoCarga/i;
                            resultadoClaridad=resultadoClaridad/i;
                            resultadoRecomendacion=resultadoRecomendacion/i;
                            resultadoFacilidad=resultadoFacilidad/i;

                            ayudaRatingBarProfe.setRating(resultadoAyuda);
                            cargaRatingBarProfe.setRating(resultadoCarga);
                            claridadRatingBarProfe.setRating(resultadoClaridad);
                            recomendacionRatingBarProfe.setRating(resultadoRecomendacion);
                            facilidadRatingBarProfe.setRating(resultadoFacilidad);

                            ayudaRatingBarProfe.setEnabled(false);
                            cargaRatingBarProfe.setEnabled(false);
                            facilidadRatingBarProfe.setEnabled(false);
                            recomendacionRatingBarProfe.setEnabled(false);
                            claridadRatingBarProfe.setEnabled(false);

                        } else {
                            Log.w("Hola", "Error getting documents.", task.getException());
                        }
                    }
                });


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

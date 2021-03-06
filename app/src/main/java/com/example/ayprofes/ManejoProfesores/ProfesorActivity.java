package com.example.ayprofes.ManejoProfesores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayprofes.MainActivity;
import com.example.ayprofes.ManejoProfesores.BuscarActivity;
import com.example.ayprofes.ManejoProfesores.ComentarioActivity;
import com.example.ayprofes.ManejoUsuarios.LoginActivity;
import com.example.ayprofes.R;
import com.example.ayprofes.RecyclerViews.AdaptadorMuestraComentario;
import com.example.ayprofes.RecyclerViews.MuestraComentario;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfesorActivity extends AppCompatActivity {

    RecyclerView rcvComentario;
    AdaptadorMuestraComentario adaptador;

    TextView txtvCarga, txtvRecomendacion, txtvAyuda, txtvClaridad, txtvNombre, txtvProfesor,txtvFacilidad, txtvCargaCalif, txtvAyudaCalif, txtvClaridadCalif, txtvFacilidadCalif;
    Button btnAñadir;
    RatingBar facilidadRatingBarProfe, claridadRatingBarProfe, ayudaRatingBarProfe, cargaRatingBarProfe, recomendacionRatingBarProfe;
    FirebaseFirestore db;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);
        rcvComentario=findViewById(R.id.rcvComentario);
        LinearLayoutManager llm=new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rcvComentario.setLayoutManager(llm);

        btnAñadir=findViewById(R.id.btnAñadir);
        txtvCarga=findViewById(R.id.txtvCarga);
        txtvRecomendacion=findViewById(R.id.txtvRecomendacion);
        txtvAyuda=findViewById(R.id.txtvAyuda);
        txtvClaridad=findViewById(R.id.txtvClaridad);
        txtvProfesor=findViewById(R.id.txtvProfesor);
        txtvFacilidad=findViewById(R.id.txtvFacilidad);
        txtvCargaCalif=findViewById(R.id.textvCargaCalif);
        txtvAyudaCalif=findViewById(R.id.textvAyudaCalif);
        txtvClaridadCalif=findViewById(R.id.textvClaridadCalif);
        txtvFacilidadCalif=findViewById(R.id.textvFacilidadCalif);

        toolbar=findViewById(R.id.toolbar);

        facilidadRatingBarProfe=findViewById(R.id.facilidadRatingBarProfe);
        claridadRatingBarProfe=findViewById(R.id.claridadRatingBarProfe);
        ayudaRatingBarProfe=findViewById(R.id.ayudaRatingBarProfe);
        cargaRatingBarProfe=findViewById(R.id.cargaRatingBarProfe);
        recomendacionRatingBarProfe=findViewById(R.id.recomendacionRatingBarProfe);

        Bundle bundle = getIntent().getExtras();
        final String nombreProfe = bundle.getString("nombreProfe");
        txtvProfesor.setText(nombreProfe);

        db = FirebaseFirestore.getInstance();


    try {
    db.collection("Profesores").document(nombreProfe).collection("Comentarios")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        int i = 0;
                        float ayudaAux;
                        float resultadoAyuda = 0;
                        float facilidadAux;
                        float resultadoFacilidad = 0;
                        float claridadAux;
                        float resultadoClaridad = 0;
                        float cargaAux;
                        float resultadoCarga = 0;
                        float recomendacionAux;
                        float resultadoRecomendacion = 0;
                        double calificacionaux;

                        //Obtiene los comentarios del profesor seleccionado por el usuario y de ahí hace iteraciones
                        //para calcular el promedio de cada atributo
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ayudaAux = Float.parseFloat(document.getData().get("ayuda").toString());
                            cargaAux = Float.parseFloat(document.getData().get("carga").toString());
                            facilidadAux = Float.parseFloat(document.getData().get("facilidad").toString());
                            claridadAux = Float.parseFloat(document.getData().get("claridad").toString());
                            recomendacionAux = Float.parseFloat(document.getData().get("recomendacion").toString());

                            resultadoAyuda = resultadoAyuda + ayudaAux;
                            resultadoCarga = resultadoCarga + cargaAux;
                            resultadoClaridad = resultadoClaridad + claridadAux;
                            resultadoFacilidad = resultadoFacilidad + facilidadAux;
                            resultadoRecomendacion = resultadoRecomendacion + recomendacionAux;
                            i++;
                        }
                        resultadoAyuda = resultadoAyuda / i;
                        resultadoCarga = resultadoCarga / i;
                        resultadoClaridad = resultadoClaridad / i;
                        resultadoRecomendacion = resultadoRecomendacion / i;
                        resultadoFacilidad = resultadoFacilidad / i;
                        calificacionaux=(resultadoAyuda+resultadoClaridad+resultadoFacilidad+resultadoRecomendacion)/2;

                        //Conversión de promedios a dato tipo string para su visualización
                        String calificacion=String.format("%.2f",calificacionaux);
                        String resultadoAyudaString, resultadoCargaString, resultadoClaridadString, resultadoFacilidadString;
                        resultadoAyudaString=String.format("%.1f",resultadoAyuda);
                        resultadoCargaString=String.format("%.1f",resultadoCarga);
                        resultadoClaridadString=String.format("%.1f",resultadoClaridad);
                        resultadoFacilidadString=String.format("%.1f",resultadoFacilidad);

                        db.collection("Profesores").document(nombreProfe).update("calificacion",Double.parseDouble(calificacion));

                        //Manejo de ratingBar
                        ayudaRatingBarProfe.setRating(5);
                        cargaRatingBarProfe.setRating(5);
                        claridadRatingBarProfe.setRating(5);
                        facilidadRatingBarProfe.setRating(5);
                        recomendacionRatingBarProfe.setRating(resultadoRecomendacion);

                        //Despliegue de calificaciones
                        txtvAyudaCalif.setText(resultadoAyudaString);
                        txtvCargaCalif.setText(resultadoCargaString);
                        txtvClaridadCalif.setText(resultadoClaridadString);
                        txtvFacilidadCalif.setText(resultadoFacilidadString);

                        //Impide al usuario modificar las ratingBars
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
    } catch (Exception e) {
        e.printStackTrace();
        //Manejo de error si no se puede conectar a la base de datos
        Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastHaOcurridoError), Toast.LENGTH_SHORT).show();
    }

        //Creación de recycle view a partir de una query de la base de datos
        Query query=db.collection("Profesores").document(nombreProfe).collection("Comentarios");
        FirestoreRecyclerOptions<MuestraComentario> firestoreRecyclerOptions=new FirestoreRecyclerOptions.Builder<MuestraComentario>().setQuery(query,MuestraComentario.class).build();
        adaptador=new AdaptadorMuestraComentario(firestoreRecyclerOptions);
        adaptador.notifyDataSetChanged();
        rcvComentario.setAdapter(adaptador);

        //Evento para añadir comentario
        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseFirestore.getInstance();
                SharedPreferences sharedPreferences=getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
                String usuarioShared=sharedPreferences.getString("Usuario","No hay info");

                //Si no existe una sesión iniciada no permite añadir un comentario del profesor
                if(usuarioShared=="No hay info") {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.toastRequiereSesionComentar), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent in = new Intent(getApplicationContext(), ComentarioActivity.class);
                    Bundle bundle = getIntent().getExtras();
                    String nombreProfe = bundle.getString("nombreProfe");
                    Log.d("Transito", nombreProfe);
                    in.putExtra("nombreProfe", nombreProfe);
                    startActivity(in);
                }
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    //Nétodos para el recycle view
    @Override
    protected void onStart() {
        super.onStart();
        adaptador.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptador.stopListening();
    }

    //Métodos del menu descritos previamente
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
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    //Manejo del evento al presionar back, te manda a la vista anterior a esta
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(getApplicationContext(), BuscarActivity.class);
        startActivity(in);
    }

    //Funcion explicada anteriormente
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

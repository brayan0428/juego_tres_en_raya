package parcial1.tresenraya.com.juegotresenraya;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class EstadisticasActivity extends AppCompatActivity {
    TextView numPartidas,numPartidasGanadas1,numPartidasGanadas2,numPartidasGanadasMaquina,numPartidasCerradas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        try{
            //Iniciamos las variables
            numPartidas = findViewById(R.id.NumPartidas);
            numPartidasGanadas1 = findViewById(R.id.NumPartidasGanadas1);
            numPartidasGanadas2 = findViewById(R.id.NumPartidasGanadas2);
            numPartidasGanadasMaquina=findViewById(R.id.NumPartidasGanadasMaquina);
            numPartidasCerradas = findViewById(R.id.NumPartidasCerradas);

            SharedPreferences preferences = getSharedPreferences("Estadisticas", Context.MODE_PRIVATE);
            //Asignamos los valores a los textview
            numPartidas.setText(preferences.getInt("NumPartidas",0) + "");
            numPartidasGanadas1.setText(preferences.getInt("NumPartidasGanadas1",0) + "");
            numPartidasGanadas2.setText(preferences.getInt("NumPartidasGanadas2",0) + "");
            numPartidasGanadasMaquina.setText(preferences.getInt("NumPartidasGanadasMaquina",0) + "");
            numPartidasCerradas.setText(preferences.getInt("NumPartidasCerradas",0) + "");

        }catch (Exception e){
            mostrarMensaje(e.getMessage());
        }
    }

    public void mostrarMensaje(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}

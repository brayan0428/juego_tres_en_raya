package parcial1.tresenraya.com.juegotresenraya;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class EstadisticasActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        try{
            SharedPreferences preferences = getSharedPreferences("Estadisticas", Context.MODE_PRIVATE);
            mostrarMensaje(preferences.getInt("Num_Partidas",0) + "");
        }catch (Exception e){
            mostrarMensaje(e.getMessage());
        }
    }

    public void mostrarMensaje(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}

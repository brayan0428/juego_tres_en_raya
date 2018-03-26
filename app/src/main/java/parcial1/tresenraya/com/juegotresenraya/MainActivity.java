package parcial1.tresenraya.com.juegotresenraya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn_UnJugador,btn_Multijugador,btn_Estadisticas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asignamos los ids de la activity
        btn_UnJugador = findViewById(R.id.btn_unJugador);
        btn_Multijugador = findViewById(R.id.btn_Multijugador);
        btn_Estadisticas = findViewById(R.id.btn_Estadisticas);

        //Asignamos el evento a los botones
        btn_UnJugador.setOnClickListener(this);
        btn_Multijugador.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_unJugador:
                Intent intent_1 = new Intent(getApplicationContext(),JuegoActivity.class);
                intent_1.putExtra("Parametro",1);
                startActivity(intent_1);
                break;
            case R.id.btn_Multijugador:
                Intent intent_2 = new Intent(getApplicationContext(),JuegoActivity.class);
                intent_2.putExtra("Parametro",2);
                startActivity(intent_2);
                break;
        }
    }
}

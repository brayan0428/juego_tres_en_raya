package parcial1.tresenraya.com.juegotresenraya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        inicializarSharedPreferences();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask,2000);
    }

    public void inicializarSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("Estadisticas", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("NumPartidas",0);
        editor.putInt("NumPartidasGanadas1",0);
        editor.putInt("NumPartidasGanadas2",0);
        editor.putInt("NumPartidasGanadasMaquina",0);
        editor.putInt("NumPartidasCerradas",0);
        editor.commit();
    }
}

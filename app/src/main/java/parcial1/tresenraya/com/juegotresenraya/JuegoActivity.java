package parcial1.tresenraya.com.juegotresenraya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class JuegoActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_uno,btn_dos,btn_tres,btn_cuatro,btn_cinco,btn_seis,btn_siete,btn_ocho,btn_nueve,btn_reiniciar;
    LinearLayout niveles;
    RadioGroup radioGroup;
    RadioButton rb_Facil,rb_Intermedio,rb_Avanzado;
    TextView cuenta_regresiva;
    int Tablero [][] = new int[3][3];
    int Parametro = 0,turno = 0;
    String op_letra = String.valueOf("X");
    Contador contador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        //Recuperamos el parametro de la vista principal
        Parametro = getIntent().getIntExtra("Parametro",0);
        //Inicializamos las variables
        btn_uno = findViewById(R.id.btn_uno);
        btn_dos = findViewById(R.id.btn_dos);
        btn_tres = findViewById(R.id.btn_tres);
        btn_cuatro = findViewById(R.id.btn_cuatro);
        btn_cinco = findViewById(R.id.btn_cinco);
        btn_seis = findViewById(R.id.btn_seis);
        btn_siete = findViewById(R.id.btn_siete);
        btn_ocho = findViewById(R.id.btn_ocho);
        btn_nueve = findViewById(R.id.btn_nueve);
        btn_reiniciar = findViewById(R.id.reiniciar);
        niveles = findViewById(R.id.niveles);
        cuenta_regresiva = findViewById(R.id.cuenta_regresiva);
        radioGroup = findViewById(R.id.radio_group);
        rb_Facil = findViewById(R.id.rb_Facil);
        rb_Intermedio = findViewById(R.id.rb_Intermedio);
        rb_Avanzado = findViewById(R.id.rb_Avanzado);
        iniciarTablero();

        turno = 0;
        //Asignamos el evento click a los botones
        btn_uno.setOnClickListener(this);
        btn_dos.setOnClickListener(this);
        btn_tres.setOnClickListener(this);
        btn_cuatro.setOnClickListener(this);
        btn_cinco.setOnClickListener(this);
        btn_seis.setOnClickListener(this);
        btn_siete.setOnClickListener(this);
        btn_ocho.setOnClickListener(this);
        btn_nueve.setOnClickListener(this);
        btn_reiniciar.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int cantSegundos = 0;
                switch (radioButton.getId()){
                    case R.id.rb_Intermedio:
                        //Iniciamos contador
                        rb_Facil.setEnabled(false);
                        rb_Avanzado.setEnabled(false);
                        cantSegundos = 21000;
                        break;
                    case R.id.rb_Avanzado:
                        cantSegundos = 11000;
                        rb_Facil.setEnabled(false);
                        rb_Intermedio.setEnabled(false);
                        break;
                }
                if(radioButton.getId() != R.id.rb_Facil){
                    contador = new Contador(cantSegundos,1000);
                    contador.start();
                }
            }
        });

        if(Parametro == 2){
            niveles.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() != R.id.reiniciar){
            if(validarPosicionLlena(view) == false){
                mostrarMensaje("Debe seleccionar otra posiciòn");
                return;
            }
            turno++;
            if(turno == 1){
                editarPreferencias("NumPartidas");
            }
            if(Parametro == 1){
                turnoJugador(view,"X",1);
                //turnoMaquina();
                turnoMaquina2();
            }else{
                if (op_letra.equals("X")) turnoJugador(view,op_letra,1);
                else turnoJugador(view,op_letra,2);

            }
        }else{
            reiniciar();
            return;
        }
        if (validarGanador() != -1){
            if (validarGanador() == 1){
                editarPreferencias("NumPartidasGanadas1");
                mostrarMensaje("Ha ganado el Jugador 1");
            }else{
                if(Parametro == 1){
                    editarPreferencias("NumPartidasGanadasMaquina");
                }else{
                    editarPreferencias("NumPartidasGanadas2");
                }
                mostrarMensaje("Ha ganado el Jugador 2");
            }
            cancelarContador();
            return;
        }
        if(validarTableroLleno() == true && validarGanador() == -1){
            mostrarMensaje("Empate");
            editarPreferencias("NumPartidasCerradas");
            cancelarContador();
            retornarMain();
            return;
        }
    }

    @Override
    protected void onPause() {
        cancelarContador();
        super.onPause();
    }

    //Funcionar para inicializar todos los valores del tablero en -1
    public void iniciarTablero(){
        try{
            for (int x = 0;x<=2;x++){
                for (int y = 0;y<=2;y++){
                    this.Tablero[x][y] = -1;
                }
            }
        }catch (Exception e){
            mostrarMensaje(e.getMessage());
        }

    }

    public void mostrarMensaje(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    private void turnoMaquina(){
        try{
            if (turno == 5 || validarGanador() != -1 || validarTableroLleno()){
                return;
            }
            boolean colocar = false;
            int xRandom = (int) (Math.random() * 3);
            int yRandom = (int) (Math.random() * 3);
            while(!colocar){
                if (Tablero[xRandom][yRandom] == -1){
                    Tablero[xRandom][yRandom] = 2;
                    colocar = true;
                }else{
                    xRandom = (int) (Math.random() * 3);
                    yRandom = (int) (Math.random() * 3);
                }
            }
            switch (xRandom){
                case 0:
                    if(yRandom == 0){
                        btn_uno.setText("O");
                    }
                    if(yRandom == 1){
                        btn_dos.setText("O");
                    }
                    if(yRandom == 2){
                        btn_tres.setText("O");
                    }
                    break;
                case 1:
                    if(yRandom == 0){
                        btn_cuatro.setText("O");
                    }
                    if(yRandom == 1){
                        btn_cinco.setText("O");
                    }
                    if(yRandom == 2){
                        btn_seis.setText("O");
                    }
                    break;
                case 2:
                    if(yRandom == 0){
                        btn_siete.setText("O");
                    }
                    if(yRandom == 1){
                        btn_ocho.setText("O");
                    }
                    if(yRandom == 2){
                        btn_nueve.setText("O");
                    }
                    break;
            }
        }catch (Exception e){
            mostrarMensaje(e.getMessage());
        }
    }
    /*INICIO ALGORITMO MINIMAX*/
        private void turnoMaquina2(){
            try{
                if (turno == 5 || validarGanador() != -1 || validarTableroLleno()){
                    return;
                }
                int f=0,c=0;
                int v = Integer.MIN_VALUE;
                int aux;
                for(int x=0;x<3;x++){
                    for(int y=0;y<3;y++){
                        if(Tablero[x][y] == -1){
                            Tablero[x][y] = 2;
                            aux = min();
                            if(aux > v){
                                v = aux;
                                f = x;
                                c = y;
                            }
                            Tablero[x][y] = -1;
                        }
                    }
                }
                Tablero[f][c] = 2;
                switch (f){
                    case 0:
                        if(c == 0){
                            btn_uno.setText("O");
                        }
                        if(c == 1){
                            btn_dos.setText("O");
                        }
                        if(c == 2){
                            btn_tres.setText("O");
                        }
                        break;
                    case 1:
                        if(c == 0){
                            btn_cuatro.setText("O");
                        }
                        if(c == 1){
                            btn_cinco.setText("O");
                        }
                        if(c == 2){
                            btn_seis.setText("O");
                        }
                        break;
                    case 2:
                        if(c == 0){
                            btn_siete.setText("O");
                        }
                        if(c == 1){
                            btn_ocho.setText("O");
                        }
                        if(c == 2){
                            btn_nueve.setText("O");
                        }
                        break;
                }
            }catch (Exception e){
                mostrarMensaje(e.getMessage());
            }
        }

        private int max(){
            if(validarTableroLleno() || validarGanador() != -1){
                if(validarGanador() != -1) return -1;
                else return 0;
            }
            int v = Integer.MIN_VALUE;
            int aux;
            for(int x=0;x<3;x++) {
                for (int y = 0; y < 3; y++) {
                    if (Tablero[x][y] == -1) {
                        Tablero[x][y] = 2;
                        aux = min();
                        if (aux > v) {
                            v = aux;
                        }
                        Tablero[x][y] = -1;
                    }
                }
            }
            return v;
        }
        private int min(){
            if(validarTableroLleno()|| validarGanador() != -1){
                if(validarGanador() != -1) return 2;
                else return 0;
            }
            int v = Integer.MAX_VALUE;
            int aux;
            for(int x=0;x<3;x++) {
                for (int y = 0; y < 3; y++) {
                    if (Tablero[x][y] == -1) {
                        Tablero[x][y] = 1;
                        aux = max();
                        if (aux < v) {
                            v = aux;
                        }
                        Tablero[x][y] = -1;
                    }
                }
            }
            return v;
        }
    /*FIN ALGORITMO MINIMAX*/
    public int validarGanador(){
        try{
            //Verificamos la diagonal \
            if(Tablero[0][0] != -1 && Tablero[0][0] == Tablero[1][1] && Tablero[0][0] == Tablero[2][2] ){
                return Tablero[0][0];
            }
            //Verificamos la diagonal /
            if(Tablero[0][2] != -1 && Tablero[0][2] == Tablero[1][1] && Tablero[0][2] == Tablero[2][0] ){
                return Tablero[0][2];
            }
            //Verificamos las filas horizontales y verticales
            for (int a = 0;a<=2;a++) {
                if (Tablero[a][0] != -1 && Tablero[a][0] == Tablero[a][1] && Tablero[a][0] == Tablero[a][2]) {
                    return Tablero[a][0];
                }
                if (Tablero[0][a] != -1 && Tablero[0][a] == Tablero[1][a] && Tablero[0][a] == Tablero[2][a]) {
                    return Tablero[0][a];
                }
            }
        }catch (Exception e){
            mostrarMensaje(e.getMessage());
        }
        return -1;
    }

    private boolean validarTableroLleno(){
        for (int x = 0;x<=2;x++){
            for (int y=0;y<=2;y++){
                if(Tablero[x][y] == -1){
                    return false;
                }
            }
        }
        return true;
    }

    private void turnoJugador(View v,String letra,int turno){
            switch (v.getId()){
                case R.id.btn_uno:
                    Tablero[0][0] = turno;
                    btn_uno.setText(letra);
                    break;
                case R.id.btn_dos:
                    Tablero[0][1] = turno;
                    btn_dos.setText(letra);
                    break;
                case R.id.btn_tres:
                    Tablero[0][2] = turno;
                    btn_tres.setText(letra);
                    break;
                case R.id.btn_cuatro:
                    Tablero[1][0] = turno;
                    btn_cuatro.setText(letra);
                    break;
                case R.id.btn_cinco:
                    Tablero[1][1] = turno;
                    btn_cinco.setText(letra);
                    break;
                case R.id.btn_seis:
                    Tablero[1][2] = turno;
                    btn_seis.setText(letra);
                    break;
                case R.id.btn_siete:
                    Tablero[2][0] = turno;
                    btn_siete.setText(letra);
                    break;
                case R.id.btn_ocho:
                    Tablero[2][1] = turno;
                    btn_ocho.setText(letra);
                    break;
                case R.id.btn_nueve:
                    Tablero[2][2] = turno;
                    btn_nueve.setText(letra);
                    break;
            }
            if (letra.equals("X")){
                op_letra = String.valueOf("O");
            }else{
                op_letra = String.valueOf("X");
            }
    }

    private void reiniciar(){
        if(validarGanador() == -1){
            editarPreferencias("NumPartidasCerradas");
        }
        iniciarTablero();
        btn_uno.setText("");
        btn_dos.setText("");
        btn_tres.setText("");
        btn_cuatro.setText("");
        btn_cinco.setText("");
        btn_seis.setText("");
        btn_siete.setText("");
        btn_ocho.setText("");
        btn_nueve.setText("");
        rb_Facil.setEnabled(true);
        rb_Intermedio.setEnabled(true);
        rb_Avanzado.setEnabled(true);
        rb_Facil.setChecked(true);
        rb_Intermedio.setChecked(false);
        rb_Avanzado.setChecked(false);
        cuenta_regresiva.setText("");
        turno = 0;
        op_letra = "X";
    }

    public class Contador extends CountDownTimer{
        public Contador(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            rb_Facil.setEnabled(true);
            rb_Intermedio.setEnabled(true);
            rb_Avanzado.setEnabled(true);
            if(validarGanador() == -1){
                this.cancel();
                mostrarMensaje("Se ha terminado el tiempo");
                retornarMain();
            }
        }

        @Override
        public void onTick(long l) {
            cuenta_regresiva.setText("" + l/1000);
        }
    }

    public void cancelarContador(){
        if(Parametro == 1 && contador != null){
            contador.cancel();
            cuenta_regresiva.setText("");
        }
    }

    public void retornarMain(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean validarPosicionLlena(View v){
        Button button = findViewById(v.getId());
        if (button.getText().toString() == ""){
            return true;
        }
        return false;
    }

    public void editarPreferencias(String variable){
        SharedPreferences preferences = getSharedPreferences("Estadisticas",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int cantidad = preferences.getInt(variable,0) + 1;
        editor.putInt(variable,cantidad);
        editor.commit();
    }
}

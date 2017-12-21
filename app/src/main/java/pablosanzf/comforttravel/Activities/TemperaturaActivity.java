package pablosanzf.comforttravel.Activities;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import pablosanzf.comforttravel.Domain.Asiento;
import pablosanzf.comforttravel.R;

/**
 * Created by master on 17/11/2017.
 */

public class TemperaturaActivity extends Activity {

    private TextView textTemperaturaActual;
    private TextView textTemperaturaSeleccioanda;
    private Button buttonMas;
    private Button buttonMenos;

    private Asiento asiento;

    private static final int MIN_TEMPERATURA = 10;
    private static final int MAX_TEMPERATURA = 30;

    public static final String MODIFICAR_TEMPERATURA = "modificar_temp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_temperatura);
        asiento = new Asiento();

        if (getIntent().getSerializableExtra(MODIFICAR_TEMPERATURA) != null)
            this.asiento = (Asiento) getIntent().getSerializableExtra(MODIFICAR_TEMPERATURA);

        textTemperaturaActual = (TextView) findViewById(R.id.grados_actuales);
        textTemperaturaSeleccioanda = (TextView) findViewById(R.id.temperatura_insertada);
        buttonMas = (Button) findViewById(R.id.mas_temperatura);
        buttonMenos = (Button) findViewById(R.id.menos_temperatura);

        recibirTemoperatura();
        mostrarTemperaturaActual();

        buttonMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (asiento.getTemperatura() == MAX_TEMPERATURA) {
                    Toast.makeText(getApplicationContext(), R.string.tempMaxima , Toast.LENGTH_SHORT).show();

                } else {
                    asiento.setTemperatura(asiento.getTemperatura() + 1);
                    modificarValorAire(1);
                }
                mostrarTemperaturaActual();
            }
        });

        buttonMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (asiento.getTemperatura() == MIN_TEMPERATURA) {
                    Toast.makeText(getApplicationContext(), R.string.tempMinima , Toast.LENGTH_SHORT).show();

                } else {
                    asiento.setTemperatura(asiento.getTemperatura() - 1);
                    modificarValorAire(-1);
                }
                mostrarTemperaturaActual();
            }
        });

    }

    private void mostrarTemperaturaActual() {
        if (asiento.getTemperatura() >= MAX_TEMPERATURA) {
            textTemperaturaSeleccioanda.setText("MAX");
        } else {
            if (asiento.getTemperatura() <= MIN_TEMPERATURA) {
                textTemperaturaSeleccioanda.setText("MIN");
            } else {
                textTemperaturaSeleccioanda.setText(String.valueOf(asiento.getTemperatura()));
            }
        }

    }

    /**
     * Método que debe recibir la temperatura del termómetro
     */
    private void recibirTemoperatura() {
        SensorManager mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        Sensor TemperatureSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if(TemperatureSensor != null){
            mySensorManager.registerListener(AmbientTemperatureSensorListener, TemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            //introducir la comunicación para recibir la temperatura por el sensor del arduino

        }
    }

    private final SensorEventListener AmbientTemperatureSensorListener = new SensorEventListener(){
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
                textTemperaturaActual.setText( String.valueOf(event.values[0]));
            }
        }

    };


    /**
     * Metodo que debe modificar la temperatura del aire
     * @param i grados que sube o baja
     */
    private void modificarValorAire(int i) {

    }

    @Override
    public void onBackPressed() {
        Intent intentResult = new Intent();
        intentResult.putExtra(MODIFICAR_TEMPERATURA, asiento);
        setResult(Activity.RESULT_OK, intentResult);
        finish();
    }
}

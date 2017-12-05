package pablosanzf.comforttravel.Activities;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import pablosanzf.comforttravel.Domain.Asiento;
import pablosanzf.comforttravel.R;

/**
 * Created by master on 17/11/2017.
 */

public class LuminosidadActivity extends Activity {

    private TextView textLuminosidadActual;
    private EditText editLuminosidadSeleccioanda;
    private Button buttonMas;
    private Button buttonMenos;

    private Asiento asiento;

    private static final int MIN_LUMINOSIDAD = 0;
    private static final int MAX_lUMINOSIDAD = 4000;

    private float valorLum;
    private boolean primerPaso = true;

    public static final String MODIFICAR_LUMINOSIDAD = "modificar_lum";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_luminosidad);
        asiento = new Asiento();

        if (getIntent().getSerializableExtra(MODIFICAR_LUMINOSIDAD) != null)
            this.asiento = (Asiento) getIntent().getSerializableExtra(MODIFICAR_LUMINOSIDAD);

        textLuminosidadActual = (TextView) findViewById(R.id.cd_actuales);
        editLuminosidadSeleccioanda = (EditText) findViewById(R.id.luminosidad_insertada);
        buttonMas = (Button) findViewById(R.id.mas_luminosidad);
        buttonMenos = (Button) findViewById(R.id.menos_luminosidad);


        recibirLuminosidad();
        mostrarLuminosidadActual();

        buttonMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (asiento.getLuminosidad() >= MAX_lUMINOSIDAD) {
                    Toast.makeText(getApplicationContext(), R.string.lumMaxima , Toast.LENGTH_SHORT).show();

                } else {
                    asiento.setLuminosidad(asiento.getLuminosidad() + 100);
                    modificarValorLuz(100);
                }
                mostrarLuminosidadActual();
            }
        });

        buttonMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (asiento.getLuminosidad() <= MIN_LUMINOSIDAD) {
                    Toast.makeText(getApplicationContext(), R.string.lumMinima , Toast.LENGTH_SHORT).show();

                } else {
                    asiento.setLuminosidad(asiento.getLuminosidad() - 100);
                    modificarValorLuz(-100);
                }
                mostrarLuminosidadActual();
            }
        });



        editLuminosidadSeleccioanda.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            // the user is done typing.
                            Double value = Double.parseDouble(editLuminosidadSeleccioanda.getText().toString());
                            if (value >= MAX_lUMINOSIDAD) {
                                editLuminosidadSeleccioanda.setText("MAX");
                                asiento.setLuminosidad(MAX_lUMINOSIDAD);
                            } else {
                                if (value <= MIN_LUMINOSIDAD) {
                                    editLuminosidadSeleccioanda.setText("MIN");
                                    asiento.setLuminosidad(MIN_LUMINOSIDAD);
                                } else {
                                    editLuminosidadSeleccioanda.setText(value.toString());
                                    asiento.setLuminosidad(value);

                                }
                            }
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            return true; // consume.
                        }
                        return false; // pass on to other listeners.
                    }
                }
        );
    }

    private void mostrarLuminosidadActual() {
        if (asiento.getLuminosidad() >= MAX_lUMINOSIDAD) {
            editLuminosidadSeleccioanda.setText("MAX");

        } else {
            if (asiento.getLuminosidad() <= MIN_LUMINOSIDAD) {
                editLuminosidadSeleccioanda.setText("MIN");
            } else {
                editLuminosidadSeleccioanda.setText(String.valueOf(asiento.getLuminosidad()));
            }
        }

    }

    /**
     * Método que debe recibir la luminosidad del termómetro
     */
    private void recibirLuminosidad() {

        SensorManager mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor lightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(lightSensor != null){
            mySensorManager.registerListener(LightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            //introducir la comunicación para recibir la temperatura por el sensor del arduino
            textLuminosidadActual.setText(String.valueOf(new Random().nextInt(150)));
        }
    }

    private final SensorEventListener LightSensorListener = new SensorEventListener(){
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                if((!primerPaso) &&((valorLum+40)<(event.values[0])|| (valorLum-40)>(event.values[0]))){
                    textLuminosidadActual.setText( String.valueOf(event.values[0]));
                    valorLum = event.values[0];
                }
                if(primerPaso){
                    textLuminosidadActual.setText( String.valueOf(event.values[0]));
                    valorLum = event.values[0];
                    primerPaso = false;
                }


            }
        }

    };


    /**
     * Metodo que debe modificar la luminosidad del aire
     *
     * @param i grados que sube o baja
     */
    private void modificarValorLuz(int i) {

    }

    @Override
    public void onBackPressed() {
        Intent intentResult = new Intent();
        intentResult.putExtra(MODIFICAR_LUMINOSIDAD, asiento);
        setResult(Activity.RESULT_OK, intentResult);
        finish();
    }

}

package pablosanzf.comforttravel.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import pablosanzf.comforttravel.Domain.Asiento;
import pablosanzf.comforttravel.Net.SimpleHttpClient;
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

    private String deviceID = "4";


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
        //El primer caso es recibir directamente el valor de la luminosidad desde el sensor del movil
        //el else es para hacerlo desde el arduino
       if(lightSensor != null){
           mySensorManager.registerListener(LightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            arduinoLuminosidad();
        }
    }

    private void arduinoLuminosidad() {
        // First check if there is connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            new ArduinoLuminosidad().execute(getString(R.string.service_uri)+ "arduino/getlast.php?device_id=" + deviceID + "&data_name=lum&nitems=1");
        } else {
            // No -> Display error message
            Toast.makeText(this, R.string.msg_error_no_connection, Toast.LENGTH_SHORT).show();
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

    private class ArduinoLuminosidad extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            SimpleHttpClient shc = new SimpleHttpClient(url);
            publishProgress(40);
            String result = shc.doGet();
            if(result != null){
                Log.i("Temperaure data",result);
                double luminosidad = getLuminosidadFromJson(result);
                publishProgress(100);
                return new Double(luminosidad).toString();
            }else
                return null;
        }


        @Override
        protected void onPostExecute(String result) {
            if(result != null){
                textLuminosidadActual.setText(result);
            }else{
                Toast.makeText(LuminosidadActivity.this, R.string.msg_error_server, Toast.LENGTH_SHORT).show();
            }
        }

        private double getLuminosidadFromJson(String data){
            double lum = 0.0;
            try {
                JSONArray jsonArray = new JSONArray(data);
                //JSONObject json = new JSONObject(data);
                JSONObject json = jsonArray.getJSONObject(0);
                lum = json.getDouble("data_value");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return lum;
        }


    }






}



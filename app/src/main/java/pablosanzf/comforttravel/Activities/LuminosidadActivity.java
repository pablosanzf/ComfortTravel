package pablosanzf.comforttravel.Activities;

import android.app.Activity;
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
    private static final int MAX_lUMINOSIDAD = 150;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_luminosidad);
        asiento = new Asiento();

        System.out.println("Este es el asiiento que creo en temperaturaactivity: " + asiento.toString());

        if (getIntent().getSerializableExtra("asiento_lum") != null)
            this.asiento = (Asiento) getIntent().getSerializableExtra("asiento_lum");

        textLuminosidadActual = (TextView) findViewById(R.id.cd_actuales);
        editLuminosidadSeleccioanda = (EditText) findViewById(R.id.luminosidad_insertada);
        buttonMas = (Button) findViewById(R.id.mas_luminosidad);
        buttonMenos = (Button) findViewById(R.id.menos_luminosidad);

        recibirLuminosidad();
        mostrarLuminosidadActual();

        buttonMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (asiento.getLuminosidad() == MAX_lUMINOSIDAD) {
                    Toast.makeText(getApplicationContext(), "la temperatura ya es máxima", Toast.LENGTH_SHORT).show();

                } else {
                    asiento.setLuminosidad(asiento.getLuminosidad() + 1);
                    modificarValorAire(1);
                }
                mostrarLuminosidadActual();
            }
        });

        buttonMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (asiento.getLuminosidad() == MIN_LUMINOSIDAD) {
                    Toast.makeText(getApplicationContext(), "la temperatura ya es mínima", Toast.LENGTH_SHORT).show();

                } else {
                    asiento.setLuminosidad(asiento.getLuminosidad() - 1);
                    modificarValorAire(-1);
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
        Toast.makeText(getApplicationContext(), "Recibir la luminosidad", Toast.LENGTH_SHORT).show();
        textLuminosidadActual.setText(String.valueOf(new Random().nextInt(150)));
    }

    /**
     * Metodo que debe modificar la luminosidad del aire
     *
     * @param i grados que sube o baja
     */
    private void modificarValorAire(int i) {

    }

}

package pablosanzf.comforttravel.Activities;

import android.app.Activity;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_temperatura);
        asiento = new Asiento();

        System.out.println("Este es el asiiento que creo en temperaturaactivity: " + asiento.toString());

        if (getIntent().getSerializableExtra("asiento_temp") != null)
            this.asiento = (Asiento) getIntent().getSerializableExtra("asiento_temp");

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
                    Toast.makeText(getApplicationContext(), "la temperatura ya es máxima", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getApplicationContext(), "la temperatura ya es mínima", Toast.LENGTH_SHORT).show();

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
        Toast.makeText(getApplicationContext(), "Recibir la temperatura", Toast.LENGTH_SHORT).show();
        textTemperaturaActual.setText(String.valueOf(10 + new Random().nextInt(20)));
    }

    /**
     * Metodo que debe modificar la temperatura del aire
     * @param i grados que sube o baja
     */
    private void modificarValorAire(int i) {

    }

}

package pablosanzf.comforttravel.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import pablosanzf.comforttravel.Domain.Asiento;
import pablosanzf.comforttravel.R;

public class RotacionActivity extends Activity {

    public static final String ROTAR_ASIENTO = "modificar_rotacion";

    private RadioGroup radioGroup;
    private RadioButton cabeza;
    private RadioButton espalda;
    private RadioButton pies;
    private SeekBar seekBar;

    private Asiento asiento;

    private static final int MIN_ROTACION_CABEZA = 0;
    private static final int MAX_ROTACION_CABEZA = 100;
    private static final int MIN_ROTACION_ESPALDA = 0;
    private static final int MAX_ROTACION_ESPALDA = 100;
    private static final int MIN_ROTACION_PIES = 0;
    private static final int MAX_ROTACION_PIES = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rotacion);
        asiento = new Asiento();


        if (getIntent().getSerializableExtra(ROTAR_ASIENTO) != null)
            this.asiento = (Asiento) getIntent().getSerializableExtra(ROTAR_ASIENTO);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupRotar);
        cabeza = (RadioButton) findViewById(R.id.radioButtonCabeza);
        espalda = (RadioButton) findViewById(R.id.radioButtonEspalda);
        pies = (RadioButton) findViewById(R.id.radioButtonPies);
        seekBar = (SeekBar) findViewById(R.id.seekBarRotar);

        //Por ser cabeza el default elegido cada vez que se inicia la actividad
        seekBar.setMax(MAX_ROTACION_CABEZA);
        seekBar.setProgress((int) asiento.getRotacionCabeza());

        /*Para cada uno de los tres casos habrá que meter el valor inicial dentro de la seekbar
        no hacerlo solo para cabeza ya que el evento de tener que cambiar en radiobuttons no creo que
                se pueda controlar. pienso que habra que hacer un ifchecked controlar el valor del asiento de partida
                y mirarlo. Problemas con los valores máximos paa Seguridad, Noche, ... miralo
         */


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonCabeza){
                   // seekBar.setMin(MIN_ROTACION_CABEZA);
                    seekBar.setMax(MAX_ROTACION_CABEZA);
                    seekBar.setProgress((int) asiento.getRotacionCabeza());

                }else if(checkedId == R.id.radioButtonEspalda){
                   // seekBar.setMin(MIN_ROTACION_ESPALDA);
                    seekBar.setMax(MAX_ROTACION_ESPALDA);
                    seekBar.setProgress((int) asiento.getRotacionAsiento());

                }else if (checkedId == R.id.radioButtonPies){
                    // seekBar.setMin(MIN_ROTACION_PIES);
                    seekBar.setMax(MAX_ROTACION_PIES);
                    seekBar.setProgress((int) asiento.getRotacionReposapies());
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (R.id.radioButtonCabeza == radioGroup.getCheckedRadioButtonId()){
                    asiento.setRotacionCabeza(progress);
                }else if(R.id.radioButtonEspalda == radioGroup.getCheckedRadioButtonId()){
                    asiento.setRotacionAsiento(progress);
                }else if (R.id.radioButtonPies == radioGroup.getCheckedRadioButtonId()){
                   asiento.setRotacionReposapies(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }

    @Override
    public void onBackPressed() {
        Intent intentResult = new Intent();
        intentResult.putExtra(ROTAR_ASIENTO, asiento);
        setResult(Activity.RESULT_OK, intentResult);
        finish();
    }
}
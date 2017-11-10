package pablosanzf.comforttravel;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

public class AsientoActivity extends Activity {

    private Asiento asiento;
    private String modoDeAsientoSeleccionado;
    private ArrayList<Asiento> perfilesDeAsiento = new ArrayList<Asiento>();
    static final private Asiento NOCHE = new Asiento("NOCHE", "Noche", 10,10 ,10,10,10);
    static final private Asiento LECTURA = new Asiento("LECTURA", "Lectura", 20,20 ,20,20,20);
    static final private Asiento SEGURIDAD = new Asiento("SEGURIDAD", "Seguridad", 30,30 ,30,30,30);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asiento);
    }
}

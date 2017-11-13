package pablosanzf.comforttravel;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class AsientoActivity extends Activity implements
        ActionBar.OnNavigationListener{

    private static final String PERFIL_SELECCIONADO= "perfil seleccionado";

    private Asiento asiento;


    private ArrayList<Asiento> perfilesDeAsiento = new ArrayList<Asiento>();
    private static final  Asiento NOCHE = new Asiento("NOCHE", "Noche", 10,10 ,10,10,10);
    private static final  Asiento LECTURA = new Asiento("LECTURA", "Lectura", 20,20 ,20,20,20);
    private static final  Asiento SEGURIDAD = new Asiento("SEGURIDAD", "Seguridad", 30,30 ,30,30,30);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Aquí debería hacerse la recogida del intent desde la fase anterior de sincornización con el asiento
        this.asiento = new Asiento("", " ", 0,0,0,0,0);


        setContentView(R.layout.activity_asiento);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActionBarThemedContextCompat(),
                R.array.perfiles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionBar.setListNavigationCallbacks(adapter,this);

        onNavigationItemSelected(0,0);
        asiento = buscarEnArray(getResources().getStringArray(R.array.perfiles)[0]);









    }

    /**
     * Backward-compatible version of {@link ActionBar#getThemedContext()} that
     * simply returns the {@link Activity} if
     * <code>getThemedContext</code> is unavailable.
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private Context getActionBarThemedContextCompat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return getActionBar().getThemedContext();
        } else {
            return this;
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(PERFIL_SELECCIONADO)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(PERFIL_SELECCIONADO));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(PERFIL_SELECCIONADO, getActionBar()
                .getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.asiento, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Otra forma de hacer lo mismo que se hace hacia arriba
        if(item.getItemId()==R.id.mnu_guardar){

            return true;
        }
        if(item.getItemId()==R.id.mnu_borrar){

            return true;
        }
        return false;
    }



    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {


        Asiento elegido = buscarEnArray(getResources().getStringArray(R.array.perfiles)[itemPosition]);
        modificarValoresAsiento (elegido);



        invalidateOptionsMenu(); //esta invocación sirve para destuir el menu y dejarlo guardado, para asi por si se comparte tener en el intent de compartir
        //el nuevo valor de grados y de ciudad
        return true;
    }

    /**
     * Aquí debería realizarse la modficación del asiento con los valores del perfil seleccionado
     * @param elegido el asiento con los valores del perfil elegido
     */
    private void modificarValoresAsiento(Asiento elegido) {
        Toast.makeText(this, "ambiando al perfil " + elegido.getNombreModo(), Toast.LENGTH_LONG).show();
    }


    private Asiento buscarEnArray (String perfil){

        Asiento respuesta = new Asiento();
        Boolean encontrado = false;
        int i=0;
        while (!encontrado && i < perfilesDeAsiento.size()) {
            if (perfil.equals(perfilesDeAsiento.get(i).getNombreModo())){
                respuesta =  perfilesDeAsiento.get(i);
                encontrado = true;
            }else{
                i++;
            }
        }
        if (encontrado) return respuesta;
        else return null;
    }

}

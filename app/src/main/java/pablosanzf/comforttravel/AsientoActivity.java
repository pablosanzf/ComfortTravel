package pablosanzf.comforttravel;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class AsientoActivity extends Activity implements
        ActionBar.OnNavigationListener{

    private static final String PERFIL_SELECCIONADO= "perfil seleccionado";

    private Asiento asiento;


    private ArrayList<Asiento> perfilesDeAsiento = new ArrayList<Asiento>();
    private static final  Asiento NOCHE = new Asiento(new String("NOCHE"), new String("Noche"), 10,10 ,10,10,10);
    private static final  Asiento LECTURA = new Asiento(new String("LECTURA"), new String("Lectura"), 20,20 ,20,20,20);
    private static final  Asiento SEGURIDAD = new Asiento(new String("SEGURIDAD"), new String("Seguridad"), 30,30 ,30,30,30);

    private ArrayList<String>  listaDePerfiles = new ArrayList<String>();

    ArrayAdapter<String> listnav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Aquí debería hacerse la recogida del intent desde la fase anterior de sincornización con el asiento
        this.asiento = new Asiento("", "Manual", 0,0,0,0,0);

        perfilesDeAsiento.add(asiento);

        perfilesDeAsiento.add(SEGURIDAD);
        perfilesDeAsiento.add(NOCHE);
        perfilesDeAsiento.add(LECTURA);

        listaDePerfiles = llenarArrayConPerfiles(perfilesDeAsiento);

        setContentView(R.layout.activity_asiento);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);


        listnav = new ArrayAdapter<String>(getActionBarThemedContextCompat(), android.R.layout.simple_spinner_item , listaDePerfiles);
        listnav.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionBar.setListNavigationCallbacks(listnav,this);

        onNavigationItemSelected(0,0);
        asiento = buscarEnArray(listaDePerfiles.get(0));









    }

    /**
     * Backward-compatible version of {@link ActionBar#getThemedContext()} that
     * simply returns the {@link Activity} if
     * <code>getThemedContext</code> is unavailable.
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private Context getActionBarThemedContextCompat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
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

        MenuItem mnuShare = menu.findItem(R.id.mnu_compartir);
        ShareActionProvider shareProv = (ShareActionProvider)
                mnuShare.getActionProvider();
        shareProv.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String mensajeParaEnviar = new String("Prueba a viajar de esta manera \r\n Reposapies: " + asiento.getRotacionReposapies() + " \r\n Respaldo: " + asiento.getRotacionAsiento()
                                                        + "  \r\n Reposacabezas: " + asiento.getRotacionCabeza() + "\r\n Luminosidad: " + asiento.getLuminosidad() + " \r\n Temperatura: " + asiento.getTemperatura()
                                                        + "\r\n ¡Cómo mola viajar con ComfortTravel!");
        intent.putExtra(Intent.EXTRA_TEXT, mensajeParaEnviar);
        shareProv.setShareIntent(intent);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Otra forma de hacer lo mismo que se hace hacia arriba
        if(item.getItemId()==R.id.mnu_guardar){

            final AlertDialog.Builder dialogoGuardar = new AlertDialog.Builder(AsientoActivity.this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_guardar_perfil, null);
            dialogoGuardar.setView(dialogView);

            dialogoGuardar.setTitle("Nombre del nuevo perfil");
            final EditText nuevoModo = (EditText) dialogView.findViewById(R.id.editText_nombre_perfil_nuevo);
            dialogoGuardar.setPositiveButton("Hecho", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (nuevoModo.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Add a value", Toast.LENGTH_SHORT).show();
                    } else {
                        if (listaDePerfiles.indexOf(nuevoModo.getText().toString()) != -1) {
                            Toast.makeText(getApplicationContext(), "Ya existe un perfil con ese nombre", Toast.LENGTH_SHORT).show();
                        } else {
                            System.out.println("aiento a añadir: " + asiento);
                            listaDePerfiles.add(nuevoModo.getText().toString());
                            System.out.println(perfilesDeAsiento);
                            asiento.setNombreModo(nuevoModo.getText().toString());
                            perfilesDeAsiento.add(asiento);
                            System.out.println(perfilesDeAsiento);
                            llenarArrayConPerfiles(perfilesDeAsiento);

                            System.out.println("tamaño del array de asientos " + perfilesDeAsiento.size() + " y el array de strings " + listaDePerfiles);
                            System.out.println("y el asiento que hay ahora es  " + asiento);

                            listnav.notifyDataSetChanged();

                            Toast.makeText(getApplicationContext(), "Perfil añadido", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            dialogoGuardar.setNegativeButton("Cancelar",new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick (DialogInterface dialogInterface,int i){
                        }
                    }
            );
            dialogoGuardar.show();
            return true;
        }
        if(item.getItemId()==R.id.mnu_borrar){

            AlertDialog.Builder builder = new AlertDialog.Builder(AsientoActivity.this);
            builder.setMessage("¿Desea borrar el perfil " + asiento.getNombreModo().toLowerCase() + " ?");
            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (asiento.getNombreModo().equals("Manual")){
                        Toast.makeText(getApplicationContext(), "No se puede borrar el perfil manual", Toast.LENGTH_SHORT).show();
                    }else{
                        System.out.println("aiento a borrar: " + asiento);
                        listaDePerfiles.remove(asiento.getNombreModo());
                        System.out.println(perfilesDeAsiento);
                        perfilesDeAsiento.remove(asiento);
                        System.out.println(perfilesDeAsiento);
                        llenarArrayConPerfiles(perfilesDeAsiento);
                        asiento = perfilesDeAsiento.get(0);
                        System.out.println("tamaño del array de asientos " + perfilesDeAsiento.size() + " y el array de strings " + listaDePerfiles);
                        System.out.println("y el asiento que hay ahora es  " + asiento);
                        onNavigationItemSelected(0,0);
                        listnav.notifyDataSetChanged();
                        onNavigationItemSelected(0,0);
                        listnav.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "boton de sí", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(getApplicationContext(), "boton de no", Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
            return true;
        }
        return false;
    }



    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {

        //Asiento elegido = buscarEnArray(getResources().getStringArray(R.array.perfiles)[itemPosition]);
        asiento = buscarEnArray(listaDePerfiles.get(itemPosition));
        modificarValoresAsiento (asiento);

        invalidateOptionsMenu(); //esta invocación sirve para destuir el menu y dejarlo guardado, para asi por si se comparte tener en el intent de compartir
        //el nuevo valor de grados y de ciudad
        return true;
    }

    /**
     * Aquí debería realizarse la modficación del asiento con los valores del perfil seleccionado
     * @param elegido el asiento con los valores del perfil elegido
     */
    private void modificarValoresAsiento(Asiento elegido) {
        Toast.makeText(this, "Cambiando al perfil " + elegido.getNombreModo() + " temperatura= " + elegido.getTemperatura(), Toast.LENGTH_SHORT).show();
    }

    private ArrayList<String> llenarArrayConPerfiles (ArrayList<Asiento> perfilesDeAsiento){
        ArrayList<String> respuesta = new ArrayList<String>();
        for (int i=0; i<perfilesDeAsiento.size(); i++){
            respuesta.add(perfilesDeAsiento.get(i).getNombreModo());
        }

        return respuesta;
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
        if (encontrado){
            return respuesta;
        }else{
            return null;
        }
    }

}

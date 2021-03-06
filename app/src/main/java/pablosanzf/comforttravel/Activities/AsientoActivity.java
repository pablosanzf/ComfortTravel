package pablosanzf.comforttravel.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.Toast;


import com.facebook.login.LoginManager;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import pablosanzf.comforttravel.Domain.Asiento;
import pablosanzf.comforttravel.Net.SimpleHttpClient;
import pablosanzf.comforttravel.Persistance.AsientoManager;
import pablosanzf.comforttravel.R;
import pablosanzf.comforttravel.Receivers.BateriaBajaReceiver;


public class AsientoActivity extends Activity implements
        ActionBar.OnNavigationListener{

    private static final String PERFIL_SELECCIONADO= "perfil seleccionado";

    private Asiento asiento;

    private ArrayList<Asiento> perfilesDeAsiento;
    private static final  Asiento NOCHE = new Asiento(new String("NOCHE"), new String("Noche"), 10,10 ,10,14,100);
    private static final  Asiento LECTURA = new Asiento(new String("LECTURA"), new String("Lectura"), 20,20 ,20,20,200);
    private static final  Asiento SEGURIDAD = new Asiento(new String("SEGURIDAD"), new String("Seguridad"), 30,30 ,30,24,300);

    private ArrayList<String>  listaDePerfiles;
    private ArrayList<String>  listaDePerfilesBorrar;


    ArrayAdapter<String> listnav;

    private ImageView imagenAsiento;
    private ImageView imagenTemperatura;
    private ImageView imagenLuminosidad;
    private ImageView imagenComida;
    private ImageView imagenLocalizacion;
    private ImageView imagenAsistencia;

    public static final int BORRAR_PERFIL = 1;
    public static final int MODIFICAR_TEMPERATURA = 2;
    public static final int MODIFICAR_LUMINOSIDAD = 3;
    public static final int ROTAR_ASIENTO = 4;

    private static final int REQUEST_PERMISSION_LOCATION_UPDATES = 1;

    private BroadcastReceiver mReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        crearListaAsientos();

        setContentView(R.layout.activity_asiento);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);


        listnav = new ArrayAdapter<String>(getActionBarThemedContextCompat(), android.R.layout.simple_spinner_item , listaDePerfiles);
        listnav.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionBar.setListNavigationCallbacks(listnav,this);



        //onNavigationItemSelected(0,0);
        asiento = buscarEnArray(listaDePerfiles.get(0));


        imagenAsiento = (ImageView) findViewById(R.id.asiento);
        imagenTemperatura = (ImageView) findViewById(R.id.temperatura);
        imagenLuminosidad =  (ImageView) findViewById(R.id.linterna);
        imagenComida = (ImageView) findViewById(R.id.comida);
        imagenLocalizacion= (ImageView) findViewById(R.id.localizacion);
        imagenAsistencia = (ImageView) findViewById(R.id.asistencia);

        getActionBar().getSelectedNavigationIndex();

        imagenAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarAsistencia();
            }
        });

        imagenTemperatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (asiento.getNombreModo().equals("Seguridad")){
                    Toast.makeText(getApplicationContext(), getString(R.string.inmodificableSeguridad), Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(AsientoActivity.this, TemperaturaActivity.class);
                    intent.putExtra(TemperaturaActivity.MODIFICAR_TEMPERATURA, asiento);
                    startActivityForResult(intent, MODIFICAR_TEMPERATURA);
                }
            }
        });

        imagenLuminosidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (asiento.getNombreModo().equals("Seguridad")){
                    Toast.makeText(getApplicationContext(), getString(R.string.inmodificableSeguridad), Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(AsientoActivity.this, LuminosidadActivity.class);
                    intent.putExtra(LuminosidadActivity.MODIFICAR_LUMINOSIDAD, asiento);
                    startActivityForResult(intent, MODIFICAR_LUMINOSIDAD);
                }
            }
        });

        imagenAsiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (asiento.getNombreModo().equals("Seguridad")){
                    Toast.makeText(getApplicationContext(), getString(R.string.inmodificableSeguridad), Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(AsientoActivity.this, RotacionActivity.class);
                    intent.putExtra(RotacionActivity.ROTAR_ASIENTO, asiento);
                    startActivityForResult(intent, ROTAR_ASIENTO);
                }
            }
        });

        imagenComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AsientoActivity.this, ComidaActivity.class);
                startActivity(intent);
            }
        });

        imagenLocalizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     Intent intent = new Intent(AsientoActivity.this, pablosanzf.comforttravel.MapsActivity.class);
                      startActivity(intent);
            }
        });

        mReceiver = new BateriaBajaReceiver();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{

                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, REQUEST_PERMISSION_LOCATION_UPDATES);
        }

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private void crearListaAsientos() {

        perfilesDeAsiento = (new AsientoManager(getApplicationContext())).cargarPerfiles();


        if (perfilesDeAsiento == null ){
            perfilesDeAsiento = new ArrayList<Asiento>();
            this.asiento = new Asiento("", "Manual", 50,100,0,12,400);
            perfilesDeAsiento.add(asiento);
            perfilesDeAsiento.add(SEGURIDAD);
            perfilesDeAsiento.add(NOCHE);
            perfilesDeAsiento.add(LECTURA);
            listaDePerfiles = new ArrayList<String>();
            listaDePerfiles = llenarArrayConPerfiles(perfilesDeAsiento);
        }else{
            listaDePerfiles = llenarArrayConPerfiles(perfilesDeAsiento);
        }

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        //super.onStop();
        Log.i("Stop", "Saving data");
        (new AsientoManager(getApplicationContext())).guardarPerfiles(perfilesDeAsiento);
        unregisterReceiver(mReceiver);
        super.onStop();
    }
    @Override
    protected void onStart() {
        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        super.onStart();
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
        String mensajeParaEnviar = getString(R.string.mns_com_1) + "\r\n" + getString(R.string.mns_com_2) + " " +  asiento.getRotacionReposapies() + " \r\n"
                + getString(R.string.mns_oom_3) + " " + asiento.getRotacionAsiento() + "\r\n" + getString(R.string.mns_com_4) + " " + asiento.getRotacionCabeza()
                + "\r\n" + getString(R.string.mns_com_5) + " " + asiento.getLuminosidad() + "\r\n" +getString(R.string.mns_com_6) + " " + asiento.getTemperatura()
                + "\r\n" + getString(R.string.mns_com_7);
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

            dialogoGuardar.setTitle(getString(R.string.tit_dia_guardar));
            final EditText nuevoModo = (EditText) dialogView.findViewById(R.id.editText_nombre_perfil_nuevo);
            dialogoGuardar.setPositiveButton(getString(R.string.hecho), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (nuevoModo.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), getString(R.string.vacio), Toast.LENGTH_SHORT).show();
                    } else {
                        if (listaDePerfiles.indexOf(nuevoModo.getText().toString()) != -1) {
                            Toast.makeText(getApplicationContext(), getString(R.string.existe_perfil), Toast.LENGTH_SHORT).show();
                        } else {
                            System.out.println("aiento a añadir: " + asiento);
                            listaDePerfiles.add(nuevoModo.getText().toString());
                            Asiento añadir = new Asiento(asiento.getIdentificador(), nuevoModo.getText().toString(), asiento.getRotacionCabeza(), asiento.getRotacionAsiento(), asiento.getRotacionReposapies(), asiento.getTemperatura(), asiento.getLuminosidad());
                            perfilesDeAsiento.add(añadir);
                            asiento = añadir;

                            System.out.println(perfilesDeAsiento);
                            llenarArrayConPerfiles(perfilesDeAsiento);

                            onNavigationItemSelected(perfilesDeAsiento.size()-1,perfilesDeAsiento.size()-1);
                            listnav.notifyDataSetChanged();
                            getActionBar().setSelectedNavigationItem(perfilesDeAsiento.size()-1);

                            Toast.makeText(getApplicationContext(), getString(R.string.perfil_añadido), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            dialogoGuardar.setNegativeButton(getString(R.string.cancelar),new DialogInterface.OnClickListener()
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

            listaDePerfilesBorrar = new ArrayList<String>();
            llenarArrayConPerfilesBorrar();
            Intent borrarPerfilIntent = new Intent(this, BorrarActivity.class);
            borrarPerfilIntent.putExtra(BorrarActivity.BORRAR_PERFILES , listaDePerfilesBorrar);
            startActivityForResult(borrarPerfilIntent, BORRAR_PERFIL);


            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BORRAR_PERFIL){ // If it was an ADD_ITEM, then add the new item and update the list
            if(resultCode == Activity.RESULT_OK){
                borrarAsiento(data.getStringExtra(BorrarActivity.BORRAR_PERFILES));
                listaDePerfiles.remove(data.getStringExtra(BorrarActivity.BORRAR_PERFILES));
                listnav.notifyDataSetChanged();
            }
        }else if(requestCode == MODIFICAR_TEMPERATURA){
            if(resultCode == Activity.RESULT_OK){
                asiento = (Asiento) data.getSerializableExtra(TemperaturaActivity.MODIFICAR_TEMPERATURA);
               //¿¿¿talvez --> listnav.notifyDataSetChanged();???
            }
        }else if(requestCode == MODIFICAR_LUMINOSIDAD){
            if(resultCode == Activity.RESULT_OK) {
                asiento = (Asiento) data.getSerializableExtra(LuminosidadActivity.MODIFICAR_LUMINOSIDAD);
                System.out.println("El asiento " + asiento.getNombreModo() + " tiene luminosidad: " + asiento.getLuminosidad());
            }
        }else if(requestCode == ROTAR_ASIENTO){
                if(resultCode == Activity.RESULT_OK){
                    asiento = (Asiento) data.getSerializableExtra(RotacionActivity.ROTAR_ASIENTO);
                    System.out.println("El asiento tras la rotación es " + asiento);
                }
        }
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {

        //Asiento elegido = buscarEnArray(getResources().getStringArray(R.array.perfiles)[itemPosition]);
        asiento = buscarEnArray(listaDePerfiles.get(itemPosition));
        modificarValoresArduino((int)asiento.getRotacionCabeza(), asiento.getTemperatura(), asiento.getLuminosidad());

        invalidateOptionsMenu(); //esta invocación sirve para destuir el menu y dejarlo guardado, para asi por si se comparte tener en el intent de compartir
        //el nuevo valor de grados y de ciudad
        return true;
    }



    private ArrayList<String> llenarArrayConPerfiles (ArrayList<Asiento> perfilesDeAsiento){
        ArrayList<String> respuesta = new ArrayList<String>();
        for (int i=0; i<perfilesDeAsiento.size(); i++){
            respuesta.add(perfilesDeAsiento.get(i).getNombreModo());
        }
        return respuesta;
    }

    private void llenarArrayConPerfilesBorrar() {
        listaDePerfilesBorrar.removeAll(listaDePerfilesBorrar);
        for (int i=0; i<listaDePerfiles.size(); i++){
            if(listaDePerfiles.get(i).equals("Manual") || listaDePerfiles.get(i).equals("Seguridad") || listaDePerfiles.get(i).equals(asiento.getNombreModo())){
            }else{
                listaDePerfilesBorrar.add(listaDePerfiles.get(i));
            }
        }

    }


    private Asiento buscarEnArray (String perfil){

        Asiento respuesta = new Asiento();
        Boolean encontrado = false;
        int i=0;
        while (!encontrado && i < perfilesDeAsiento.size()) {
            if (perfil.equals(perfilesDeAsiento.get(i).getNombreModo())){
                return perfilesDeAsiento.get(i);
            }else{
                i++;
            }
        }
        return null;
    }

    private void borrarAsiento (String perfil){
        int i=0;
        while ( i < perfilesDeAsiento.size()) {
            if (perfil.equals(perfilesDeAsiento.get(i).getNombreModo())){
                perfilesDeAsiento.remove(i);
            }else{
                i++;
            }
        }
    }

    private void llamarAsistencia(){
        AlertDialog.Builder dialogoAsistencia = new AlertDialog.Builder(AsientoActivity.this);
        dialogoAsistencia.setMessage(getString(R.string.asistencia));
        dialogoAsistencia.setPositiveButton(getString(R.string.si), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Lo necesario para que se encienda un led
                arduinoLed(true);

            }
        });
        dialogoAsistencia.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogoAsistencia.show();


    }

    private void arduinoLed(Boolean bool) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() ) {
            // OK -> Access the Internet
            new ArduinoLed().execute(bool);

        } else {
            // No -> Display error message
            Toast.makeText(this, R.string.msg_error_no_connection, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Convenience class to access the Internet and update UI elements
     */
    private class ArduinoLed extends AsyncTask<Boolean, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Boolean... booleans) {
            Boolean activar = booleans[0];
            String url;
            if(activar){
                url = getString(R.string.service_uri) + "arduino/add.php?device_id=4&data_name=led&data_value=1";
            }else{
                url = getString(R.string.service_uri) + "arduino/add.php?device_id=4&data_name=led&data_value=0";
            }

            SimpleHttpClient shc = new SimpleHttpClient(url);
            shc.doGet();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(!result)
                Toast.makeText(getApplicationContext(), R.string.msg_error_server, Toast.LENGTH_SHORT).show();
        }
    }

    private void modificarValoresArduino(int rotacionCabeza, int temperatura, double luminosidad) {
            arduinoServo(rotacionCabeza);
            arduinoTemperaturaAdd(temperatura);
            arduinoLuminosidadAdd(luminosidad);
    }

    private void arduinoServo(int progress) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() ) {
            // OK -> Access the Internet
            new ArduinoServo().execute(progress);

        } else {
            // No -> Display error message
            Toast.makeText(this, R.string.msg_error_no_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private class ArduinoServo extends AsyncTask<Integer, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... progresses) {
            int progress = progresses[0];
            String url;
            url = getString(R.string.service_uri) + "arduino/add.php?device_id=4&data_name=servo&data_value=" + progress;
            SimpleHttpClient shc = new SimpleHttpClient(url);
            shc.doGet();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(!result)
                Toast.makeText(getApplicationContext(), R.string.msg_error_server, Toast.LENGTH_SHORT).show();
        }
    }

    private void arduinoTemperaturaAdd(int temperatura) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() ) {
            // OK -> Access the Internet
            new ArduinoTemperaturaAdd().execute(temperatura);
        } else {
            // No -> Display error message
            Toast.makeText(this, R.string.msg_error_no_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private class ArduinoTemperaturaAdd extends AsyncTask<Integer, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... progresses) {
            int progress = progresses[0];
            String url;
            url = getString(R.string.service_uri) + "arduino/add.php?device_id=4&data_name=new_temp&data_value=" + progress;
            SimpleHttpClient shc = new SimpleHttpClient(url);
            shc.doGet();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(!result)
                Toast.makeText(getApplicationContext(), R.string.msg_error_server, Toast.LENGTH_SHORT).show();
        }
    }

    private void arduinoLuminosidadAdd(double luminosidad) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() ) {
            // OK -> Access the Internet
            new ArduinoLuminosidadAdd().execute(luminosidad);
        } else {
            // No -> Display error message
            Toast.makeText(this, R.string.msg_error_no_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private class ArduinoLuminosidadAdd extends AsyncTask<Double, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Double... progresses) {
            Double progress = progresses[0];
            String url;
            url = getString(R.string.service_uri) + "arduino/add.php?device_id=4&data_name=new_lum&data_value=" + progress;
            SimpleHttpClient shc = new SimpleHttpClient(url);
            shc.doGet();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(!result)
                Toast.makeText(getApplicationContext(), R.string.msg_error_server, Toast.LENGTH_SHORT).show();
        }
    }
}

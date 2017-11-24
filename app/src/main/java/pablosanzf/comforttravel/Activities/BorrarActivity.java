package pablosanzf.comforttravel.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import pablosanzf.comforttravel.Domain.Asiento;
import pablosanzf.comforttravel.R;

public class BorrarActivity extends Activity {

    public static final String BORRAR_PERFILES = "borrar_perfiles";
    ArrayList<String> perfilesDeAsiento;

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar);

        radioGroup = findViewById(R.id.radioGroupBorrar);

        perfilesDeAsiento = (ArrayList<String>) getIntent().getSerializableExtra(BORRAR_PERFILES);
        if(perfilesDeAsiento != null)
            crearRadioButtons(perfilesDeAsiento);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.borrar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_aceptar_borrar:

                int id= radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(id);
                int radioId = radioGroup.indexOfChild(radioButton);
                RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
                String selection = (String) btn.getText();

                System.out.println("EL seleccioando es " + selection);

                Intent intentResult = new Intent();
                intentResult.putExtra(BORRAR_PERFILES, selection);
                setResult(Activity.RESULT_OK, intentResult);
                finish();
                break;


            // If cancel, simply return back
            case R.id.mnu_cancelar_borrar:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void crearRadioButtons(ArrayList<String> perfilesDeAsiento) {

        final RadioButton[] rb = new RadioButton[perfilesDeAsiento.size()];
        radioGroup.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
        for(int i=0; i<perfilesDeAsiento.size(); i++){
            rb[i]  = new RadioButton(this);
            rb[i].setText(perfilesDeAsiento.get(i));
            rb[i].setId(1 +i);
            rb[i].setTextSize(20);
            if(perfilesDeAsiento.size()!=0){
                rb[0].setChecked(true);
            }
            radioGroup.addView(rb[i]);
        }


    }
}

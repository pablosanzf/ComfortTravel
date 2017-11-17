package pablosanzf.comforttravel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

/**
 * Created by master on 17/11/2017.
 */

public class TemperaturaActivity extends Activity {

    private TextView temperaturaActual;
    private TextView temperaturaSeleccioanda;
    private Button mas;
    private Button menos;

    private Asiento asiento;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_temperatura);

        if(getIntent().getStringExtra("asiento")!= null) this.asiento = getIntent().getStringExtra(asiento);



        Toolbar toolbar = (Toolbar) findViewById(R.id);
        toolbar.setTitle("New Payment");
        setSupportActionBar(toolbar);

        tripService = ServiceFactory.getTripService(getApplicationContext());
        if (getIntent().getStringExtra("trip_id") != null) this.tripId = getIntent().getStringExtra("trip_id");

        trip = tripService.getTrip(this.tripId);
        travelerList = trip.getTravelers();

        donePayment = (Button) findViewById(R.id.donePayment);
        totalPayment = (EditText)  findViewById(R.id.total_payment);
        travelersLayout = (LinearLayout) findViewById(R.id.travelers_layout);
        travelersLayout.removeAllViews();




        travelerList = new ArrayList<String>();
        //travelerList = trip.getTravelers();
        travelerList.add("Juan");
        travelerList.add("Pedro");
        travelerList.add(trip.getDestination());
        String traveler8 = new String();
        if (travelerList.get(0) != null){
            traveler8 = travelerList.get(0);
        }else{
            traveler8 = "esta nulo";
        }
        Log.d("ha llegado hasta aqui", traveler8);






















    }
}

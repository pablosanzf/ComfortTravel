package pablosanzf.comforttravel.Activities;


import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pablosanzf.comforttravel.Adapter.MiArrayAdapter;
import pablosanzf.comforttravel.Domain.Plato;
import pablosanzf.comforttravel.R;

public class ComidaActivity extends Activity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;



    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comida, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.asistencia_comida) {
            llamarAsistencia();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void llamarAsistencia(){
        AlertDialog.Builder dialogoAsistencia = new AlertDialog.Builder(ComidaActivity.this);
        dialogoAsistencia.setMessage(getString(R.string.asistenciaComida));
        dialogoAsistencia.setPositiveButton(getString(R.string.si), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Sí, por favor", Toast.LENGTH_SHORT).show();
                //Lo necesario para que se encienda un led

            }
        });
        dialogoAsistencia.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "No, de momento", Toast.LENGTH_SHORT).show();
            }
        });
        dialogoAsistencia.show();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private ArrayList<Plato> arrPlatos;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_comida, container, false);


            ListView lista = (ListView) rootView.findViewById(R.id.lista_comida);
            ListAdapter listAdapter = lista.getAdapter();

            arrPlatos = new ArrayList<Plato>();


            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                    arrPlatos.clear();
                    Plato macarrones = new Plato( "Macarrones", 12.50);

                    arrPlatos.add(new Plato(1,R.drawable.valet, "Macarrones", 12.50) );

//                    arrPlatos.add(new Plato("Espaguetis", 12.50) );
//                    arrPlatos.add(new Plato("Ensalada", 12.50) );
//                    arrPlatos.add(new Plato("Vainas", 12.50) );
//                    arrPlatos.add(new Plato("Alubias", 12.50) );
                    break;
                case 2:
                    arrPlatos.clear();
                    //                   arrPlatos.add(new Plato("Ternera", 12.50) );
//                    arrPlatos.add(new Plato("Chipirones", 12.50) );
//                    arrPlatos.add(new Plato("Merluza", 12.50) );
//                    arrPlatos.add(new Plato("Bonito", 12.50) );
//                    arrPlatos.add(new Plato("Pollo", 10.0) );
                    break;
                case 3:
                    arrPlatos.clear();
                    //                   arrPlatos.add(new Plato("Arroz con leche", 12.50) );
                    //                   arrPlatos.add(new Plato("Flan", 12.50) );
//                    arrPlatos.add(new Plato("Helado", 12.50) );
                    //                   arrPlatos.add(new Plato("Fruta", 12.50) );
//                    arrPlatos.add(new Plato("Rosco de reyes", 10.0) );
                    break;
                case 4:
                    arrPlatos.clear();
//                    arrPlatos.add(new Plato("Agua", 12.50) );
//                    arrPlatos.add(new Plato("Vino", 12.50) );
//                    arrPlatos.add(new Plato("Gintonic", 12.50) );
//                    arrPlatos.add(new Plato("Cerveza", 12.50) );
//                    arrPlatos.add(new Plato("Kas naranja", 10.0) );
                    break;

            }


            MiArrayAdapter madpPlatos = new MiArrayAdapter(getContext(), arrPlatos);


           /* ArrayAdapter<Plato> adpPlatos = new ArrayAdapter<Plato>(getContext(), R.layout.food_list_row, R.id.nombrePlato , arrPlatos) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView nombrePlato = (TextView) view.findViewById(R.id.nombrePlato);
                    TextView precioPlato = (TextView) view.findViewById(R.id.precioPlato);
                    nombrePlato.setText(arrPlatos.get(position).getNombrePlato());
                    precioPlato.setText("Precio: " + String.valueOf(arrPlatos.get(position).getPrecio()));
                    return view;
                }
            };*/

            /*ArrayAdapter<Plato> adpPlatos = new ArrayAdapter<Plato>(getContext(), android.R.layout.simple_list_item_activated_2, android.R.id.text1, arrPlatos) {
                @Override
                 public View getView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getView(position, convertView, parent);
                                        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                                        TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                                       text1.setText(arrPlatos.get(position).getNombrePlato());
                                        text2.setText("Precio: " + String.valueOf(arrPlatos.get(position).getPrecio()));
                                        return view;
                                   }
             };*/

            lista.setAdapter(madpPlatos);



            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Primeros";
                case 1:
                    return "Segundos";
                case 2:
                    return "Postres";
                case 3:
                    return "Bebidas";
            }
            return null;
        }
    }
}

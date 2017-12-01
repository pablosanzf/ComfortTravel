package pablosanzf.comforttravel.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pablosanzf.comforttravel.Domain.Plato;
import pablosanzf.comforttravel.R;

public class MiArrayAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<Plato> arrayListPlatos;

    public MiArrayAdapter(@NonNull Context context,  ArrayList<Plato> arrayListPlatos ) {
        this.context = context;
        this.arrayListPlatos = arrayListPlatos;
    }


    @Override
    public int getCount() {
        return arrayListPlatos.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListPlatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayListPlatos.get(position).getIdentificador();
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View rowView=inflater.inflate(R.layout.food_list_row, null);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.nombrePlato);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imagenPlato);
        TextView extratxt = (TextView) rowView.findViewById(R.id.precioPlato);

        txtTitle.setText(arrayListPlatos.get(position).getNombrePlato());
        imageView.setImageResource(arrayListPlatos.get(position).getImagenComida());
        extratxt.setText("Precio:  "+ String.valueOf(arrayListPlatos.get(position).getPrecio() + " €"));
        return rowView;

    }
}

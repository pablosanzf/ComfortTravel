package pablosanzf.comforttravel.Domain;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by master on 28/11/2017.
 */

public class Plato implements Serializable {

    private ImageView imagenComida;
    private String nombrePlato;
    private double precio;

    public Plato(ImageView imagenComida, String nombrePlato, double precio) {
        this.imagenComida = imagenComida;
        this.nombrePlato = nombrePlato;
        this.precio = precio;
    }

    public Plato(String nombrePlato, double precio) {
        this.nombrePlato = nombrePlato;
        this.precio = precio;
    }

    public Plato() {
        this.nombrePlato = "";
        this.precio = 0.0;
    }

    public ImageView getImagenComida() {
        return imagenComida;
    }

    public void setImagenComida(ImageView imagenComida) {
        this.imagenComida = imagenComida;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Plato{" +
                "nombrePlato='" + nombrePlato + '\'' +
                ", precio=" + precio +
                '}';
    }
}

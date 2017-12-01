package pablosanzf.comforttravel.Domain;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by master on 28/11/2017.
 */

public class Plato implements Serializable {


    private int identificador;
    private int imagenComida;
    private String nombrePlato;
    private double precio;

    public Plato(int identificador, int imagenComida, String nombrePlato, double precio) {
        this.identificador = identificador;
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

    public int getImagenComida() {
        return imagenComida;
    }

    public void setImagenComida(int imagenComida) {
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
    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }


    @Override
    public String toString() {
        return "Plato{" +
                "identificador=" + identificador +
                ", nombrePlato='" + nombrePlato + '\'' +
                ", precio=" + precio +
                '}';
    }
}

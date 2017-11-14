package pablosanzf.comforttravel;

import java.io.Serializable;

/**
 * Created by master on 10/11/2017.
 */

public class Asiento implements Serializable {

    private String identificador;
    private String nombreModo;
    private double rotacionCabeza;
    private double rotacionAsiento;
    private double rotacionReposapies;
    private double temperatura;
    private double luminosidad;

    public Asiento(){
        identificador = new String("");
        nombreModo = new String("");
        rotacionCabeza = new Double(0.0);
        rotacionAsiento = new Double(0.0);
        rotacionReposapies = new Double (0.0);
        temperatura = new Double (0.0);
        luminosidad = new Double (0.0);
    }

    public Asiento(String identificador) {
        this.identificador = identificador;
        nombreModo = new String("");
        rotacionCabeza = new Double(0.0);
        rotacionAsiento = new Double(0.0);
        rotacionReposapies = new Double (0.0);
        temperatura = new Double (0.0);
        luminosidad = new Double (0.0);
    }

    public Asiento(String identificador, String nombreModo, double rotacionCabeza, double rotacionAsiento, double rotacionReposapies, double temperatura, double luminosidad) {

        this.identificador = identificador;
        this.nombreModo = nombreModo;
        this.rotacionCabeza = rotacionCabeza;
        this.rotacionAsiento = rotacionAsiento;
        this.rotacionReposapies = rotacionReposapies;
        this.temperatura = temperatura;
        this.luminosidad = luminosidad;
    }


    @Override
    public String toString() {
        return "Asiento{" +
                "identificador='" + identificador + '\'' +
                ", nombreModo='" + nombreModo + '\'' +
                ", rotacionCabeza=" + rotacionCabeza +
                ", rotacionAsiento=" + rotacionAsiento +
                ", rotacionReposapies=" + rotacionReposapies +
                ", temperatura=" + temperatura +
                ", luminosidad=" + luminosidad +
                '}';
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombreModo() {
        return nombreModo;
    }

    public void setNombreModo(String nombreModo) {
        this.nombreModo = nombreModo;
    }

    public double getRotacionCabeza() {
        return rotacionCabeza;
    }

    public void setRotacionCabeza(double rotacionCabeza) {
        this.rotacionCabeza = rotacionCabeza;
    }

    public double getRotacionAsiento() {
        return rotacionAsiento;
    }

    public void setRotacionAsiento(double rotacionAsiento) {
        this.rotacionAsiento = rotacionAsiento;
    }

    public double getRotacionReposapies() {
        return rotacionReposapies;
    }

    public void setRotacionReposapies(double rotacionReposapies) {
        this.rotacionReposapies = rotacionReposapies;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getLuminosidad() {
        return luminosidad;
    }

    public void setLuminosidad(double luminosidad) {
        this.luminosidad = luminosidad;
    }
}

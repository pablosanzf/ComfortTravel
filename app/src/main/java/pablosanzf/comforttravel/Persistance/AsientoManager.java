package pablosanzf.comforttravel.Persistance;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import pablosanzf.comforttravel.Domain.Asiento;

/**
 * Created by master on 10/11/2017.
 */

public class AsientoManager {

    private static final String FILENAME = "PerfilesAsientos";
    private Context mContext;


    public AsientoManager(Context c){
        mContext = c;
    }

    public ArrayList<Asiento> cargarPerfiles(){
        try {
            FileInputStream fis = mContext.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Asiento> arr = (ArrayList<Asiento>) ois.readObject();
            ois.close();
            fis.close();
            return arr;
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void guardarPerfiles(ArrayList<Asiento> arr){
        try {
            FileOutputStream fos = mContext.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(arr);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

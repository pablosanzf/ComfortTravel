package pablosanzf.comforttravel;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

        import java.util.ArrayList;

/**
 * Created by ivazquez on 16/10/2015.
 */
public class SQLiteAsientoHelper extends SQLiteOpenHelper{

    // Database name and version
    private static final String DATABASE_NAME = "asientoList.db";
    private static final int DATABASE_VERSION = 1;

    // Table and columns names (_id is required as primary key)
    private static final String TABLE_ASIENTOS = "asientos";
    private static final String COLUMN_PERFIL = "nombrePerfil";
    private static final String COLUMN_ID = "identificador";
    private static final String COLUMN_ROTACION_CABEZA = "rotacionCabeza";
    private static final String COLUMN_ROTACION_ASIENTO = "rotacionAsiento";
    private static final String COLUMN_ROTACION_PIES = "rotacionPies";
    private static final String COLUMN_TEMPERATURA = "temperatura";
    private static final String COLUMN_LUMINOSIDAD = "luminosidad";





    // SQL sentence to create the tables
    private static final String DATABASE_CREATE = "create table "
            + TABLE_ASIENTOS + "(" + COLUMN_PERFIL
            + " text primary key , " + COLUMN_ID
            + " text not null, " + COLUMN_ROTACION_CABEZA + " double not null, " +  COLUMN_ROTACION_ASIENTO +   " double not null, "
            + COLUMN_ROTACION_PIES +  " double not null, " + COLUMN_TEMPERATURA +  " double not null, " + COLUMN_LUMINOSIDAD + " double not null); ";

    public SQLiteAsientoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Executed when creating the DB for first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    // Executed when upgrading to a new version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASIENTOS);
        onCreate(db);
    }

    // Convenience method to store a student in the database
    public void putAsiento(Asiento asiento){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PERFIL, asiento.getNombreModo());
        values.put(COLUMN_ID, asiento.getIdentificador());
        values.put(COLUMN_ROTACION_CABEZA, asiento.getRotacionCabeza());
        values.put(COLUMN_ROTACION_ASIENTO, asiento.getRotacionAsiento());
        values.put(COLUMN_ROTACION_PIES, asiento.getRotacionReposapies());
        values.put(COLUMN_TEMPERATURA, asiento.getTemperatura());
        values.put(COLUMN_LUMINOSIDAD, asiento.getLuminosidad());

        db.insert(TABLE_ASIENTOS, null, values);
    }

    // Convenience method to retrieve all the students from the database
    public ArrayList<Asiento> getAsientos(){
        ArrayList<Asiento> as = new ArrayList<Asiento>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_ASIENTOS,
                new String[]{COLUMN_PERFIL, COLUMN_ID, COLUMN_ROTACION_CABEZA, COLUMN_ROTACION_ASIENTO,COLUMN_ROTACION_PIES,COLUMN_TEMPERATURA,COLUMN_LUMINOSIDAD},
                null,
                null,
                null,
                null,
                null);

        // Alternative
        //Cursor cursor = db.rawQuery("select "+ COLUMN_NAME + "," + COLUMN_PHONE_NUMNER + " from " + TABLE_STUDENTS,null);

        cursor.moveToNext();
        while(!cursor.isAfterLast()){
            String perfil = cursor.getString(0);
            String id = cursor.getString(1);
            Double rotacionCabeza = Double.parseDouble(cursor.getString(2));
            Double rotacionAsiento = Double.parseDouble(cursor.getString(3));
            Double rotacionPies = Double.parseDouble(cursor.getString(4));
            Double temperatura = Double.parseDouble(cursor.getString(5));
            Double luminosidad = Double.parseDouble(cursor.getString(6));

            as.add(new Asiento(id, perfil, rotacionCabeza,rotacionAsiento,rotacionPies,temperatura,luminosidad));

            cursor.moveToNext();
        }
        return as;
    }

    public boolean borrarAsiento(Asiento asiento){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ASIENTOS, asiento.getNombreModo() + "=" + COLUMN_PERFIL , null)>0;
    }

    public boolean borrarTodosLosAsientos(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ASIENTOS, null, null)>0;
    }


}
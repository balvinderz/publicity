package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Nitu on 1/2/2016.
 */
public class EventDatabaseOperations extends SQLiteOpenHelper {

    public static final int database_version=1;
    public String CREATE_QUERY="CREATE TABLE "+
            EventTable.TableInfo.TABLE_NAME+"("+
            EventTable.TableInfo.NAME+" TEXT," +
            EventTable.TableInfo.COST+" INTEGER);";

    public EventDatabaseOperations(Context context) {
        super(context, EventTable.TableInfo.DATABASE_NAME, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void putInformation(EventDatabaseOperations dop,String name,int cost)
    {
        SQLiteDatabase SQ=dop.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(EventTable.TableInfo.NAME,name);
        cv.put(EventTable.TableInfo.COST,cost);
        long k=SQ.insert(EventTable.TableInfo.TABLE_NAME,null,cv);

    }

    public Cursor getInformation(EventDatabaseOperations  dop)
    {
        SQLiteDatabase SQ=dop.getWritableDatabase();
        String []columns={EventTable.TableInfo.NAME,EventTable.TableInfo.COST};
        Cursor CR=SQ.query(EventTable.TableInfo.TABLE_NAME,columns,null,null,null,null,null);
        return CR;
    }
    public void clearAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ EventTable.TableInfo.TABLE_NAME);
        db.close();
    }
}

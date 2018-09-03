package database;

import android.provider.BaseColumns;

/**
 * Created by Patil on 26-08-2016.
 */
public class EventTable {
    public static abstract class TableInfo implements BaseColumns
    {

        public static final String DATABASE_NAME="Publicity";
        public static final String TABLE_NAME="Event";
        public static final String NAME="name";
        public static final String COST="cost";
    }
}

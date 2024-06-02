package persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDAO extends SQLiteOpenHelper {

    private static final String Database = "Team.11";

    private static final int Database_Ver = 1;

    private static final String Create_Table_Team = "";

    private static final String Create_Table_Player = "";

    public GenericDAO(Context context){
        super(context, Database, null, Database_Ver);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table_Team);
        db.execSQL(Create_Table_Player);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(newVersion > oldVersion){
            db.execSQL("DROP TABLE IF EXISTS team");
            db.execSQL("DROP TABLE IF EXISTS player");
            onCreate(db);
        }

    }
}

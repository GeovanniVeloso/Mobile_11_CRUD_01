package persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDAO extends SQLiteOpenHelper {

    private static final String Database = "Team.11";

    private static final int Database_Ver = 1;

    private static final String Create_Table_Team = "CREATE TABLE team (id INT NOT NULL PRIMARY KEY,name VARCHAR(100) NOT NULL,city VARCHAR(30) NOT NULL);";

    private static final String Create_Table_Player = "CREATE TABLE player (number INT NOT NULL PRIMARY KEY,name VARCHAR(100) NOT NULL,birthDate VARCHAR(30) NOT NULL,height DECIMAL(6,2) NOT NULL,weight DECIMAL(6,2) NOT NULL,team_id INT NOT NULL, FOREIGN KEY (team_id) REFERENCES team(id));";;

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

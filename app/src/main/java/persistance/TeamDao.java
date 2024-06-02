package persistance;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Team;

public class TeamDao implements ITeamDao, ICRUD<Team>{

    private final Context context;
    private GenericDAO GDao;
    private SQLiteDatabase DB;

    public TeamDao(Context context){
        this.context = context;
    }
    public TeamDao open() throws SQLException {
         GDao = new GenericDAO(context);
         DB = GDao.getWritableDatabase();
         return this;
    }

    @Override
    public void close() throws SQLException {
        GDao.close();
    }

    @Override
    public void insert(Team team) throws SQLException {
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", team.getId());
        contentValues.put("name", team.getName());
        contentValues.put("city", team.getCity());

        DB.insert("team",null,contentValues);
    }

    @Override
    public int update(Team team) throws SQLException {
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", team.getId());
        contentValues.put("name", team.getName());
        contentValues.put("city", team.getCity());

        int Ret = DB.update("team", contentValues, "id = "+team.getId(), null);

        return Ret;
    }

        @Override
        public void delete(Team team) throws SQLException {
            DB.delete("team","id = " + team.getId(),null);
        }

        @SuppressLint("Range")
        @Override
        public Team findOne(Team team) throws SQLException {

            String sql = "SELECT id, name, city FROM team WHERE id = " + team.getId();
            Cursor cursor = DB.rawQuery(sql,null);
            if(cursor != null){
                cursor.moveToFirst();
            }
            if(cursor.isAfterLast()){
                team.setId(cursor.getInt(cursor.getColumnIndex("id")));
                team.setName(cursor.getString(cursor.getColumnIndex("name")));
                team.setCity(cursor.getString(cursor.getColumnIndex("city")));
            }
            cursor.close();
            return team;
        }

        @SuppressLint("Range")
        @Override
        public List<Team> findAll() throws SQLException {
            List <Team> teams = new ArrayList<>();
            String sql = "SELECT id, name, city FROM team";
            Cursor cursor = DB.rawQuery(sql,null);
            if(cursor != null){
                cursor.moveToFirst();
            }
            while(!cursor.isAfterLast()){
                Team team = new Team();
                team.setId(cursor.getInt( cursor.getColumnIndex("id")));
                team.setName(cursor.getString(cursor.getColumnIndex("name")));
                team.setCity(cursor.getString(cursor.getColumnIndex("city")));

                teams.add(team);
                cursor.moveToNext();
            }
            cursor.close();
            return teams;
        }

}


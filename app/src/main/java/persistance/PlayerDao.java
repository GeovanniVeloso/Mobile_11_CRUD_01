package persistance;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Player;
import model.Team;

public class PlayerDao implements IPlayerDao, ICRUD<Player>{

    private final Context context;
    private GenericDAO GDao;
    private SQLiteDatabase DB;

    public PlayerDao(Context context){
        this.context = context;
    }

    @Override
    public PlayerDao open() throws SQLException {
        GDao = new GenericDAO(context);
        DB = GDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() throws SQLException {
        GDao.close();
    }

    @Override
    public void insert(Player player) throws SQLException {
        ContentValues contentValues = new ContentValues();

        contentValues.put("number", player.getNumber());
        contentValues.put("name", player.getName());
        contentValues.put ("birthDate", String.valueOf(player.getBirthDate()));
        contentValues.put("height", player.getHeight());
        contentValues.put("weight", player.getWeight());
        contentValues.put("number", player.getNumber());
        contentValues.put("team", player.getTeam().getId());

        DB.insert("player", null, contentValues);
    }

    @Override
    public int update(Player player) throws SQLException {
        ContentValues contentValues = new ContentValues();

        contentValues.put("number", player.getNumber());
        contentValues.put("name", player.getName());
        contentValues.put ("birthDate", String.valueOf(player.getBirthDate()));
        contentValues.put("height", player.getHeight());
        contentValues.put("weight", player.getWeight());
        contentValues.put("team", player.getTeam().getId());

        int Ret = DB.update("player",contentValues,"number = " + player.getNumber(), null);

        return Ret;
    }

    @Override
    public void delete(Player player) throws SQLException {
        DB.delete("player", "number = " + player.getNumber(), null);
    }

    @SuppressLint("Range")
    @Override
    public Player findOne(Player player) throws SQLException {

        String sql = "SELECT t.id  AS team_cod, t.name AS team_name, t.city AS team_city, p.number AS player_number" +
                " p.name AS player_name, p.birthDate AS player_birth, p.height AS player_height, p.weight AS player_weight, " +
                " FROM team t, player p WHERE t.id = p.team_cod " +
                "AND p.number = " + player.getNumber();

        Cursor cursor = DB.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        if(!cursor.isAfterLast()){
            Team team = new Team();
            team.setId(cursor.getInt(cursor.getColumnIndex("team_cod")));
            team.setName(cursor.getString(cursor.getColumnIndex("team_name")));
            team.setCity(cursor.getString(cursor.getColumnIndex("team_city")));

            player.setNumber(cursor.getInt(cursor.getColumnIndex("player_number")));
            player.setName(cursor.getString(cursor.getColumnIndex("player_name")));
            player.setBirthDate(LocalDate.parse(cursor.getString(cursor.getColumnIndex("player_birth"))));
            player.setHeight(cursor.getFloat(cursor.getColumnIndex("player_height")));
            player.setWeight(cursor.getFloat(cursor.getColumnIndex("player_weight")));
            player.setTeam(team);
        }
        cursor.close();

        return player;
    }

    @SuppressLint("Range")
    @Override
    public List<Player> findAll() throws SQLException {

        List<Player> players = new ArrayList<>();
        String sql = "SELECT t.id  AS team_cod, t.name AS team_name, t.city AS team_city, p.number AS player_number" +
                " p.name AS player_name, p.birthDate AS player_birth, p.height AS player_height, p.weight AS player_weight, " +
                " FROM team t, player p WHERE t.id = p.team_cod ";

        Cursor cursor = DB.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        while(!cursor.isAfterLast()){
            Player player = new Player();
            Team team = new Team();
            team.setId(cursor.getInt(cursor.getColumnIndex("team_cod")));
            team.setName(cursor.getString(cursor.getColumnIndex("team_name")));
            team.setCity(cursor.getString(cursor.getColumnIndex("team_city")));

            player.setNumber(cursor.getInt(cursor.getColumnIndex("player_number")));
            player.setName(cursor.getString(cursor.getColumnIndex("player_name")));
            player.setBirthDate(LocalDate.parse(cursor.getString(cursor.getColumnIndex("player_birth"))));
            player.setHeight(cursor.getFloat(cursor.getColumnIndex("player_height")));
            player.setWeight(cursor.getFloat(cursor.getColumnIndex("player_weight")));
            player.setTeam(team);

            players.add(player);
            cursor.moveToNext();
        }
        cursor.close();

        return players;
    }
}

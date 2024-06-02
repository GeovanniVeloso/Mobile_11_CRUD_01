package controller;

import java.sql.SQLException;
import java.util.List;

import model.Player;
import persistance.PlayerDao;

public class PlayerController implements IController<Player> {

    private final PlayerDao PDao;

    public PlayerController(PlayerDao PDao){
        this.PDao = PDao;
    }

    @Override
    public void insert(Player player) throws SQLException {
        if(PDao.open() == null){
            PDao.open();
        }
        PDao.insert(player);
        PDao.close();
    }

    @Override
    public void update(Player player) throws SQLException {
        if(PDao.open() == null){
            PDao.open();
        }
        PDao.update(player);
        PDao.close();
    }

    @Override
    public void delete(Player player) throws SQLException {
        if(PDao.open() == null){
            PDao.open();
        }
        PDao.delete(player);
        PDao.close();
    }

    @Override
    public Player search(Player player) throws SQLException {
        if(PDao.open() == null){
            PDao.open();
        }
        return PDao.findOne(player);
    }

    @Override
    public List<Player> list() throws SQLException {
        if(PDao.open() == null){
            PDao.open();
        }
        return PDao.findAll();
    }
}

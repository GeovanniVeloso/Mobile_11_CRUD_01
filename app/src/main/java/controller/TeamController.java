package controller;

import java.sql.SQLException;
import java.util.List;

import model.Team;
import persistance.TeamDao;

public class TeamController implements IController<Team>{

    private final TeamDao TDao;

    public TeamController(TeamDao TDao){
        this.TDao = TDao;
    }
    @Override
    public void insert(Team team) throws SQLException {
        if(TDao.open() == null){
            TDao.open();
        }
        TDao.insert(team);
        TDao.close();
    }

    @Override
    public void update(Team team) throws SQLException {
        if(TDao.open() == null){
            TDao.open();
        }
        TDao.update(team);
        TDao.close();
    }

    @Override
    public void delete(Team team) throws SQLException {
        if(TDao.open() == null){
            TDao.open();
        }
        TDao.delete(team);
        TDao.close();
    }

    @Override
    public Team search(Team team) throws SQLException {
        if(TDao.open() == null){
            TDao.open();
        }
        return TDao.findOne(team);
    }

    @Override
    public List<Team> list() throws SQLException {
        return TDao.findAll();
    }
}

package persistance;

import java.sql.SQLException;

public interface IPlayerDao {

    public PlayerDao open()throws SQLException;
    public void close()throws SQLException;

}

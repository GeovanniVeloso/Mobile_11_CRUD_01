package persistance;

import java.sql.SQLException;

public interface ITeamDao {

    public TeamDao open()throws SQLException;
    public void close()throws SQLException;

}

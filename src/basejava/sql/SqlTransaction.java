package basejava.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlTransaction<T> {
    T doTransaction(Connection conn) throws SQLException;
}

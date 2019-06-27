package basejava.sql;

import basejava.exception.ExistStorageException;
import basejava.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    // Method for usage only in clear-method from SqlStorage
    public void doExecute(String sql) {
        doExecute(sql, PreparedStatement::execute);
    }

    public <T> T doExecute(String sql, Executable<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.executeSql(ps);
        } catch (SQLException e) {
            if (e instanceof PSQLException) {
                throw new ExistStorageException(null);
            }
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface Executable<T> {
        T executeSql(PreparedStatement ps) throws SQLException;
    }
}


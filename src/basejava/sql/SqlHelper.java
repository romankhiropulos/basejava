package basejava.sql;

import basejava.exception.StorageException;

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
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T doTransactionExecute(TransactionExecutable<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.doTransaction(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface Executable<T> {
        T executeSql(PreparedStatement ps) throws SQLException;
    }

    @FunctionalInterface
    public interface TransactionExecutable<T> {
        T doTransaction(Connection conn) throws SQLException;
    }
}


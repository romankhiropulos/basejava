package basejava.storage;

import basejava.exception.NotExistStorageException;
import basejava.model.ContactType;
import basejava.model.Resume;
import basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.doExecute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doExecute("" +
                "    SELECT * FROM resume r " +
                " LEFT JOIN contact c " +
                "        ON r.uuid = c.resume_uuid " +
                "     WHERE r.uuid =? ", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                putContact(rs, resume);
            } while (rs.next());

            return resume;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.doTransactionExecute("UPDATE resume SET full_name = ? WHERE uuid = ?", (ps, conn) -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            checkResume(resume.getUuid(), ps);
            removeContacts(conn, resume);
            insertContacts(conn, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.doTransactionExecute("INSERT INTO resume (uuid, full_name) VALUES (?,?)", (ps, conn) -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
            insertContacts(conn, resume);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.doExecute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            checkResume(uuid, ps);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.doExecute("" +
                "   SELECT * FROM resume r\n" +
                "LEFT JOIN contact c ON r.uuid = c.resume_uuid\n" +
                "ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            Map<String, Resume> map = new LinkedHashMap<>();
            while (rs.next()) {
                Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                map.putIfAbsent(resume.getUuid(), resume);
                putContact(rs, map.get(resume.getUuid()));
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.doExecute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void checkResume(String uuid, PreparedStatement ps) throws SQLException {
        if (ps.executeUpdate() == 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    private void removeContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE  FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void insertContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void putContact(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }
}
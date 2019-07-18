package basejava.storage;

import basejava.exception.NotExistStorageException;
import basejava.model.*;
import basejava.sql.ConnectionFactory;
import basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        sqlHelper = new SqlHelper(new ConnectionFactory() {
            @Override
            public Connection getConnection() throws SQLException {
                return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            }
        });
    }

    @Override
    public void clear() {
        sqlHelper.doExecute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doTransactionExecute("SELECT * FROM resume WHERE uuid =?", (ps, conn) -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }

            Resume resume = new Resume(uuid, rs.getString("full_name"));

            try (PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid =?")) {
                ps2.setString(1, uuid);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    putContact(rs2, resume);
                }
            }

            try (PreparedStatement ps3 = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                ps3.setString(1, uuid);
                ResultSet rs3 = ps3.executeQuery();
                while (rs3.next()) {

                    putSection(rs3, resume);
                }
            }

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
            removeSections(conn, resume);
            insertContacts(conn, resume);
            insertSections(conn, resume);
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
            insertSections(conn, resume);
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

    /*
     * "map.putIfAbsent" allowing us don't overwrite value of resume,
     * but allowing to add all contacts by every unique resume in EnumMap<> "contacts".
     */
//    @Override
//    public List<Resume> getAllSorted() {  //method only for uuid, full_name and contacts
//        return sqlHelper.doExecute("" +
//                "   SELECT * FROM resume r\n" +
//                "LEFT JOIN contact c ON r.uuid = c.resume_uuid\n" +
//                "ORDER BY full_name, uuid", ps -> {
//            ResultSet rs = ps.executeQuery();
//            Map<String, Resume> map = new LinkedHashMap<>();
//            while (rs.next()) {
//                Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
//                map.putIfAbsent(resume.getUuid(), resume);
//                putContact(rs, map.get(resume.getUuid()));
//            }
//            return new ArrayList<>(map.values());
//        });
//    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.doTransactionExecute("SELECT * FROM resume ORDER BY full_name, uuid", (ps, conn) -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
            }

            try (PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    Resume resume = resumes.get(rs2.getString("resume_uuid"));
                    putContact(rs2, resume);
                }
            }

            try (PreparedStatement ps3 = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rs3 = ps3.executeQuery();
                while (rs3.next()) {
                    Resume resume = resumes.get(rs3.getString("resume_uuid"));
                    putSection(rs3, resume);
                }
            }

            return new ArrayList<>(resumes.values());
        });
    }

    /*
     * Here executeQuery return table which have only one column "count" with only one value "count of resumes".
     * After that getInt retrieves the value of the designated column in the current row of this ResultSet object
     * as an int in the Java programming language.
     */
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

    private void removeSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE  FROM section WHERE resume_uuid=?")) {
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

    private void insertSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSectionType().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());

                SectionType searchKey = entry.getKey();
                switch (searchKey) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection textSection = (TextSection) entry.getValue();
                        ps.setString(3, textSection.getContent());
                        ps.addBatch();
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ProgressSection progressSection = (ProgressSection) entry.getValue();
                        List<String> progress = progressSection.getProgress();
                        StringBuilder stringBuilder = new StringBuilder();
                        progress.forEach(x -> stringBuilder.append(x).append("\n"));
                        ps.setString(3, String.valueOf(stringBuilder));
                        ps.addBatch();
                        break;
                }
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

    private void putSection(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            SectionType searchKey = SectionType.valueOf(rs.getString("type"));
            switch (searchKey) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.addSection(searchKey, new TextSection(rs.getString("value")));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> list = new ArrayList<>(Arrays.asList(rs.getString("value").split("\n")));
                    resume.addSection(searchKey, new ProgressSection(list));
                    break;
            }
        }
    }
}
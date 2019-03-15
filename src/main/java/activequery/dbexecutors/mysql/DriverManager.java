/**
 * File DriverManager
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 06.03.19 22:57
 */

package activequery.dbexecutors.mysql;

import activequery.adapters.IQueryBuilder;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class DriverManager
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.dbexecutors.mysql
 * Created by 06.03.19 22:57
 */
public class DriverManager {

    private static final Logger LOGGER = Logger.getLogger(DriverManager.class.getName());

    private final ObjectMapper mObjectMapper;

    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;

    private final String dbDriver;
    private final String dbUser;
    private final String dbPassword;

    public DriverManager() {
        this(System.getenv("DB_DRIVER"), System.getenv("DB_USER"), System.getenv("DB_PASSWORD"));
    }

    public DriverManager(final String dbDriver, final String dbUser, final String dbPassword) {
        this.dbDriver = dbDriver;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        mObjectMapper = new ObjectMapper();
        mObjectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    public <T> List<T> executeQuery(final IQueryBuilder.Select query, final Class<T> tClass) {
        final List<T> resList = new ArrayList<>();
        for (Map<String, Object> map : executeQuery(query.getQuery().toString(), query.getQueryArguments(), query.getResultFieldsName())) {
            resList.add(mObjectMapper.convertValue(map, tClass));
        }
        return resList;
    }

    public List<Map<String, Object>> executeQuery(final IQueryBuilder.Select query) {
        return executeQuery(query.getQuery().toString(), Collections.emptyList(), query.getResultFieldsName());
    }

    public List<Map<String, Object>> executeQuery(final String query, final List<Object> args, final Set<String> fieldsName) {
        final List<Map<String, Object>> resList = new ArrayList<>();
        try {
            con = java.sql.DriverManager.getConnection(dbDriver, dbUser, dbPassword);

            stmt = con.prepareStatement(query);

            if (args != null && !args.isEmpty()) {
                for (int i = 1; i <= args.size(); i++) {
                    stmt.setObject(i, args.get(i - 1));
                }
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                final Map<String, Object> map = new ConcurrentHashMap<>();
                if (!fieldsName.isEmpty()) {
                    for (String fieldName : fieldsName) {
                        map.put(fieldName, rs.getObject(fieldName));
                    }
                } else {
                    final ResultSetMetaData rsmd = rs.getMetaData();
                    final int count = rsmd.getColumnCount();
                    for (int i = 1; i <= count; i++) {
                        final String columnName = rsmd.getColumnLabel(i);
                        map.put(columnName, getValue(rsmd.getColumnType(i), columnName));
                    }
                }
                resList.add(map);
            }
        } catch (Exception sqlEx) {
            LOGGER.log(Level.OFF, sqlEx.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception se) {
                LOGGER.log(Level.WARNING, "db connection close exception");
            }
            try {
                stmt.close();
            } catch (Exception se) {
                LOGGER.log(Level.WARNING, "PreparedStatement close exception");
            }
            try {
                rs.close();
            } catch (Exception se) {
                LOGGER.log(Level.WARNING, "ResultSet close exception");
            }
        }
        return resList;
    }

    public void executeUpdate(final IQueryBuilder.Save query) {
        executeUpdate(query.getQuery().toString(), query.getQueryArguments());
    }

    public void executeUpdate(final String query, final List<Object> args) {
        try {
            con = java.sql.DriverManager.getConnection(dbDriver, dbUser, dbPassword);
            stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            if (args != null && !args.isEmpty()) {
                for (int i = 1; i <= args.size(); i++) {
                    stmt.setObject(i, args.get(i - 1));
                }
            }
            stmt.executeUpdate();
        } catch (Exception sqlEx) {
            LOGGER.log(Level.OFF, sqlEx.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception se) {
                LOGGER.log(Level.WARNING, "db connection close exception");
            }
            try {
                stmt.close();
            } catch (Exception se) {
                LOGGER.log(Level.WARNING, "PreparedStatement close exception");
            }
        }
    }

    private Object getValue(final int type, final String columnName) throws SQLException {
        switch (type) {
            case Types.DATE:
                return rs.getDate(columnName);
            case Types.TIMESTAMP:
                return rs.getTimestamp(columnName);
            case Types.TIME:
                return rs.getTime(columnName);
            case Types.INTEGER:
                return rs.getInt(columnName);
            case Types.VARCHAR:
                return rs.getString(columnName);
            case Types.BOOLEAN:
                return rs.getBoolean(columnName);
            case Types.DOUBLE:
                return rs.getDouble(columnName);
            case Types.FLOAT:
                return rs.getFloat(columnName);
            default:
                return rs.getObject(columnName);
        }
    }
}

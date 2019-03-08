/**
 * File DriverManager
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 06.03.19 22:57
 */

package activequery.dbexecutors.mysql;

import activequery.adapters.MysqlQueryBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    private final MysqlQueryBuilder mMysqlQueryBuilder;

    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;

    public DriverManager() {
        mMysqlQueryBuilder = new MysqlQueryBuilder();
        mObjectMapper = new ObjectMapper();
    }

    public DriverManager(MysqlQueryBuilder mysqlQueryBuilder) {
        mMysqlQueryBuilder = mysqlQueryBuilder;
        mObjectMapper = new ObjectMapper();
    }

    public <T> List<T> executeQueryGet(final Class<T> aClass) {
        return executeQuery(mMysqlQueryBuilder.getQuery().toString(), aClass, mMysqlQueryBuilder.getQueryArgs());
    }

    public <T> List<T> executeQuery(final String query, final Class<T> tClass) {
        return executeQuery(query, tClass, null);
    }

    public <T> List<T> executeQuery(final String query, final Class<T> tClass, final List<Object> args) {
        final List<T> resList = new ArrayList<>();
        for (Map<String, Object> map : executeQuery(query, args)) {
            resList.add(mObjectMapper.convertValue(map, tClass));
        }
        return resList;
    }

    public List<Map<String, Object>> executeQuery(final String query) {
        return executeQuery(query, Collections.emptyList());
    }

    public List<Map<String, Object>> executeQuery(final String query, final List<Object> args) {
        final List<Map<String, Object>> resList = new ArrayList<>();
        try {
            con = java.sql.DriverManager.getConnection(System.getenv("DB_DRIVER"), System.getenv("DB_USER"), System.getenv("DB_PASSWORD"));

            stmt = con.prepareStatement(query);

            if (args != null && !args.isEmpty()) {
                for (int i = 1; i <= args.size(); i++) {
                    stmt.setObject(i, args.get(i - 1));
                }
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                final Map<String, Object> map = new ConcurrentHashMap<>();
                for (String fieldName : mMysqlQueryBuilder.getResultFieldsName()) {
                    map.put(fieldName, rs.getObject(fieldName));
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

    public void executeUpdate(final String query) {
        executeUpdate(query, null);
    }

    public void executeUpdate(final String query, final List<Object> args) {
        try {
            con = java.sql.DriverManager.getConnection(System.getenv("DB_DRIVER"), System.getenv("DB_USER"), System.getenv("DB_PASSWORD"));
            stmt = con.prepareStatement(query);
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
}

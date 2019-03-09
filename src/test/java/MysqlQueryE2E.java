/**
 * File MysqlQueryE2E
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 08.03.19 12:11
 */

import activequery.dbexecutors.mysql.DriverManager;
import dto.UserResult;
import models.User;
import models.UserRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Class MysqlQueryE2E
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package PACKAGE_NAME
 * Created by 08.03.19 12:11
 */
public class MysqlQueryE2E {

    private User mUser;

    @Before
    public void init() {
        final Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, 2018);
        calendar.set(Calendar.MONTH, 10);
        calendar.set(Calendar.DAY_OF_MONTH, 27);
        calendar.set(Calendar.HOUR, 20);
        calendar.set(Calendar.MINUTE, 18);
        calendar.set(Calendar.SECOND, 23);
        mUser = new User(1, "test@example.com", new Date(calendar.getTimeInMillis()));
        final DriverManager driverManager = new DriverManager();
        final String createUsersTable = "CREATE TABLE users " +
            "(ID INTEGER not NULL, " +
            " email VARCHAR(255), " +
            " created_at DATETIME(6), " +
            " PRIMARY KEY ( id ))";
        driverManager.executeUpdate(createUsersTable);

        final String createUserRoleTable = "CREATE TABLE users_roles " +
            "(ID INTEGER not NULL, " +
            " users_id INTEGER, " +
            " roles INTEGER, " +
            " PRIMARY KEY ( id ))";
        driverManager.executeUpdate(createUserRoleTable);

        final String insertUser = "INSERT INTO users VALUES (1, 'test@example.com', '2018-11-28 20:18:23')";
        final String insertUserRole = "INSERT INTO users_roles VALUES (1, 1, 2)";
        driverManager.executeUpdate(insertUser);
        driverManager.executeUpdate(insertUserRole);
    }

    @Test
    public void getUserJoinRole() {
        final List<User> pairRes = new User()
            .select(User.Fields.id.as("id"), User.Fields.email, UserRole.roleId, User.Fields.createdAt)
            .innerJoin(UserRole.class, UserRole.userId.eq(User.Fields.id))
            .get();
        Assert.assertEquals(mUser.getId(), pairRes.get(0).getId());
        Assert.assertEquals(mUser.getEmail(), pairRes.get(0).getEmail());
        // TODO: 08.03.19
//        Assert.assertEquals(userResult.getCreatedAt().getYear(), pairRes.get(0).getCreatedAt().getYear());
//        Assert.assertEquals(userResult.getCreatedAt().getMonth(), pairRes.get(0).getCreatedAt().getMonth());
//        Assert.assertEquals(userResult.getCreatedAt().getDay(), pairRes.get(0).getCreatedAt().getDay());
    }
}

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

import java.util.List;

import static activequery.aggregations.All.ALL;

/**
 * Class MysqlQueryE2E
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package PACKAGE_NAME
 * Created by 08.03.19 12:11
 */
public class MysqlQueryE2E {

    private UserResult userResult;

    @Before
    public void init() {
        userResult = new UserResult(1, "test@example.com", 2);
        final DriverManager driverManager = new DriverManager();
        final String createUsersTable = "CREATE TABLE users " +
            "(ID INTEGER not NULL, " +
            " email VARCHAR(255), " +
            " PRIMARY KEY ( id ))";
        driverManager.executeUpdate(createUsersTable);

        final String createUserRoleTable = "CREATE TABLE users_roles " +
            "(ID INTEGER not NULL, " +
            " users_id INTEGER, " +
            " roles INTEGER, " +
            " PRIMARY KEY ( id ))";
        driverManager.executeUpdate(createUserRoleTable);

        final String insertUser = "INSERT INTO users VALUES (1, 'test@example.com')";
        final String insertUserRole = "INSERT INTO users_roles VALUES (1, 1, 2)";
        driverManager.executeUpdate(insertUser);
        driverManager.executeUpdate(insertUserRole);
    }

    @Test
    public void getUserJoinRole() {
        final List<UserResult> pairRes = new User()
            .select(User.id.as("id"), User.email, UserRole.roleId)
            .innerJoin(UserRole.class, UserRole.userId.eq(User.id))
            .get(UserResult.class);
        Assert.assertEquals(userResult.getId(), pairRes.get(0).getId());
        Assert.assertEquals(userResult.getEmail(), pairRes.get(0).getEmail());
        Assert.assertEquals(userResult.getRoles(), pairRes.get(0).getRoles());
    }
}

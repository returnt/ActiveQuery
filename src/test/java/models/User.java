package models; /**
 * File models.User
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 23.02.19 18:48
 */

import activequery.conditions.AbstractField;
import activequery.dbexecutors.mysql.Model;
import activequery.operators.SimpleField;

/**
 * Class models.User
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package PACKAGE_NAME
 * Created by 23.02.19 18:48
 */
public class User extends Model {

    public static AbstractField id = new SimpleField("users", "id", "ID");
    public static AbstractField email = new SimpleField("email");
    public static AbstractField createdAt = new SimpleField("created_at");

    @Override
    public String tableName() {
        return "users";
    }
}
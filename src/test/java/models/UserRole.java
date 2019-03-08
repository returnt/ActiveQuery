package models; /**
 * File models.User
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 23.02.19 18:48
 */

import activequery.conditions.AbstractField;
import activequery.conditions.Field;
import activequery.conditions.ITableSchema;
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
public class UserRole extends Model implements ITableSchema {

    public static AbstractField userId = new AbstractField(new Field() {
        @Override
        public String table() {
            return "users_roles";
        }

        @Override
        public String field() {
            return "users_id";
        }

        @Override
        public String alias() {
            return "roleId";
        }
    }) {
    };

    public static AbstractField roleId = new SimpleField("roles");

    @Override
    public String tableName() {
        return "users_roles";
    }
}
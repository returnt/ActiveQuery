/**
 * File MySqlActiveQueryE2E
 *
 * Author d.a.ganzha
 * Project parser-java
 * Created by 23.02.19 11:23
 */

import activequery.ActiveQuery;
import activequery.IQuerySource;
import activequery.adapters.MysqlSaveQueryBuilder;
import models.User;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class MySqlActiveQueryE2E
 *
 * Author d.a.ganzha
 * Project parser-java
 * Package ru.returnt.parser
 * Created by 23.02.19 11:23
 */
public class MySqlSaveActiveQueryE2E {

    private static final String INSERT = "INSERT INTO users (`id`, `email`) VALUES (?, ?)";
    private static final String INSERT_MANY = "INSERT INTO users (`id`, `email`) VALUES (?, ?), (?, ?)";

    @Test
    public void insert() {
        final IQuerySource.Save<MysqlSaveQueryBuilder> query =
            ActiveQuery.save(new MysqlSaveQueryBuilder(), User.class)
                .insert(User.Fields.id, User.Fields.email)
                .values(23, "returnt.ru@gmail.com");
        Assert.assertEquals(INSERT, query.build().getQuery().toString());

        final IQuerySource.Save<MysqlSaveQueryBuilder> query2 =
            ActiveQuery.save(new MysqlSaveQueryBuilder(), User.class)
                .insert(User.Fields.id, User.Fields.email)
                .values(23, "returnt.ru@gmail.com")
                .values(24, "returnt+2.ru@gmail.com");
        Assert.assertEquals(INSERT_MANY, query2.build().getQuery().toString());

        IQuerySource.Save<MysqlSaveQueryBuilder> query3 =
            ActiveQuery.save(new MysqlSaveQueryBuilder(), User.class)
                .insert(User.Fields.id, User.Fields.email);
        for (int i = 0; i < 2; i++) {
            query3 = query3.values(i, "returnt.ru@gmail.com");
        }

        Assert.assertEquals(INSERT_MANY, query3.build().getQuery().toString());
    }

}

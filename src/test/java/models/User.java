package models; /**
 * File models.User
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 23.02.19 18:48
 */

import activequery.annotations.PrimaryKey;
import activequery.conditions.AbstractField;
import activequery.dbexecutors.mysql.Model;
import activequery.operators.SimpleField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Date;

/**
 * Class models.User
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package PACKAGE_NAME
 * Created by 23.02.19 18:48
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends Model {

    @PrimaryKey
    private Integer id;
    private String email;
    private Date createdAt;

    public User() {
    }

    public User(Integer id, String email, Date createdAt) {
        this.id = id;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String tableName() {
        return "users";
    }

    public static class Fields {
        public static AbstractField id = new SimpleField("users", "id", "ID");
        public static AbstractField email = new SimpleField("email");
        public static AbstractField createdAt = new SimpleField("created_at");
    }
}
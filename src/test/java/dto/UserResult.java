/**
 * File UserResult
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 04.03.19 23:47
 */

package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Date;

/**
 * Class UserResult
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package dto
 * Created by 04.03.19 23:47
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResult {

    private Integer id;
    private String email;
    private Integer roles;
    private Date createdAt;

    public UserResult() {
    }

    public UserResult(Integer id, String email, Integer roles, Date createdAt) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Integer getRoles() {
        return roles;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}

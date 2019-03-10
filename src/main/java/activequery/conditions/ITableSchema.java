/**
 * File ITableSchema
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 03.03.19 19:05
 */

package activequery.conditions;

import activequery.aggregations.All;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Interface ITableSchema
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.conditions
 * Created by 03.03.19 19:05
 */
public interface ITableSchema {

    String tableName();

    @JsonIgnore
    default Class getModelClass() {
        return getClass();
    }

    default Field ALL() {
        return new All(getModelClass());
    }
}

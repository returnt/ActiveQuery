/**
 * File ITableSchema
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 03.03.19 19:05
 */

package activequery.conditions;

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

    default Class getModelClass() {
        return getClass();
    }

    default Field ALL() {
        return new Field() {
            @Override
            public String table() {
                return tableName();
            }

            @Override
            public String field() {
                return "*";
            }
        };
    }
}

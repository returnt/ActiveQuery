/**
 * File All
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 25.02.19 21:21
 */

package activequery.aggregations;

import activequery.conditions.AbstractField;
import activequery.conditions.Field;
import activequery.conditions.ITableSchema;

import java.util.logging.Logger;

/**
 * Class All
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.aggregations
 * Created by 25.02.19 21:21
 */
public class All extends AbstractField {

    private static final Logger LOGGER = Logger.getLogger(All.class.getName());

    public All(final Class aClass) {
        super(new Field() {
            @Override
            public String table() {
                try {
                    return ((ITableSchema) aClass.newInstance()).tableName();
                } catch (InstantiationException | IllegalAccessException e) {
                    LOGGER.warning(e.getMessage());
                }
                return null;
            }

            @Override
            public String field() {
                return "*";
            }
        });
    }

    public static All ALL(final Class aClass) {
        return new All(aClass);
    }
}

/**
 * File Count
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 24.02.19 22:12
 */

package activequery.aggregations;

import activequery.conditions.AbstractField;
import activequery.conditions.Field;

/**
 * Class Count
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.aggregations
 * Created by 24.02.19 22:12
 */
public class Count extends AbstractField {

    public Count(Field field) {
        super(field);
    }

    public static Count COUNT(Field field) {
        return new Count(field);
    }
}

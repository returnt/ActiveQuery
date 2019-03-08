/**
 * File Max
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 24.02.19 22:20
 */

package activequery.aggregations;

import activequery.conditions.AbstractField;
import activequery.conditions.Field;

/**
 * Class Max
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.aggregations
 * Created by 24.02.19 22:20
 */
public class Max extends AbstractField {

    public Max(Field field) {
        super(field);
    }

    public static Max MAX(Field field) {
        return new Max(field);
    }
}

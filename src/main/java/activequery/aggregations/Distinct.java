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

/**
 * Class All
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.aggregations
 * Created by 25.02.19 21:21
 */
public class Distinct extends AbstractField {

    public Distinct(final Field field) {
        super(field);
    }

    public static Distinct DISTINCT(final Field field) {
        return new Distinct(field);
    }
}

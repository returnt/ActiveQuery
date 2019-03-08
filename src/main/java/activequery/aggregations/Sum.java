/**
 * File Sum
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 24.02.19 22:24
 */

package activequery.aggregations;

import activequery.conditions.AbstractField;
import activequery.conditions.Field;

/**
 * Class Sum
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.aggregations
 * Created by 24.02.19 22:24
 */
public class Sum extends AbstractField {

    public Sum(Field field) {
        super(field);
    }

    public static Sum SUM(Field field) {
        return new Sum(field);
    }
}

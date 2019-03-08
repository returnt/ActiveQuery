/**
 * File Avg
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 24.02.19 22:18
 */

package activequery.aggregations;

import activequery.conditions.AbstractField;
import activequery.conditions.Field;

/**
 * Class Avg
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.aggregations
 * Created by 24.02.19 22:18
 */
public class Avg extends AbstractField {

    public Avg(Field field) {
        super(field);
    }

    public static Avg AVG(Field field) {
        return new Avg(field);
    }
}

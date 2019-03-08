/**
 * File Min
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 24.02.19 22:23
 */

package activequery.aggregations;

import activequery.conditions.AbstractField;
import activequery.conditions.Field;

/**
 * Class Min
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.aggregations
 * Created by 24.02.19 22:23
 */
public class Min extends AbstractField {

    public Min(Field field) {
        super(field);
    }

    public static Min MIN(Field field) {
        return new Min(field);
    }
}

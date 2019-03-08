/**
 * File All
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 25.02.19 21:21
 */

package activequery.aggregations;

import activequery.conditions.AbstractField;

/**
 * Class All
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.aggregations
 * Created by 25.02.19 21:21
 */
public class All extends AbstractField {

    public All() {
        super(() -> "*");
    }

    public static All ALL() {
        return new All();
    }
}

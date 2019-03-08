/**
 * File OrderBy
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 03.03.19 21:26
 */

package activequery.conditions;

/**
 * Interface OrderBy
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.conditions
 * Created by 03.03.19 21:26
 */
public interface OrderBy {

    Field field();

    default OrderDirection direction() {
        return OrderDirection.ASC;
    }
}

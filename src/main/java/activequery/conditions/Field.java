/**
 * File Field
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 23.02.19 19:04
 */

package activequery.conditions;

/**
 * Interface Field
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.conditions
 * Created by 23.02.19 19:04
 */
public interface Field {

    default String table() {
        return null;
    }

    String field();

    default String alias() {
        return null;
    }

    default Field as(final String s) {
        return this;
    }
}

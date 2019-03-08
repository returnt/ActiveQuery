/**
 * File Join
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 03.03.19 19:03
 */

package activequery.conditions;

/**
 * Interface Join
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.conditions
 * Created by 03.03.19 19:03
 */
public interface Join {

    Class table();

    void on(WhereGroup whereGroup);
}

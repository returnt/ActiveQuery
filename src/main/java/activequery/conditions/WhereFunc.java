/**
 * File WhereFunc
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 23.02.19 19:58
 */

package activequery.conditions;

/**
 * Interface WhereFunc
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.conditions
 * Created by 23.02.19 19:58
 */
public interface WhereFunc {

    void condition(final WhereGroup func);

    interface WhereCondition {

        void and(Object o);

        void or(Object o);
    }

    interface Where {

        Object l();

        Condition operator();

        Object r();
    }
}

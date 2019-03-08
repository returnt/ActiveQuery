/**
 * File WhereOperator
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 03.03.19 23:10
 */

package activequery.conditions;

import java.util.List;

/**
 * Interface WhereOperator
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.conditions
 * Created by 03.03.19 23:10
 */
public interface WhereOperator {

    WhereFunc.Where eq(final Object field);

    WhereFunc.Where neq(final Object field);

    WhereFunc.Where gt(final Object field);

    WhereFunc.Where lt(final Object field);

    WhereFunc.Where gte(final Object field);

    WhereFunc.Where lte(final Object field);

    WhereFunc.Where between(final List<Object> field);

    WhereFunc.Where like(final Object field);

    WhereFunc.Where in(final List<Object> field);
}

/**
 * File OrderByActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 03.03.19 21:26
 */

package activequery.operators;

import activequery.ActiveQuery;
import activequery.adapters.IQueryBuilder;
import activequery.conditions.OrderBy;

/**
 * Class OrderByActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.operators
 * Created by 03.03.19 21:26
 */
public class OrderByActiveQuery<T extends IQueryBuilder.Select> extends ActiveQuery<T> {

    private final OrderBy[] mOrderBy;

    public OrderByActiveQuery(final ActiveQuery<T> activeQuery, final OrderBy... orderBy) {
        super(activeQuery);
        mOrderBy = orderBy;
    }

    @Override
    public void subscribeActual() throws Exception {
        mActiveQuery.subscribeActual();
        for (OrderBy order : mOrderBy) {
            mQueryBuilder.applyOrderBy(order.field(), order.direction());
        }
    }
}
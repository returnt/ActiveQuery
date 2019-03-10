/**
 * File LimitActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 03.03.19 22:55
 */

package activequery.operators;

import activequery.ActiveQuery;
import activequery.adapters.IQueryBuilder;

/**
 * Class LimitActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.operators
 * Created by 03.03.19 22:55
 */
public class LimitActiveQuery<T extends IQueryBuilder.Select> extends ActiveQuery<T> {

    private final Long mLimit;

    public LimitActiveQuery(final ActiveQuery<T> activeQuery, final Long limit) {
        super(activeQuery);
        mLimit = limit;
    }

    @Override
    public void subscribeActual() throws Exception {
        mActiveQuery.subscribeActual();
        mQueryBuilder.applyLimit(mLimit);
    }
}
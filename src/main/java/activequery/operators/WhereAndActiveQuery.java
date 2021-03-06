/**
 * File WhereGroupActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 23.02.19 20:40
 */

package activequery.operators;

import activequery.ActiveQuery;
import activequery.adapters.IQueryBuilder;
import activequery.conditions.WhereFunc;
import activequery.conditions.WhereGroup;

/**
 * Class WhereGroupActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.operators
 * Created by 23.02.19 20:40
 */
public class WhereAndActiveQuery<T extends IQueryBuilder.Select> extends ActiveQuery<T> {

    private final WhereFunc.Where[] mFunc;

    public WhereAndActiveQuery(final ActiveQuery<T> activeQuery, final WhereFunc.Where... func) {
        super(activeQuery);
        mFunc = func;
    }

    @Override
    public void subscribeActual() throws Exception {
        mActiveQuery.subscribeActual();
        final WhereGroup whereGroup = new WhereGroup();
        for (WhereFunc.Where where : mFunc) {
            whereGroup.and(where);
        }
        mQueryBuilder.applyWhere(whereGroup);
    }

}
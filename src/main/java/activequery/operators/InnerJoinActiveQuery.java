/**
 * File InnerJoinActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 03.03.19 19:00
 */

package activequery.operators;

import activequery.ActiveQuery;
import activequery.adapters.IQueryBuilder;
import activequery.conditions.ITableSchema;
import activequery.conditions.Join;
import activequery.conditions.WhereGroup;
import activequery.exceptions.CastTableTypeExeption;

/**
 * Class InnerJoinActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.operators
 * Created by 03.03.19 19:00
 */
public class InnerJoinActiveQuery<T extends IQueryBuilder.Select> extends ActiveQuery<T> {

    private final Join mJoin;

    public InnerJoinActiveQuery(final ActiveQuery<T> activeQuery, final Join join) {
        super(activeQuery);
        mJoin = join;
    }

    @Override
    public void subscribeActual() throws Exception {
        mActiveQuery.subscribeActual();
        final Object o = mJoin.table().newInstance();
        if (o instanceof ITableSchema) {
            final ITableSchema tableSchema = (ITableSchema) o;
            final WhereGroup whereGroup = new WhereGroup();
            mJoin.on(whereGroup);
            mQueryBuilder.applyInnerJoin(tableSchema.tableName(), whereGroup);
        } else {
            throw new CastTableTypeExeption(o.getClass().getName());
        }
    }
}
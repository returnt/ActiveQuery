/**
 * File FromActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 23.02.19 17:54
 */

package activequery.operators;

import activequery.ActiveQuery;
import activequery.adapters.IQueryBuilder;
import activequery.conditions.ITableSchema;

/**
 * Class FromActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.operators
 * Created by 23.02.19 17:54
 */
public class FromActiveQuery<T extends IQueryBuilder.Select> extends ActiveQuery<T> {

    private final Class[] mTables;

    public FromActiveQuery(final ActiveQuery<T> activeQuery, final Class... tables) {
        super(activeQuery);
        mTables = tables;
    }

    @Override
    public void subscribeActual() throws Exception {
        mActiveQuery.subscribeActual();
        for (Class table : mTables) {
            final ITableSchema tableSchema = (ITableSchema) table.newInstance();
            mQueryBuilder.applyFrom(tableSchema.tableName());
        }
    }
}

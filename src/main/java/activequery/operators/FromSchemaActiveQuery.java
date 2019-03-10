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
public class FromSchemaActiveQuery<T extends IQueryBuilder.Select> extends ActiveQuery<T> {

    private final ITableSchema[] mTables;

    public FromSchemaActiveQuery(final ActiveQuery<T> activeQuery, final ITableSchema... tables) {
        super(activeQuery);
        mTables = tables;
    }

    @Override
    public void subscribeActual() throws Exception {
        for (ITableSchema table : mTables) {
            mQueryBuilder.applyFrom(table.tableName());
        }
    }
}

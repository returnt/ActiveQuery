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
public class FromActiveQuery<T extends IQueryBuilder> extends ActiveQuery<T> {

    private final Class<? extends ITableSchema>[] mTables;

    public FromActiveQuery(final ActiveQuery<T> activeQuery, final Class<? extends ITableSchema>... tables) {
        super(activeQuery);
        mTables = tables;
    }

    @Override
    public void subscribeActual() throws Exception {
        for (Class<? extends ITableSchema> table : mTables) {
            final ITableSchema tableSchema = table.newInstance();
            mQueryBuilder.applyFrom(tableSchema.tableName());
        }
    }
}

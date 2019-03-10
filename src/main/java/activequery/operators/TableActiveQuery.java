/**
 * File TableActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 10.03.19 12:12
 */

package activequery.operators;

import activequery.ActiveQuerySave;
import activequery.adapters.IQueryBuilder;
import activequery.conditions.ITableSchema;

/**
 * Class TableActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery.operators
 * Created by 10.03.19 12:12
 */
public class TableActiveQuery<T extends IQueryBuilder.Save> extends ActiveQuerySave<T> {

    private final Class mTable;

    public TableActiveQuery(final ActiveQuerySave<T> activeQuery, final Class table) {
        super(activeQuery);
        mTable = table;
    }

    @Override
    public void subscribeActual() throws Exception {
        mActiveQuery.subscribeActual();
        final ITableSchema tableSchema = (ITableSchema) mTable.newInstance();
        mQueryBuilder.applyTable(tableSchema.tableName());
    }
}

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
public class TableSchemaActiveQuery<T extends IQueryBuilder.Save> extends ActiveQuerySave<T> {

    private final ITableSchema mTableSchema;

    public TableSchemaActiveQuery(final ActiveQuerySave<T> activeQuery, final ITableSchema tableSchema) {
        super(activeQuery);
        mTableSchema = tableSchema;
    }

    @Override
    public void subscribeActual() throws Exception {
        mActiveQuery.subscribeActual();
        mQueryBuilder.applyTable(mTableSchema.tableName());
    }
}

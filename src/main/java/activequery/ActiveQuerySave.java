/**
 * File ActiveQuerySave
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 10.03.19 11:39
 */

package activequery;

import activequery.adapters.IQueryBuilder;
import activequery.conditions.Field;
import activequery.conditions.ITableSchema;
import activequery.operators.InsertActiveQuery;
import activequery.operators.TableActiveQuery;
import activequery.operators.TableSchemaActiveQuery;
import activequery.operators.ValueActiveQuery;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class ActiveQuerySave
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery
 * Created by 10.03.19 11:39
 */
public class ActiveQuerySave<T extends IQueryBuilder.Save> implements IQuerySource.Save<T> {

    private static final Logger LOGGER = Logger.getLogger(ActiveQuerySave.class.getName());

    protected final ActiveQuerySave<T> mActiveQuery;
    protected final T mQueryBuilder;

    ActiveQuerySave(final T queryBuilder) {
        mActiveQuery = this;
        mQueryBuilder = queryBuilder;
    }

    protected ActiveQuerySave(final ActiveQuerySave<T> activeQuery) {
        mActiveQuery = activeQuery;
        mQueryBuilder = activeQuery.mQueryBuilder;
    }

    @Override
    public IQuerySource.Save<T> table(final Class table) {
        IQuerySource.requireNonNull(table, "table is null");
        return new TableActiveQuery<>(this, table);
    }

    @Override
    public IQuerySource.Save<T> table(final ITableSchema table) {
        IQuerySource.requireNonNull(table, "table is null");
        return new TableSchemaActiveQuery<>(this, table);
    }

    @Override
    public IQuerySource.Save<T> insert(final Field... fields) {
        IQuerySource.requireNonNull(fields, "fields is null");
        return new InsertActiveQuery<>(this, fields);
    }

    @Override
    public IQuerySource.Save<T> values(final Object... values) {
        IQuerySource.requireNonNull(values, "values is null");
        return new ValueActiveQuery<>(this, values);
    }

    @Override
    public T build() {
        try {
            subscribeActual();
        } catch (Exception e) {
            LOGGER.log(Level.OFF, e.getMessage());
        }
        if (mQueryBuilder != null)
            mQueryBuilder.build();
        return mQueryBuilder;
    }
}

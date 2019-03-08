/**
 * File IQuerySource
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 23.02.19 18:24
 */

package activequery;

import activequery.adapters.IQueryBuilder;
import activequery.conditions.*;

/**
 * Interface IQuerySource
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3
 * Created by 23.02.19 18:24
 */
public interface IQuerySource<T extends IQueryBuilder> {

    default void subscribeActual() throws Exception {
    }

    IQuerySource<T> from(final Class... tables);

    IQuerySource<T> from(final ITableSchema... tables);

    IQuerySource<T> select(final Field... fields);

    IQuerySource<T> where(final WhereFunc.Where func);

    IQuerySource<T> where(final WhereFunc.Where... func);

    IQuerySource<T> orWhere(final WhereFunc.Where... func);

    IQuerySource<T> where(final WhereFunc func);

    IQuerySource<T> innerJoin(final Join func);

    IQuerySource<T> innerJoin(final Class aClass, final WhereFunc.Where... func);

    IQuerySource<T> leftJoin(final Join func);

    IQuerySource<T> leftJoin(final Class aClass, final WhereFunc.Where... func);

    IQuerySource<T> rightJoin(final Join func);

    IQuerySource<T> rightJoin(final Class aClass, final WhereFunc.Where... func);

    IQuerySource<T> groupBy(final Field... fields);

    IQuerySource<T> orderBy(final OrderBy... orders);

    IQuerySource<T> limit(final Long limit);

    IQuerySource<T> offset(final Long offset);

    T build();

    static <T> T requireNonNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}

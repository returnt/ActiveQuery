/**
 * File ActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 23.02.19 13:00
 */

package activequery;

import activequery.adapters.IQueryBuilder;
import activequery.conditions.*;
import activequery.operators.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class ActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3
 * Created by 23.02.19 13:00
 */
public class ActiveQuery<T extends IQueryBuilder> implements IQuerySource<T> {

    private static final Logger LOGGER = Logger.getLogger(ActiveQuery.class.getName());

    protected final ActiveQuery<T> mActiveQuery;
    protected final T mQueryBuilder;

    public ActiveQuery(final T queryBuilder) {
        mActiveQuery = this;
        mQueryBuilder = queryBuilder;
    }

    public ActiveQuery(final ActiveQuery<T> activeQuery) {
        mActiveQuery = activeQuery;
        mQueryBuilder = activeQuery.mQueryBuilder;
    }

    @Override
    public IQuerySource<T> from(final Class... tables) {
        IQuerySource.requireNonNull(tables, "tables is null");
        return new FromActiveQuery<>(this, tables);
    }

    @Override
    public IQuerySource<T> from(final ITableSchema... tables) {
        IQuerySource.requireNonNull(tables, "tables is null");
        return new FromSchemaActiveQuery<>(this, tables);
    }

    @Override
    public IQuerySource<T> select(final Field... fields) {
        IQuerySource.requireNonNull(fields, "fields is null");
        return new SelectActiveQuery<>(this, fields);
    }

    @Override
    public IQuerySource<T> where(final WhereFunc.Where func) {
        IQuerySource.requireNonNull(func, "supplier is null");
        return new WhereActiveQuery<>(this, func);
    }

    @Override
    public IQuerySource<T> where(final WhereFunc.Where... func) {
        IQuerySource.requireNonNull(func, "supplier is null");
        return new WhereAndActiveQuery<>(this, func);
    }

    @Override
    public IQuerySource<T> orWhere(final WhereFunc.Where... func) {
        IQuerySource.requireNonNull(func, "supplier is null");
        return new WhereOrActiveQuery<>(this, func);
    }

    @Override
    public IQuerySource<T> where(final WhereFunc func) {
        IQuerySource.requireNonNull(func, "supplier is null");
        return new WhereGroupActiveQuery<>(this, func);
    }

    @Override
    public IQuerySource<T> innerJoin(final Join func) {
        IQuerySource.requireNonNull(func, "supplier is null");
        return new InnerJoinActiveQuery<>(this, func);
    }

    @Override
    public IQuerySource<T> innerJoin(final Class aClass, final WhereFunc.Where... func) {
        IQuerySource.requireNonNull(aClass, "class is null");
        IQuerySource.requireNonNull(func, "supplier is null");
        return innerJoin(new Join() {
            @Override
            public Class table() {
                return aClass;
            }

            @Override
            public void on(WhereGroup whereGroup) {
                for (WhereFunc.Where where : func) {
                    whereGroup.and(where);
                }
            }
        });
    }

    @Override
    public IQuerySource<T> leftJoin(final Join func) {
        IQuerySource.requireNonNull(func, "supplier is null");
        return new LeftJoinActiveQuery<>(this, func);
    }

    @Override
    public IQuerySource<T> leftJoin(Class aClass, WhereFunc.Where... func) {
        IQuerySource.requireNonNull(aClass, "class is null");
        IQuerySource.requireNonNull(func, "supplier is null");
        return leftJoin(new Join() {
            @Override
            public Class table() {
                return aClass;
            }

            @Override
            public void on(WhereGroup whereGroup) {
                for (WhereFunc.Where where : func) {
                    whereGroup.and(where);
                }
            }
        });
    }

    @Override
    public IQuerySource<T> rightJoin(final Join func) {
        IQuerySource.requireNonNull(func, "supplier is null");
        return new RightJoinActiveQuery<>(this, func);
    }

    @Override
    public IQuerySource<T> rightJoin(Class aClass, WhereFunc.Where... func) {
        IQuerySource.requireNonNull(aClass, "class is null");
        IQuerySource.requireNonNull(func, "supplier is null");
        return rightJoin(new Join() {
            @Override
            public Class table() {
                return aClass;
            }

            @Override
            public void on(WhereGroup whereGroup) {
                for (WhereFunc.Where where : func) {
                    whereGroup.and(where);
                }
            }
        });
    }

    @Override
    public IQuerySource<T> groupBy(final Field... fields) {
        IQuerySource.requireNonNull(fields, "fields is null");
        return new GroupByActiveQuery<>(this, fields);
    }

    @Override
    public IQuerySource<T> orderBy(final OrderBy... orders) {
        IQuerySource.requireNonNull(orders, "orders is null");
        return new OrderByActiveQuery<>(this, orders);
    }

    @Override
    public IQuerySource<T> limit(final Long limit) {
        IQuerySource.requireNonNull(limit, "limit is null");
        return new LimitActiveQuery<>(this, limit);
    }

    @Override
    public IQuerySource<T> offset(final Long offset) {
        IQuerySource.requireNonNull(offset, "offset is null");
        return new OffsetActiveQuery<>(this, offset);
    }

    @Override
    public final T build() {
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

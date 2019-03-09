/**
 * File Model
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 23.02.19 23:50
 */

package activequery.dbexecutors.mysql;

import activequery.ActiveQuery;
import activequery.IQuerySource;
import activequery.adapters.MysqlQueryBuilder;
import activequery.conditions.*;

import java.util.List;

/**
 * Class Model
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.impl
 * Created by 23.02.19 23:50
 */
public abstract class Model implements IModel, ITableSchema {

    private IQuerySource<MysqlQueryBuilder> mActiveQuery;

    public Model() {
        mActiveQuery = new ActiveQuery<>(new MysqlQueryBuilder()).from(this);
    }

    @Override
    public Model from(Class... tables) {
        mActiveQuery = mActiveQuery.from(tables);
        return this;
    }

    @Override
    public Model from(ITableSchema... tables) {
        mActiveQuery = mActiveQuery.from(tables);
        return this;
    }

    @Override
    public Model select(Field... fields) {
        mActiveQuery = mActiveQuery.select(fields);
        return this;
    }

    @Override
    public Model where(WhereFunc.Where func) {
        mActiveQuery = mActiveQuery.where(func);
        return this;
    }

    @Override
    public Model where(WhereFunc.Where... func) {
        mActiveQuery = mActiveQuery.where(func);
        return this;
    }

    @Override
    public Model orWhere(WhereFunc.Where... func) {
        mActiveQuery = mActiveQuery.orWhere(func);
        return this;
    }

    @Override
    public Model where(WhereFunc func) {
        mActiveQuery = mActiveQuery.where(func);
        return this;
    }

    @Override
    public Model innerJoin(Join func) {
        mActiveQuery = mActiveQuery.innerJoin(func);
        return this;
    }

    @Override
    public Model innerJoin(final Class aClass, final WhereFunc.Where... func) {
        mActiveQuery = mActiveQuery.innerJoin(aClass, func);
        return this;
    }

    @Override
    public Model leftJoin(Join func) {
        mActiveQuery = mActiveQuery.leftJoin(func);
        return this;
    }

    @Override
    public Model leftJoin(Class aClass, WhereFunc.Where... func) {
        mActiveQuery = mActiveQuery.leftJoin(aClass, func);
        return this;
    }

    @Override
    public Model rightJoin(Join func) {
        mActiveQuery = mActiveQuery.rightJoin(func);
        return this;
    }

    @Override
    public Model rightJoin(Class aClass, WhereFunc.Where... func) {
        mActiveQuery = mActiveQuery.rightJoin(aClass, func);
        return this;
    }

    @Override
    public Model groupBy(Field... fields) {
        mActiveQuery = mActiveQuery.groupBy(fields);
        return this;
    }

    @Override
    public Model orderBy(OrderBy... orders) {
        mActiveQuery = mActiveQuery.orderBy(orders);
        return this;
    }

    @Override
    public Model limit(Long limit) {
        mActiveQuery = mActiveQuery.limit(limit);
        return this;
    }

    @Override
    public Model offset(Long offset) {
        mActiveQuery = mActiveQuery.offset(offset);
        return this;
    }

    @Override
    public MysqlQueryBuilder build() {
        return mActiveQuery.build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> List<R> get() {
        return get(getModelClass());
    }

    @Override
    public <R> List<R> get(final Class<R> rClass) {
        final DriverManager driverManager = new DriverManager(build());
        return driverManager.executeQueryGet(rClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> R first() {
        return (R) first(getModelClass());
    }

    @Override
    public <R> R first(final Class<R> rClass) {
        final DriverManager driverManager = new DriverManager(build());
        final List<R> rs = driverManager.executeQueryGet(rClass);
        return !rs.isEmpty() ? rs.get(0) : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer count() {
        return count(getModelClass());
    }

    @Override
    public <R> Integer count(final Class<R> rClass) {
        return get(rClass).size();
    }

}

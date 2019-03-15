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
import activequery.adapters.MysqlSaveQueryBuilder;
import activequery.annotations.PrimaryKey;
import activequery.conditions.*;
import activequery.operators.SimpleField;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class Model
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.impl
 * Created by 23.02.19 23:50
 */
public abstract class Model implements IModel, ITableSchema {

    private static final Logger LOGGER = Logger.getLogger(Model.class.getName());

    private DriverManager driverManager;
    private IQuerySource<MysqlQueryBuilder> mActiveQuery;
    private boolean isSelectOverride = false;

    public Model() {
        driverManager = new DriverManager();
        mActiveQuery = ActiveQuery.from(new MysqlQueryBuilder(), this);
    }

    public Model(final DriverManager driverManager, final IQuerySource<MysqlQueryBuilder> activeQuery) {
        this.driverManager = driverManager;
        mActiveQuery = activeQuery;
    }

    @Override
    public Model from(final Class... tables) {
        mActiveQuery = mActiveQuery.from(tables);
        return this;
    }

    @Override
    public Model from(final ITableSchema... tables) {
        mActiveQuery = mActiveQuery.from(tables);
        return this;
    }

    @Override
    public Model select(final Field... fields) {
        isSelectOverride = true;
        mActiveQuery = mActiveQuery.select(fields);
        return this;
    }

    @Override
    public Model selectAll(final Field... fields) {
        isSelectOverride = true;
        mActiveQuery = mActiveQuery.select(ALL()).select(fields);
        return this;
    }

    @Override
    public Model where(final WhereFunc.Where func) {
        mActiveQuery = mActiveQuery.where(func);
        return this;
    }

    @Override
    public Model where(final WhereFunc.Where... func) {
        mActiveQuery = mActiveQuery.where(func);
        return this;
    }

    @Override
    public Model orWhere(final WhereFunc.Where... func) {
        mActiveQuery = mActiveQuery.orWhere(func);
        return this;
    }

    @Override
    public Model where(final WhereFunc func) {
        mActiveQuery = mActiveQuery.where(func);
        return this;
    }

    @Override
    public Model innerJoin(final Join func) {
        mActiveQuery = mActiveQuery.innerJoin(func);
        return this;
    }

    @Override
    public Model innerJoin(final Class aClass, final WhereFunc.Where... func) {
        mActiveQuery = mActiveQuery.innerJoin(aClass, func);
        return this;
    }

    @Override
    public Model leftJoin(final Join func) {
        mActiveQuery = mActiveQuery.leftJoin(func);
        return this;
    }

    @Override
    public Model leftJoin(final Class aClass, final WhereFunc.Where... func) {
        mActiveQuery = mActiveQuery.leftJoin(aClass, func);
        return this;
    }

    @Override
    public Model rightJoin(final Join func) {
        mActiveQuery = mActiveQuery.rightJoin(func);
        return this;
    }

    @Override
    public Model rightJoin(final Class aClass, final WhereFunc.Where... func) {
        mActiveQuery = mActiveQuery.rightJoin(aClass, func);
        return this;
    }

    @Override
    public Model groupBy(final Field... fields) {
        mActiveQuery = mActiveQuery.groupBy(fields);
        return this;
    }

    @Override
    public Model orderBy(final OrderBy... orders) {
        mActiveQuery = mActiveQuery.orderBy(orders);
        return this;
    }

    @Override
    public Model limit(final Long limit) {
        mActiveQuery = mActiveQuery.limit(limit);
        return this;
    }

    @Override
    public Model offset(final Long offset) {
        mActiveQuery = mActiveQuery.offset(offset);
        return this;
    }

    @Override
    public MysqlQueryBuilder build() {
        if (!isSelectOverride) {
            mActiveQuery = mActiveQuery.select(ALL());
        }
        return mActiveQuery.build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> List<R> get() {
        return get(getModelClass());
    }

    @Override
    public <R> List<R> get(final Class<R> rClass) {
        return driverManager.executeQuery(build(), rClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> R first() {
        return (R) first(getModelClass());
    }

    @Override
    public <R> R first(final Class<R> rClass) {
        final List<R> rs = driverManager.executeQuery(build(), rClass);
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

    @SuppressWarnings("unchecked")
    @Override
    public <R> R save() {
        AbstractField fieldPrimary = null;
        Object primaryValue = null;
        final AbstractField[] fields = new AbstractField[getClass().getDeclaredFields().length];
        final Object[] fieldsValue = new Object[getClass().getDeclaredFields().length];
        final java.lang.reflect.Field[] fields1 = getClass().getDeclaredFields();
        for (int i = 0; i < fields1.length; i++) {
            // TODO: 10.03.19 add parse json property annotations
            fields[i] = new SimpleField(fields1[i].getName());
            fields1[i].setAccessible(true);
            try {
                final Object value = fields1[i].get(this);
                fieldsValue[i] = value;
            } catch (IllegalAccessException e) {
                LOGGER.log(Level.OFF, e.getMessage());
            }
            final PrimaryKey primaryKey = fields1[i].getDeclaredAnnotation(PrimaryKey.class);
            if (primaryKey != null) {
                fieldPrimary = new SimpleField(primaryKey.name());
                primaryValue = fieldsValue[i];
            }
        }
        // TODO: 10.03.19 add exception if  fieldPrimary or primaryValue null
        driverManager.executeUpdate(ActiveQuery.save(new MysqlSaveQueryBuilder(), getModelClass()).insert(fields).values(fieldsValue).build());
        return (fieldPrimary != null) ? (R) driverManager.executeQuery(ActiveQuery
            .from(new MysqlQueryBuilder(), getModelClass())
            .select(ALL())
            .where(fieldPrimary.eq(primaryValue)).build(), getModelClass()).get(0) : null;
    }
}

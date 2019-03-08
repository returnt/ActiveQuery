/**
 * File AbstractField
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 24.02.19 22:21
 */

package activequery.conditions;

import java.util.List;

/**
 * Class AbstractField
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.aggregations
 * Created by 24.02.19 22:21
 */
public abstract class AbstractField implements Field, WhereOperator {

    final Field mField;
    String as = null;

    public AbstractField(Field field) {
        mField = field;
    }

    @Override
    public String table() {
        return mField.table();
    }

    @Override
    public String field() {
        return mField.field();
    }

    @Override
    public String alias() {
        return this.as != null ? this.as : mField.alias();
    }

    @Override
    public Field as(String as) {
        this.as = as;
        return this;
    }

    @Override
    public WhereFunc.Where eq(final Object field) {
        return buildCondition(Condition.EQ, field);
    }

    @Override
    public WhereFunc.Where neq(final Object field) {
        return buildCondition(Condition.NEQ, field);
    }

    @Override
    public WhereFunc.Where gt(final Object field) {
        return buildCondition(Condition.GT, field);
    }

    @Override
    public WhereFunc.Where lt(final Object field) {
        return buildCondition(Condition.LT, field);
    }

    @Override
    public WhereFunc.Where gte(final Object field) {
        return buildCondition(Condition.GTE, field);
    }

    @Override
    public WhereFunc.Where lte(final Object field) {
        return buildCondition(Condition.LTE, field);
    }

    @Override
    public WhereFunc.Where between(final List<Object> field) {
        return buildCondition(Condition.BETWEEN, field);
    }

    @Override
    public WhereFunc.Where like(final Object field) {
        return buildCondition(Condition.LIKE, field);
    }

    @Override
    public WhereFunc.Where in(final List<Object> field) {
        return buildCondition(Condition.IN, field);
    }

    private WhereFunc.Where buildCondition(final Condition condition, final Object field) {
        return new WhereFunc.Where() {
            @Override
            public Field l() {
                return mField;
            }

            @Override
            public Condition operator() {
                return condition;
            }

            @Override
            public Object r() {
                return field;
            }
        };
    }
}

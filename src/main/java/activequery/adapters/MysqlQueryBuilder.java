/**
 * File MysqlQueryBuilder
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 23.02.19 13:14
 */

package activequery.adapters;

import activequery.aggregations.*;
import activequery.conditions.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static activequery.adapters.IQueryBuilder.quoteField;
import static activequery.adapters.IQueryBuilder.quoteName;

/**
 * Class MysqlQueryBuilder
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.adapters
 * Created by 23.02.19 13:14
 */
public class MysqlQueryBuilder implements IQueryBuilder.Select {

    private final List<String> mFrom;
    private final List<String> mSelect;
    private final List<String> mWhere;
    private final List<String> mJoin;
    private final List<String> mGroupBy;
    private final List<String> mOrderBy;
    private final List<Object> mQueryArgs;
    private Long mLimit;
    private Long mOffset;
    private final StringBuilder mQuery;

    public MysqlQueryBuilder() {
        mFrom = new ArrayList<>();
        mSelect = new ArrayList<>();
        mWhere = new ArrayList<>();
        mJoin = new ArrayList<>();
        mGroupBy = new ArrayList<>();
        mOrderBy = new ArrayList<>();
        mQueryArgs = new ArrayList<>();
        mLimit = null;
        mOffset = null;
        mQuery = new StringBuilder();
    }

    @Override
    public void applyFrom(final String table) {
        if (table != null && !table.isEmpty())
            mFrom.add(quoteName(table));
    }

    @Override
    public void applySelect(final Field select) {
        final String alias = !quoteName(select.alias()).isEmpty() ? " AS ".concat(quoteName(select.alias())) : "";
        mSelect.add(mapAggregation(select).concat(alias));
    }

    @Override
    public void applyWhere(final WhereGroup whereGroup) {
        calculateWhereGroup(mWhere, whereGroup);
    }

    @Override
    public void applyInnerJoin(final String table, final WhereGroup join) {
        mJoin.add(calculateJoin("INNER", table, join));
    }

    @Override
    public void applyLeftJoin(final String table, final WhereGroup join) {
        mJoin.add(calculateJoin("LEFT", table, join));
    }

    @Override
    public void applyRightJoin(final String table, final WhereGroup join) {
        mJoin.add(calculateJoin("RIGHT", table, join));
    }

    @Override
    public void applyGroupBy(final Field field) {
        mGroupBy.add(quoteField(field));
    }

    @Override
    public void applyOrderBy(final Field field, final OrderDirection direction) {
        mOrderBy.add(mapAggregation(field).concat(" ").concat(direction.name()));
    }

    @Override
    public void applyLimit(final Long limit) {
        mLimit = limit;
    }

    @Override
    public void applyOffset(Long offset) {
        mOffset = offset;
    }

    private String buildSelect() {
        return String.join(", ", mSelect);
    }

    private String buildFrom() {
        return String.join(", ", mFrom);
    }

    private String buildWhere() {
        return String.join(" ", mWhere);
    }

    private String buildInnerJoin() {
        return String.join(" ", mJoin);
    }

    private String buildGroupBy() {
        return String.join(", ", mGroupBy);
    }

    private String buildOrderBy() {
        return String.join(", ", mOrderBy);
    }

    @Override
    public void build() {
        if (!mSelect.isEmpty()) {
            mQuery.append("SELECT ").append(buildSelect());
        }
        if (!mFrom.isEmpty()) {
            mQuery.append(" ");
            mQuery.append("FROM ").append(buildFrom());
        }
        if (!mJoin.isEmpty()) {
            mQuery.append(" ");
            mQuery.append(buildInnerJoin());
        }
        if (!mWhere.isEmpty()) {
            mQuery.append(" ");
            mQuery.append("WHERE ").append(buildWhere());
        }
        if (!mGroupBy.isEmpty()) {
            mQuery.append(" ");
            mQuery.append("GROUP BY ").append(buildGroupBy());
        }
        if (!mOrderBy.isEmpty()) {
            mQuery.append(" ");
            mQuery.append("ORDER BY ").append(buildOrderBy());
        }
        if (mLimit != null) {
            mQuery.append(" ");
            mQuery.append("LIMIT ").append(mLimit);
        }
        if (mOffset != null) {
            mQuery.append(" ");
            mQuery.append("OFFSET ").append(mOffset);
        }
    }

    public Set<String> getResultFieldsName() {
        final Pattern p = Pattern.compile("[.+]?`(\\w+)`$");
        return mSelect.stream()
            .map(p::matcher)
            .filter(Matcher::find)
            .map(matcher -> matcher.group(1))
            .filter(s -> !s.equals("*"))
            .collect(Collectors.toSet());
    }

    public StringBuilder getQuery() {
        return mQuery;
    }

    public List<Object> getQueryArguments() {
        return mQueryArgs;
    }

    public MysqlQueryBuilder format() {
        final String newQuery = mQuery.toString()
            .replace("FROM", "\nFROM")
            .replace("WHERE", "\nWHERE");
        mQuery.delete(0, mQuery.length());
        mQuery.append(newQuery);
        return this;
    }

    private String mapCondition(final Condition condition) {
        switch (condition) {
            case EQ:
                return " = ";
            case NEQ:
                return " <> ";
            case GT:
                return " > ";
            case LT:
                return " < ";
            case GTE:
                return " >= ";
            case LTE:
                return " <= ";
            case BETWEEN:
                return " BETWEEN ";
            case LIKE:
                return " LIKE ";
            case IN:
                return " IN ";
            default:
                return "=";
        }
    }

    private String mapConditionR(final Condition condition, final Object r) {
        switch (condition) {
            case EQ:
                return mapQueryArgs(r);
            case NEQ:
                return mapQueryArgs(r);
            case GT:
                return mapQueryArgs(r);
            case LT:
                return mapQueryArgs(r);
            case GTE:
                return mapQueryArgs(r);
            case LTE:
                return mapQueryArgs(r);
            case BETWEEN:
                final List temp = (List) r;
                return mapQueryArgs(temp.get(0)).concat(" AND ").concat(mapQueryArgs(temp.get(1)));
            case LIKE:
                return mapQueryArgs(r);
            case IN:
                return "(".concat(joinCommaIfCollection(r)).concat(")");
            default:
                return mapQueryArgs(r);
        }
    }

    private String mapQueryArgs(final Object r) {
        mQueryArgs.add(r);
        return "?";
    }

    /**
     * joinCommaIfCollection
     * throw cast exception if value not collection
     *
     * @param object
     * @return String
     */
    private String joinCommaIfCollection(final Object object) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (Object o1 : (Collection) object) {
            if (!stringBuilder.toString().isEmpty()) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(mapQueryArgs(o1));
        }
        return stringBuilder.toString();
    }

    private String mapAggregation(final Field field) {
        final StringBuilder condition = new StringBuilder();
        if (field instanceof Count) {
            condition.append("COUNT(");
            condition.append(quoteField(field));
            condition.append(")");
        } else if (field instanceof Avg) {
            condition.append("AVG(");
            condition.append(quoteField(field));
            condition.append(")");
        } else if (field instanceof Max) {
            condition.append("MAX(");
            condition.append(quoteField(field));
            condition.append(")");
        } else if (field instanceof Min) {
            condition.append("MIN(");
            condition.append(quoteField(field));
            condition.append(")");
        } else if (field instanceof Sum) {
            condition.append("SUM(");
            condition.append(quoteField(field));
            condition.append(")");
        } else if (field instanceof Distinct) {
            condition.append("DISTINCT(");
            condition.append(quoteField(field));
            condition.append(")");
        } else if (field instanceof All) {
            condition.append(quoteName(field.table()).concat(".").concat(field.field()));
        } else {
            condition.append(quoteField(field));
        }
        return condition.toString();
    }

    private String calculateJoin(final String joinType, final String table, final WhereGroup join) {
        final List<String> condition = new ArrayList<>();
        calculateWhereGroup(condition, join);
        return joinType + " JOIN " +
            quoteName(table) +
            " ON " +
            String.join(" ", condition);
    }

    private void calculateWhereGroup(final List<String> condition, final WhereGroup whereGroup) {
        for (Object o : whereGroup.getObjects()) {
            if (o instanceof WhereGroup.And) {
                if (!condition.isEmpty()) {
                    condition.add("AND");
                }
            } else if (o instanceof WhereGroup.Not) {
                if (!condition.isEmpty()) {
                    condition.add("NOT");
                }
            } else if (o instanceof WhereGroup.Or) {
                if (!condition.isEmpty()) {
                    condition.add("OR");
                }
            } else if (o instanceof WhereFunc.Where) {
                condition.add(calculateWhere((WhereFunc.Where) o));
            } else if (o instanceof WhereGroup.GroupStart) {
                condition.add("(");
            } else if (o instanceof WhereGroup.GroupEnd) {
                condition.add(")");
            } else if (o instanceof WhereGroup) {
                applyWhere((WhereGroup) o);
            }
        }
    }

    private String calculateWhere(final WhereFunc.Where where) {
        final StringBuilder whereCondition = new StringBuilder();
        final Object l = where.l();
        final Object r = where.r();
        whereCondition.append(quoteField((Field) l));
        whereCondition.append(mapCondition(where.operator()));
        if (r instanceof Field) {
            whereCondition.append(quoteField((Field) r));
        } else {
            whereCondition.append(mapConditionR(where.operator(), where.r()));
        }
        return whereCondition.toString();
    }
}

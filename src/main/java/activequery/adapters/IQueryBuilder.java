/**
 * File IQueryBuilder
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 23.02.19 13:03
 */

package activequery.adapters;

import activequery.conditions.Field;
import activequery.conditions.OrderDirection;
import activequery.conditions.WhereGroup;

import java.util.List;
import java.util.Set;

/**
 * Interface IQueryBuilder
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.adapters
 * Created by 23.02.19 13:03
 */
public interface IQueryBuilder {

    void build();

    IQueryBuilder format();

    StringBuilder getQuery();

    List<Object> getQueryArguments();

    static String quoteName(final String name) {
        return name != null && !name.isEmpty() ? "`".concat(name).concat("`") : "";
    }

    static String quoteField(final Field field) {
        final String table = quoteName(field.table());
        return (!table.isEmpty() ? table.concat(".") : "").concat(quoteName(field.field()));
    }

    interface Save extends IQueryBuilder {

        void applyTable(final String name);

        void applyInsert(final Field fields);

        void applyValue(final Object[] values);
    }

    interface Select extends IQueryBuilder {

        void applyFrom(final String table);

        void applySelect(final Field select);

        void applyWhere(final WhereGroup whereGroup);

        void applyInnerJoin(final String table, final WhereGroup join);

        void applyLeftJoin(final String table, final WhereGroup join);

        void applyRightJoin(final String table, final WhereGroup join);

        void applyGroupBy(final Field field);

        void applyOrderBy(final Field field, final OrderDirection direction);

        void applyLimit(final Long limit);

        void applyOffset(final Long offset);

        Set<String> getResultFieldsName();
    }
}

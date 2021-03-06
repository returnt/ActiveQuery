/**
 * File MysqlQueryBuilder
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 23.02.19 13:14
 */

package activequery.adapters;

import activequery.conditions.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static activequery.adapters.IQueryBuilder.quoteName;

/**
 * Class MysqlQueryBuilder
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.adapters
 * Created by 23.02.19 13:14
 */
public class MysqlSaveQueryBuilder implements IQueryBuilder.Save {

    private String mTable;
    private final List<String> mFields;
    private final List<Object[]> mValue;
    private final StringBuilder mQuery;

    public MysqlSaveQueryBuilder() {
        mTable = "";
        mFields = new ArrayList<>();
        mValue = new ArrayList<>();
        mQuery = new StringBuilder();
    }

    @Override
    public void applyTable(final String name) {
        mTable = name;
    }

    @Override
    public void applyInsert(final Field field) {
        mFields.add(quoteName(field.field()));
    }

    @Override
    public void applyValue(final Object... value) {
        mValue.add(value);
    }

    private String buildFields() {
        return String.join(", ", mFields);
    }

    private String buildValues() {
        return mValue.stream().map(objects -> "(".concat(Arrays.stream(objects).map(o -> "?").collect(Collectors.joining(", ")).concat(")"))).collect(Collectors.joining(", "));
    }

    @Override
    public void build() {
        if (!mTable.isEmpty()) {
            mQuery.append("INSERT INTO ").append(mTable);
        }
        if (!mFields.isEmpty()) {
            mQuery.append(" (").append(buildFields()).append(")");
        }
        if (!mValue.isEmpty()) {
            mQuery.append(" VALUES ").append(buildValues());
        }
    }

    @Override
    public IQueryBuilder format() {
        return null;
    }

    @Override
    public StringBuilder getQuery() {
        return mQuery;
    }

    @Override
    public List<Object> getQueryArguments() {
        return mValue.stream().flatMap(Arrays::stream).collect(Collectors.toList());
    }
}

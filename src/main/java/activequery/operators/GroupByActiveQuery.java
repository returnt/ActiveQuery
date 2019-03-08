/**
 * File GroupByActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 03.03.19 21:06
 */

package activequery.operators;

import activequery.ActiveQuery;
import activequery.adapters.IQueryBuilder;
import activequery.conditions.Field;

/**
 * Class GroupByActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.operators
 * Created by 03.03.19 21:06
 */
public class GroupByActiveQuery<T extends IQueryBuilder> extends ActiveQuery<T> {

    private final Field[] mFields;

    public GroupByActiveQuery(final ActiveQuery<T> activeQuery, final Field... fields) {
        super(activeQuery);
        mFields = fields;
    }

    @Override
    public void subscribeActual() throws Exception {
        mActiveQuery.subscribeActual();
        for (Field field : mFields) {
            mQueryBuilder.applyGroupBy(field);
        }
    }
}
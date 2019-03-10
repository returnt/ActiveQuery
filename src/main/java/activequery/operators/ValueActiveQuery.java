/**
 * File ValueActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 10.03.19 12:17
 */

package activequery.operators;

import activequery.ActiveQuerySave;
import activequery.adapters.IQueryBuilder;

/**
 * Class ValueActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery.operators
 * Created by 10.03.19 12:17
 */
public class ValueActiveQuery<T extends IQueryBuilder.Save> extends ActiveQuerySave<T> {

    private final Object[] mValues;

    public ValueActiveQuery(final ActiveQuerySave<T> activeQuery, final Object... values) {
        super(activeQuery);
        mValues = values;
    }

    @Override
    public void subscribeActual() throws Exception {
        mActiveQuery.subscribeActual();
        for (Object o : mValues) {
            mQueryBuilder.applyValue(o);
        }
    }
}
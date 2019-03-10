/**
 * File SelectActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 23.02.19 18:08
 */

package activequery.operators;

import activequery.ActiveQuery;
import activequery.adapters.IQueryBuilder;
import activequery.conditions.Field;

/**
 * Class SelectActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.operators
 * Created by 23.02.19 18:08
 */
public class SelectActiveQuery<T extends IQueryBuilder.Select> extends ActiveQuery<T> {

    private final Field[] mFields;

    public SelectActiveQuery(final ActiveQuery<T> activeQuery, final Field[] fields) {
        super(activeQuery);
        mFields = fields;
    }

    @Override
    public void subscribeActual() throws Exception {
        mActiveQuery.subscribeActual();
        for (Field field : mFields) {
            mQueryBuilder.applySelect(field);
        }

    }
}
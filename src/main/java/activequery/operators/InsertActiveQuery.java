/**
 * File InsertActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 10.03.19 0:12
 */

package activequery.operators;

import activequery.ActiveQuerySave;
import activequery.adapters.IQueryBuilder;
import activequery.conditions.Field;

/**
 * Class InsertActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery.operators
 * Created by 10.03.19 0:12
 */
public class InsertActiveQuery<T extends IQueryBuilder.Save> extends ActiveQuerySave<T> {

    private final Field[] mFields;

    public InsertActiveQuery(final ActiveQuerySave<T> activeQuery, final Field... fields) {
        super(activeQuery);
        mFields = fields;
    }

    @Override
    public void subscribeActual() throws Exception {
        mActiveQuery.subscribeActual();
        for (Field field : mFields) {
            mQueryBuilder.applyInsert(field);
        }
    }
}

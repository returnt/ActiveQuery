/**
 * File OffsetActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 03.03.19 22:57
 */

package activequery.operators;

import activequery.ActiveQuery;
import activequery.adapters.IQueryBuilder;

/**
 * Class OffsetActiveQuery
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.operators
 * Created by 03.03.19 22:57
 */
public class OffsetActiveQuery<T extends IQueryBuilder.Select> extends ActiveQuery<T> {

    private final Long mOffset;

    public OffsetActiveQuery(final ActiveQuery<T> activeQuery, final Long offset) {
        super(activeQuery);
        mOffset = offset;
    }

    @Override
    public void subscribeActual() throws Exception {
        mActiveQuery.subscribeActual();
        mQueryBuilder.applyOffset(mOffset);
    }
}
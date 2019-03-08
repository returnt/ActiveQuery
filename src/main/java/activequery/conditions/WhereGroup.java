/**
 * File WhereGroup
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 23.02.19 22:41
 */

package activequery.conditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Class WhereGroup
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.conditions
 * Created by 23.02.19 22:41
 */
public final class WhereGroup {

    private final List<Object> mObjects;

    public WhereGroup() {
        mObjects = new ArrayList<>();
    }

    public WhereGroup add(final Object obj) {
        mObjects.add(obj);
        return this;
    }

    public WhereGroup and(final Object obj) {
        mObjects.add(new And());
        mObjects.add(obj);
        return this;
    }

    public WhereGroup andNot(final Object obj) {
        mObjects.add(new And());
        mObjects.add(new Not());
        mObjects.add(obj);
        return this;
    }

    public WhereGroup or(final Object obj) {
        mObjects.add(new Or());
        mObjects.add(obj);
        return this;
    }

    public WhereGroup and(final WhereGroup obj) {
        mObjects.add(new And());
        mObjects.add(new GroupStart());
        mObjects.add(obj);
        mObjects.add(new GroupEnd());
        return this;
    }

    public WhereGroup or(final WhereGroup obj) {
        mObjects.add(new Or());
        mObjects.add(new GroupStart());
        mObjects.add(obj);
        mObjects.add(new GroupEnd());
        return this;
    }

    public class Not {
    }

    public class And {
    }

    public class Or {
    }

    public class GroupStart {
    }

    public class GroupEnd {
    }

    public List<Object> getObjects() {
        return mObjects;
    }
}

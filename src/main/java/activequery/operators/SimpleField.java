/**
 * File SimpleField
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 24.02.19 22:43
 */

package activequery.operators;

import activequery.conditions.AbstractField;
import activequery.conditions.Field;

/**
 * Class SimpleField
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.operators
 * Created by 24.02.19 22:43
 */
public class SimpleField extends AbstractField {

    public SimpleField(final String field) {
        this(null, field);
    }

    public SimpleField(final String table, final String field) {
        this(table, field, null);
    }

    public SimpleField(final String table, final String field, final String alias) {
        super(new Field() {
            @Override
            public String table() {
                return table;
            }

            @Override
            public String field() {
                return field;
            }

            @Override
            public String alias() {
                return alias;
            }
        });
    }
}

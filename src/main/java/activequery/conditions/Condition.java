/**
 * File Condition
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 24.02.19 22:52
 */

package activequery.conditions;

/**
 * Enum Condition
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.conditions
 * Created by 24.02.19 22:52
 */
public enum Condition {

    /**
     * Equal (=)
     */
    EQ,
    /**
     * Not equal. Note: In some versions of SQL this operator may be written as != (<>)
     */
    NEQ,
    /**
     * Greater than (>)
     */
    GT,
    /**
     * Less than (<)
     */
    LT,
    /**
     * Greater than or equal (>=)
     */
    GTE,
    /**
     * Less than or equal (<=)
     */
    LTE,
    /**
     * Between a certain range
     */
    BETWEEN,
    /**
     * Search for a pattern
     */
    LIKE,
    /**
     * To specify multiple possible values for a column
     */
    IN
}
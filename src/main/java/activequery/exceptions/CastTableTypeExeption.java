/**
 * File CastTableTypeExeption
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 03.03.19 19:15
 */

package activequery.exceptions;

/**
 * Class CastTableTypeExeption
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.exceptions
 * Created by 03.03.19 19:15
 */
public class CastTableTypeExeption extends RuntimeException {

    public CastTableTypeExeption(final String className) {
        super("Not cast class: " + className + " to ITableSchema");
    }
}

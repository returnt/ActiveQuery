/**
 * File DriverManagerException
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 06.03.19 23:05
 */

package activequery.exceptions;

/**
 * Class DriverManagerException
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.exceptions
 * Created by 06.03.19 23:05
 */
public class DriverManagerException extends Exception {

    public DriverManagerException(final Class aClass, final String message) {
        super("Driver manager exception, class: " + aClass.getName() + ", message: " + message);
    }
}

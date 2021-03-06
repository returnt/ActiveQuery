/**
 * File IModel
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 06.03.19 23:54
 */

package activequery.dbexecutors.mysql;

import activequery.IQuerySource;
import activequery.adapters.MysqlQueryBuilder;
import activequery.conditions.Field;

import java.util.List;

/**
 * Interface IModel
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Package activequery3.dbexecutors.mysql
 * Created by 06.03.19 23:54
 */
public interface IModel extends IQuerySource<MysqlQueryBuilder> {

    Model selectAll(final Field... fields);

    <R> List<R> get();

    <R> List<R> get(final Class<R> rClass);

    <R> R first();

    <R> R first(final Class<R> rClass);

    Integer count();

    <R> Integer count(final Class<R> rClass);

    <R> R save();
}

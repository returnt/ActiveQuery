package activequery.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * File PrimaryKey
 *
 * Author d.a.ganzha
 * Project TestDbDriver
 * Created by 10.03.19 19:43
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {

    String name() default "id";
}

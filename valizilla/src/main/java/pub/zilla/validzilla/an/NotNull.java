package pub.zilla.validzilla.an;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Not null annotation
 * Created by zilla on 9/15/17.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {
    /**
     * validate order
     *
     * @return
     */
//    int value();

    /**
     * Error message
     *
     * @return
     */
    int error();
}

package pub.zilla.vali.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Not null annotation
 * Created by zilla on 9/15/17.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface NotNull {
    /**
     * validate order
     *
     * @return
     */
    int order();

    /**
     * Error message
     *
     * @return
     */
    int error();
}

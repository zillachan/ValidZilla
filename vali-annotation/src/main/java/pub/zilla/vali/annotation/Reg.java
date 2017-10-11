package pub.zilla.vali.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Reg validation
 * Created by zilla on 9/15/17.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface Reg {
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

    /**
     * reg
     *
     * @return
     */
    String reg();
}

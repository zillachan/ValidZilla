package pub.zilla.vali.api;

import android.app.Activity;

/**
 * Created by zilla on 10/11/17.
 */

public class ValiZilla {

    private static final String SUBFix = "Vali";

    public static void validate(Activity activity) {
        validate(activity, activity);
    }

    public static void validate(Object target, Object root) {
        Class<?> c = target.getClass();
        String proxyClassName = c.getName() + "$$" + SUBFix;
        try {
            Class<?> proxyClass = Class.forName(proxyClassName);
            Validator validator = (Validator) proxyClass.newInstance();
            validator.validate(target, root);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * inject interface
     *
     * @param <T>
     */
    public interface Validator<T> {
        void validate(T target, Object object);
    }
}

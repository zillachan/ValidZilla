package pub.zilla.validzilla.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by zilla on 10/12/17.
 */

public class MethodModel {
    private Method method;
    private Annotation annotation;

    public MethodModel() {

    }

    public MethodModel(Method method, Annotation annotation) {
        this.method = method;
        this.annotation = annotation;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    @Override
    public String toString() {
        return "MethodModel{" +
                "method=" + method +
                ", annotation=" + annotation +
                '}';
    }
}

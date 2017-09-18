package pub.zilla.validzilla.model;

import java.lang.reflect.Field;

/**
 * Validate field model
 * Created by zilla on 9/15/17.
 */

public class ValiModel {
    private int order;
    private int error;
    private String reg;

    private Field field;


    public ValiModel() {

    }

    public ValiModel(Field field) {
        this.field = field;
    }

    /**
     * constructor
     * @param order
     * @param error
     * @param reg
     * @param field
     */
    public ValiModel(int order, int error, String reg, Field field) {
        this.order = order;
        this.error = error;
        this.reg = reg;
        this.field = field;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "ValiModel{" +
                "order=" + order +
                ", error=" + error +
                ", reg='" + reg + '\'' +
                ", field=" + field +
                '}';
    }
}

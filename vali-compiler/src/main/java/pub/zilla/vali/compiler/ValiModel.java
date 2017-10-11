package pub.zilla.vali.compiler;

import javax.lang.model.element.Element;

/**
 * Validate element model
 * Created by zilla on 9/15/17.
 */

public class ValiModel {
    private int order;
    private int error;
    private String reg;

    private Element element;


    public ValiModel() {

    }

    public ValiModel(Element element) {
        this.element = element;
    }

    /**
     * constructor
     *
     * @param order
     * @param error
     * @param reg
     * @param element
     */
    public ValiModel(int order, int error, String reg, Element element) {
        this.order = order;
        this.error = error;
        this.reg = reg;
        this.element = element;
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

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    @Override
    public String toString() {
        return "ValiModel{" +
                "order=" + order +
                ", error=" + error +
                ", reg='" + reg + '\'' +
                ", element=" + element +
                '}';
    }
}

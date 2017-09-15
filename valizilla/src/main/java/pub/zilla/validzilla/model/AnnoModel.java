package pub.zilla.validzilla.model;

/**
 * validate annotation model
 * Created by zilla on 9/15/17.
 */

public class AnnoModel {
    private int error;
    private String reg;

    public AnnoModel() {

    }

    public AnnoModel(int error, String reg) {
        this.error = error;
        this.reg = reg;
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

    @Override
    public String toString() {
        return "AnnoModel{" +
                ", error=" + error +
                ", reg='" + reg + '\'' +
                '}';
    }
}

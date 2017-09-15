package pub.zilla.validzilla.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Validate field model
 * Created by zilla on 9/15/17.
 */

public class ValiModel {
    private Field field;
    private List<AnnoModel> annoModels;

    public ValiModel() {

    }

    public ValiModel(Field field) {
        this.field = field;
    }

    public ValiModel(Field field, List<AnnoModel> annoModels) {
        this.field = field;
        this.annoModels = annoModels;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public List<AnnoModel> getAnnoModels() {
        return annoModels;
    }

    public void setAnnoModels(List<AnnoModel> annoModels) {
        this.annoModels = annoModels;
    }

    public void addAnnoModel(AnnoModel annoModel) {
        if (annoModels == null) {
            annoModels = new ArrayList<>();
        }
        annoModels.add(annoModel);
    }


    @Override
    public String toString() {
        return "ValiModel{" +
                "field=" + field +
                ", annoModels=" + annoModels +
                '}';
    }
}

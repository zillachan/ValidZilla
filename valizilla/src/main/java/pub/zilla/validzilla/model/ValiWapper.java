package pub.zilla.validzilla.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zilla on 9/15/17.
 */

public class ValiWapper {
    private List<ValiModel> valiModels;
    private List<MethodModel> methods;

    public ValiWapper() {

    }

    public List<ValiModel> getValiModel() {
        return valiModels;
    }

    public void setValiModel(List<ValiModel> valiModel) {
        this.valiModels = valiModel;
    }

    public void addMethod(MethodModel method) {
        if (methods == null) {
            methods = new ArrayList<>();
        }
        methods.add(method);
    }

    public List<MethodModel> getMethods() {
        return methods;
    }

    public void addValiModel(ValiModel model) {
        if (valiModels == null) {
            valiModels = new ArrayList<>();
        }
        valiModels.add(model);
    }

    @Override
    public String toString() {
        return "ValiWapper{" +
                "valiModels=" + valiModels +
                ", methods=" + methods +
                '}';
    }
}

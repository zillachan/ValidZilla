package pub.zilla.validzilla.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zilla on 9/15/17.
 */

public class ValiWapper {
    private List<ValiModel> valiModels;
    private Method method;

    public ValiWapper() {

    }

    public List<ValiModel> getValiModel() {
        return valiModels;
    }

    public void setValiModel(List<ValiModel> valiModel) {
        this.valiModels = valiModel;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
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
                ", method=" + method +
                '}';
    }
}

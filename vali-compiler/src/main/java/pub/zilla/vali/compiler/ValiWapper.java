package pub.zilla.vali.compiler;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;

/**
 * Created by zilla on 9/15/17.
 */

public class ValiWapper {
    private List<ValiModel> valiModels;
    private List<Element> methods;

    public ValiWapper() {

    }

    public void addValiModel(ValiModel model) {
        if (valiModels == null) {
            valiModels = new ArrayList<>();
        }
        valiModels.add(model);
    }

    public void addMethod(Element method) {
        if (methods == null) {
            methods = new ArrayList<>();
        }
        methods.add(method);
    }

    public List<ValiModel> getValiModels() {
        if (valiModels == null) valiModels = new ArrayList<>();
        return valiModels;
    }

    public List<Element> getMethods() {
        if (methods == null) methods = new ArrayList<>();
        return methods;
    }

    @Override
    public String toString() {
        return "ValiWapper{" +
                "valiModels=" + valiModels +
                ", methods=" + methods +
                '}';
    }
}

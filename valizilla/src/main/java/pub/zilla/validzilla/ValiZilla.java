package pub.zilla.validzilla;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import pub.zilla.validzilla.an.NotNull;
import pub.zilla.validzilla.an.Reg;
import pub.zilla.validzilla.an.ValiSuccess;
import pub.zilla.validzilla.model.ValiModel;
import pub.zilla.validzilla.model.ValiWapper;

/**
 * Valid
 * Created by zilla on 9/15/17.
 */

public class ValiZilla {

    public static void vali(Object target) {
        ValiWapper wapper = getFieldsFromCache(target.getClass());
        if (wapper.getValiModel() == null) return;
        Class textInputLayout = null;
        try {
            textInputLayout = Class.forName("android.support.design.widget.TextInputLayout");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (textInputLayout == null) return;
        List<ValiModel> models = wapper.getValiModel();
        Collections.sort(models, new Comparator<ValiModel>() {
            @Override
            public int compare(ValiModel o1, ValiModel o2) {
                return o1.getOrder() - o2.getOrder();
            }
        });
        for (ValiModel model : models) {
            try {
                Class fieldType = model.getField().getType();
                if (textInputLayout.isAssignableFrom(fieldType)) {//if is TextInputLayout or extends from TextInputLayout.
                    TextInputLayout targetField = (TextInputLayout) model.getField().get(target);
                    String result = targetField.getEditText().getText().toString();//result
                    if (TextUtils.isEmpty(model.getReg())) {//not null check fail
                        if (TextUtils.isEmpty(result)) {
                            targetField.setError(targetField.getContext().getString(model.getError()));
                            return;
                        } else {
                            targetField.setError("");
                        }
                    } else if (!result.matches(model.getReg())) {// reg check fail;
                        targetField.setError(targetField.getContext().getString(model.getError()));
                        return;
                    } else {
                        targetField.setError("");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (wapper.getMethod() != null) {
            try {
                wapper.getMethod().invoke(target);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * un inject
     *
     * @param target
     */
    public static void unJect(Object target) {
        cache.remove(target.getClass());
    }

    /**
     * reflect cache
     */
    private static HashMap<Class, ValiWapper> cache = new HashMap<>();

    private static ValiWapper getFieldsFromCache(Class c) {
        ValiWapper wapper = cache.get(c);
        if (wapper == null) {
            wapper = new ValiWapper();
            //Field
            Field[] cFields = c.getFields();
            NotNull notNull;
            Reg reg;
            for (Field field : cFields) {
                notNull = field.getAnnotation(NotNull.class);
                reg = field.getAnnotation(Reg.class);
                if (notNull != null || reg != null) {
                    if (notNull != null) {
                        ValiModel valiModel = new ValiModel(notNull.value(), notNull.error(), null, field);
                        wapper.addValiModel(valiModel);
                    }
                    if (reg != null) {
                        ValiModel valiModel = new ValiModel(reg.value(), reg.error(), reg.reg(), field);
                        wapper.addValiModel(valiModel);
                    }
                }
            }
            //Method
            Method[] methods = c.getMethods();
            for (Method method : methods) {
                ValiSuccess valiSuccess = method.getAnnotation(ValiSuccess.class);
                if (valiSuccess != null) {
                    wapper.setMethod(method);
                }
            }
            cache.put(c, wapper);
        }
        return wapper;
    }
}

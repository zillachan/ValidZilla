package pub.zilla.validzilla;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import pub.zilla.validzilla.an.NotNull;
import pub.zilla.validzilla.an.Reg;
import pub.zilla.validzilla.an.ValiFailed;
import pub.zilla.validzilla.an.ValiSuccess;
import pub.zilla.validzilla.model.MethodModel;
import pub.zilla.validzilla.model.ValiModel;
import pub.zilla.validzilla.model.ValiWapper;

/**
 * Valid
 * Created by zilla on 9/15/17.
 */

public class ValiZilla {

    public static void vali(Object target) {
        vali(target, null);
    }

    public static void vali(Object target, int... orders) {

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
            if ((orders != null && contains(orders, model.getOrder())) || orders == null) {
                try {
                    Class fieldType = model.getField().getType();
                    if (textInputLayout.isAssignableFrom(fieldType)) {//if is TextInputLayout or extends from TextInputLayout.
                        Field field = model.getField();
                        field.setAccessible(true);
                        TextInputLayout targetField = (TextInputLayout) model.getField().get(target);
                        String result = targetField.getEditText().getText().toString();//result
                        if (TextUtils.isEmpty(model.getReg())) {//not null check fail
                            if (TextUtils.isEmpty(result)) {
                                targetField.setError(targetField.getContext().getString(model.getError()));
                                invokeSuccess(target, ValiFailed.class, orders);
                                return;
                            } else {
                                targetField.setError("");
                            }
                        } else if (!result.matches(model.getReg())) {// reg check fail;
                            targetField.setError(targetField.getContext().getString(model.getError()));
                            invokeSuccess(target, ValiFailed.class, orders);
                            return;
                        } else {
                            targetField.setError("");
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        invokeSuccess(target, ValiSuccess.class, orders);
    }

    /**
     * validate callback.
     *
     * @param target
     * @param orders
     */
    private static void invokeSuccess(Object target, Class annotationClass, int... orders) {
        List<MethodModel> methodModels = getFieldsFromCache(target.getClass()).getMethods();
        if (methodModels != null) {
            try {
                for (MethodModel methodModel : methodModels) {
                    if (methodModel.getAnnotation().annotationType().equals(annotationClass)) {
                        Class<?>[] parameterTypes = methodModel.getMethod().getParameterTypes();
                        if (parameterTypes.length == 0) {
                            methodModel.getMethod().invoke(target);
                        } else {
                            methodModel.getMethod().invoke(target, orders);
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean contains(int[] array, int value) {
        for (int i : array) {
            if (value == i) return true;
        }
        return false;
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
            Field[] cFields = c.getDeclaredFields();
            NotNull notNull;
            Reg reg;
            for (Field field : cFields) {
                field.setAccessible(true);
                notNull = field.getAnnotation(NotNull.class);
                reg = field.getAnnotation(Reg.class);
                if (notNull != null || reg != null) {
                    if (notNull != null) {
                        ValiModel valiModel = new ValiModel(notNull.order(), notNull.error(), null, field);
                        wapper.addValiModel(valiModel);
                    }
                    if (reg != null) {
                        ValiModel valiModel = new ValiModel(reg.order(), reg.error(), reg.reg(), field);
                        wapper.addValiModel(valiModel);
                    }
                }
            }
            //Method
            Method[] methods = c.getDeclaredMethods();
            for (Method method : methods) {
                ValiSuccess valiSuccess = method.getAnnotation(ValiSuccess.class);
                ValiFailed valiFailed = method.getAnnotation(ValiFailed.class);
                if (valiSuccess != null) {
                    MethodModel methodModel = new MethodModel(method, valiSuccess);
                    wapper.addMethod(methodModel);
                }
                if (valiFailed != null) {
                    MethodModel methodModel = new MethodModel(method, valiFailed);
                    wapper.addMethod(methodModel);
                }
            }
            cache.put(c, wapper);
        }
        return wapper;
    }
}

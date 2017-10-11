package pub.zilla.vali.compiler;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import pub.zilla.vali.annotation.NotNull;
import pub.zilla.vali.annotation.Reg;
import pub.zilla.vali.annotation.ValiFailed;
import pub.zilla.vali.annotation.ValiSuccess;

/**
 * process the annotations.
 */
@AutoService(Processor.class)
public class ValiProcessor extends AbstractProcessor {
    /**
     * file helper
     */
    private Filer mFileUtils;
    /**
     * element helper
     */
    private Elements mElementUtils;
    /**
     * log/message healper
     */
    private Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFileUtils = processingEnvironment.getFiler();
        mElementUtils = processingEnvironment.getElementUtils();
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<>();
        annotationTypes.add(NotNull.class.getCanonicalName());
        annotationTypes.add(Reg.class.getCanonicalName());
        annotationTypes.add(ValiFailed.class.getCanonicalName());
        annotationTypes.add(ValiSuccess.class.getCanonicalName());
        return annotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }

    private Map<String, ProxyInfo> proxyInfoMap = new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "ValiProcessor.process()...");
        proxyInfoMap.clear();

        //1.collect info.
        valiField(roundEnvironment, NotNull.class);
        valiField(roundEnvironment, Reg.class);
        valiMethod(roundEnvironment, ValiFailed.class);
        valiMethod(roundEnvironment, ValiSuccess.class);

        //2.generate code.
        for (String className : proxyInfoMap.keySet()) {
            ProxyInfo proxyInfo = proxyInfoMap.get(className);
            try {
                proxyInfo.generateCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    private boolean valiField(RoundEnvironment roundEnvironment, Class c) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(c);
        for (Element element : elements) {
            if (!check(element, c, ElementKind.FIELD)) return false;
            //Class
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            //class name
            String className = typeElement.getQualifiedName().toString();
            ProxyInfo proxyInfo = proxyInfoMap.get(className);
            if (proxyInfo == null) {
                proxyInfo = new ProxyInfo(mElementUtils, typeElement);
                proxyInfoMap.put(className, proxyInfo);
            }
            if (c == NotNull.class) {
                NotNull notNull = element.getAnnotation(NotNull.class);
                int order = notNull.order();
                int error = notNull.error();
                ValiModel valiModel = new ValiModel(order, error, "", element);
                proxyInfo.getValiWapper().addValiModel(valiModel);
            } else if (c == Reg.class) {
                Reg reg = element.getAnnotation(Reg.class);
                int order = reg.order();
                int error = reg.error();
                String reginfo = reg.reg();
                ValiModel valiModel = new ValiModel(order, error, reginfo, element);
                proxyInfo.getValiWapper().addValiModel(valiModel);
            } else {
                mMessager.printMessage(Diagnostic.Kind.ERROR, c.getSimpleName() + " annotation is not supported.");
            }
        }
        return true;
    }

    private boolean valiMethod(RoundEnvironment roundEnvironment, Class c) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(c);
        for (Element element : elements) {
            if (!check(element, c, ElementKind.METHOD)) return false;
            //Class
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            //class name
            String className = typeElement.getQualifiedName().toString();
            ProxyInfo proxyInfo = proxyInfoMap.get(className);
            if (proxyInfo == null) {
                proxyInfo = new ProxyInfo(mElementUtils, typeElement);
                proxyInfoMap.put(className, proxyInfo);
            }
            if (c == ValiSuccess.class) {
                ValiSuccess valiSuccess = element.getAnnotation(ValiSuccess.class);
                proxyInfo.getValiWapper().addMethod(element);
            } else if (c == ValiFailed.class) {
                ValiFailed valiFailed = element.getAnnotation(ValiFailed.class);
                proxyInfo.getValiWapper().addMethod(element);
            } else {
                mMessager.printMessage(Diagnostic.Kind.ERROR, c.getSimpleName() + " annotation is not supported.");
            }
        }
        return true;
    }

    private boolean check(Element annotatedElement, Class clazz, ElementKind kind) {
        if (annotatedElement.getKind() != kind) {
            mMessager.printMessage(Diagnostic.Kind.ERROR, annotatedElement + " must be the kind of " + kind + "@see(javax.lang.model.element.ElementKind) in class " + clazz.getSimpleName());
            return false;
        }
        if (annotatedElement.getModifiers().contains(Modifier.PRIVATE)) {
            mMessager.printMessage(Diagnostic.Kind.ERROR, annotatedElement + " can not be modified by private. in class " + clazz.getSimpleName());
            return false;
        }
        return true;
    }
}

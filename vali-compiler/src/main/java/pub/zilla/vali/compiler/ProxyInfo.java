package pub.zilla.vali.compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * proxy info.
 * <br>
 * each class will have a proxy class.
 * <br>
 * Created by zilla on 10/11/17.
 */

public class ProxyInfo {

    /**
     * packageName
     */
    private String packageName;
    /**
     * proxy class name
     */
    private String proxyClassName;
    /**
     * class
     */
    private TypeElement typeElement;
    /**
     * field & method that are annotated
     */
    private ValiWapper valiWapper = new ValiWapper();

    private static final String SUBFix = "Vali";

    public ProxyInfo() {

    }

    public ProxyInfo(Elements elements, TypeElement typeElement) {
        this.typeElement = typeElement;
        this.packageName = elements.getPackageOf(typeElement).getQualifiedName().toString();
        this.proxyClassName = typeElement.getSimpleName() + "$$" + SUBFix;
    }

    public ValiWapper getValiWapper() {
        return valiWapper;
    }

    public void setValiWapper(ValiWapper valiWapper) {
        this.valiWapper = valiWapper;
    }

    public void generateCode() throws IOException {
        generateHelloworld();
    }

    private void generateHelloworld() throws IOException {
        MethodSpec main = MethodSpec.methodBuilder("main").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello World").build();
        TypeSpec typeSpec = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC).addMethod(main).build();
        JavaFile javaFile = JavaFile.builder("com.example.helloworld", typeSpec).build();
        javaFile.writeTo(System.out);
    }
}

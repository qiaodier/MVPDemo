package com.mvp.compile;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by iqiao on 2020-02-23 23:12
 * Desc: 自定义注解处理器
 *
 * @author iqiao
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.mvp.compile.Route"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class RouterProcessor extends AbstractProcessor {
    Filer mFiler;
    Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
        mMessager.printMessage(Diagnostic.Kind.NOTE, "Router: init...");
    }

    /**
     * class --> TypeElement
     * method --> ExecutableElement
     * filed  --> VariableElement
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //get printer
        try {
            Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Route.class);
            mMessager.printMessage(Diagnostic.Kind.NOTE, "Router: Found routes, start... " + elementsAnnotatedWith.size());
            for (Element element : elementsAnnotatedWith) {
                //get this class packageName
                String packageName = processingEnv.getElementUtils().getPackageOf(element).toString();
                //get root packageName
                String rootPackageName = processingEnv.getElementUtils().getPackageElement("com.mvp.cn").toString();
                String value = element.getAnnotation(Route.class).value();
                String content = "package "+rootPackageName+".register;\n" +
                        "\n" +
                        "import "+packageName+"." + element.getSimpleName() + ";\n" +
                        "import "+rootPackageName+".router.IRouterListener;\n" +
                        "\n" +
                        "import java.util.Map;\n" +
                        "\n" +
                        "/**\n" +
                        " * Created by iqiao on 2020-02-24 00:15\n" +
                        " * Desc:\n" +
                        " */\n" +
                        "public class " + element.getSimpleName() + "RouterImp implements IRouterListener {\n" +
                        "    @Override\n" +
                        "    public void register(Map<String, Class<?>> routerMap) {\n" +
                        "        routerMap.put(\"" + value + "\", " + element.getSimpleName() + ".class);\n" +
                        "    }\n" +
                        "}";
                mMessager.printMessage(Diagnostic.Kind.NOTE, "Router: " + element.getSimpleName());
                try {
                    JavaFileObject javaFileObject = mFiler.createSourceFile("com.mvp.cn.register." + element.getSimpleName() + "RouterImp");
                    Writer writer = javaFileObject.openWriter();
//                    writer.write(createJavaFile(element.getSimpleName().toString(), value));
                    writer.write(content);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    mMessager.printMessage(Diagnostic.Kind.ERROR, "Router: writeError :" + e.getMessage());
                } finally {

                }
                mMessager.printMessage(Diagnostic.Kind.NOTE, "Router: write  success");
            }
        } catch (Exception e) {
            mMessager.printMessage(Diagnostic.Kind.ERROR, "Router: " + e.getMessage());
        }
        return true;
    }


//    private String createJavaFile(String className, String routerKey) {
//        // build class
//        ClassName superClassName = ClassName.bestGuess("com.mvp.cn.router.IRouterListener");
//        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className + "RouterImp")
//                .addModifiers(Modifier.PUBLIC)
//                .addSuperinterface(superClassName);
//        //build method
//        ClassName override = ClassName.get("java.lang", "Override");
//        MethodSpec register = MethodSpec.methodBuilder("register")
//                .addAnnotation(override)
//                .returns(void.class)
//                .addModifiers(Modifier.PUBLIC)
//                .addParameter(Map.class, "routerMap")
//                .addStatement("routerMap.put(\"" + routerKey + "\"," + className + ".class)").build();
//        // build class
//        TypeSpec impClass = classBuilder
//                .addMethod(register)
//                .build();
//        return impClass.toString();
//    }


}

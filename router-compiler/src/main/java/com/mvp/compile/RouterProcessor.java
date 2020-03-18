package com.mvp.compile;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
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
import javax.lang.model.element.Modifier;
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
//                String rootPackageName = processingEnv.getElementUtils().getPackageElement("com.compile.router").toString();
                String value = element.getAnnotation(Route.class).value();
//                String content = "package com.compile.router;\n" +
//                        "\n" +
//                        "import " + packageName + "." + element.getSimpleName() + ";\n" +
//                        "import com.mvp.compile.IRouterListener;\n" +
//                        "\n" +
//                        "import java.util.Map;\n" +
//                        "\n" +
//                        "/**\n" +
//                        " * Created by iqiao on 2020-02-24 00:15\n" +
//                        " * Desc:\n" +
//                        " */\n" +
//                        "public class " + element.getSimpleName() + "RouterImp implements IRouterListener {\n" +
//                        "    @Override\n" +
//                        "    public void register(Map<String, Class<?>> routerMap) {\n" +
//                        "        routerMap.put(\"" + value + "\", " + element.getSimpleName() + ".class);\n" +
//                        "    }\n" +
//                        "}";
                mMessager.printMessage(Diagnostic.Kind.NOTE, "Router: " + element.getSimpleName());
                try {
                    JavaFileObject javaFileObject = mFiler.createSourceFile("com.compile.router." + element.getSimpleName() + "RouterImp");
                    Writer writer = javaFileObject.openWriter();
                    writer.write(createJavaFile(packageName, element.getSimpleName().toString(), value));
//                    writer.write(content);
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


    private String createJavaFile(String packageName, String className, String routerKey) {
        //build method
        ClassName override = ClassName.get("java.lang", "Override");
        MethodSpec register = MethodSpec.methodBuilder("register")
                .addAnnotation(override)
                .returns(void.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Map.class, "routerMap")
                .addStatement("routerMap.put(\"" + routerKey + "\"," + className + ".class)")
                .build();
        // build class
        ClassName superClassName = ClassName.get("com.mvp.compile", "IRouterListener");
        ClassName activityClassName = ClassName.get(packageName, className);
        FieldSpec activity = FieldSpec.builder(activityClassName, "activity", Modifier.PRIVATE).build();
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className + "RouterImp")
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("This file is generated by apt, please do not modify!")
                .addSuperinterface(superClassName)
                .addMethod(register)
                .addField(activity)
                .build();

        JavaFile javaFile = JavaFile.builder("com.compile.router", classBuilder.build()).build();
        return javaFile.toString();
    }


}

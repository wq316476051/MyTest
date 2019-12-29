package com.wang.mytest.apt.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.wang.mytest.apt.annotation.Route;
import com.wang.mytest.apt.annotation.RouteBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class RouteProcessor extends AbstractProcessor {

    private Messager mMessager;
    private Filer mFiler;
    private Elements mElementUtils;
    private Types mTypeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
        mMessager.printMessage(Diagnostic.Kind.NOTE, "init");
        mFiler = processingEnvironment.getFiler();
        mElementUtils = processingEnvironment.getElementUtils();
        mTypeUtils = processingEnvironment.getTypeUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(Route.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Map<String, String> options = processingEnv.getOptions();
        mMessager.printMessage(Diagnostic.Kind.NOTE, "options = " + options);

        List<RouteBean> routeBeanList = new ArrayList<>();

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Route.class);
        mMessager.printMessage(Diagnostic.Kind.NOTE, "size = " + elements.size());
        for (Element element : elements) {
            if (element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                Route route = typeElement.getAnnotation(Route.class);
                routeBeanList.add(new RouteBean(route.title(), route.path(), typeElement.getQualifiedName().toString()));
            }
        }
        if (!routeBeanList.isEmpty()) {
            generateJavaFile(routeBeanList);
        }
        return true;
    }

    private void generateJavaFile(List<RouteBean> routeBeanList) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "process Route start.");
        mMessager.printMessage(Diagnostic.Kind.WARNING, "process Route start." + routeBeanList);

        // 成员变量
        FieldSpec fieldMap = FieldSpec.builder(ParameterizedTypeName.get(Map.class, String.class, RouteBean.class), "mMap", Modifier.PRIVATE, Modifier.STATIC)
                .build();

        // 静态代码块
        CodeBlock.Builder staticBlockBuilder = CodeBlock.builder()
                .addStatement("mMap = new $T<>()", HashMap.class);
        for (RouteBean routeBean : routeBeanList) {
            staticBlockBuilder.addStatement("mMap.put($S, new RouteBean($S, $S, $S))", routeBean.path,
                    routeBean.title, routeBean.path, routeBean.className);
        }
        CodeBlock staticBlock = staticBlockBuilder.build();

        // 成员方法
        MethodSpec methodGet = MethodSpec.methodBuilder("get")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(ParameterizedTypeName.get(Map.class, String.class, RouteBean.class))
                .addStatement("return mMap")
                .build();

        // 类定义
        TypeSpec routeProvider = TypeSpec.classBuilder("RouteProvider" + System.currentTimeMillis())
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClassName.get("com.wang.mytest.apt.api", "IRouteProvider"))
                .addField(fieldMap)
                .addStaticBlock(staticBlock)
                .addMethod(methodGet)
                .build();

        // Java 文件
        JavaFile javaFile = JavaFile.builder("com.wang.mytest", routeProvider)
                .build();

        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
        }

        mMessager.printMessage(Diagnostic.Kind.NOTE, "process Route end.");
    }
}

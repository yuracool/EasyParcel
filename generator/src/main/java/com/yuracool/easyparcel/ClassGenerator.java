package com.yuracool.easyparcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yura on 28.02.2015.
 */
public class ClassGenerator {
    private String packageName;
    private List<String> imports = new ArrayList<>();
    private String modifiers;
    private String name;
    private String extension;
    private List<String> implementations = new ArrayList<>();
    private List<String> fields = new ArrayList<>();
    private List<String> methods = new ArrayList<>();

    public ClassGenerator(String modifiers, String name){
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException("class name is empty");

        this.modifiers = modifiers == null ? "" : modifiers;
        this.name = name;
    }

    public void setPackageName(String packageName){
        this.packageName = packageName;
    }

    public void addImport(String importPath){
        imports.add(importPath);
    }

    public void setExtension(String name){
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException("class name is empty");

        extension = name;
    }

    public void addImplementation(String name){
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException("class name is empty");

        implementations.add(name);
    }

    public void addField(String modifier, String type, String name){
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException("name is empty");

        if(type == null || type.isEmpty())
            throw new IllegalArgumentException("type is empty");

        StringBuilder builder = new StringBuilder();
        builder.append((modifier == null || modifier.isEmpty()) ? "" : modifier);
        builder.append(' ');
        builder.append(type);
        builder.append(' ');
        builder.append(name);
        builder.append(";");

        fields.add(builder.toString());
    }

    public void addMethod(String modifiers, String returnType, String name, String body, String... typeParams){
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException("name is empty");

        if(returnType == null || returnType.isEmpty())
            throw new IllegalArgumentException("return Type is empty");

        StringBuilder builder = new StringBuilder();
        builder.append(modifiers == null ? "" : modifiers);
        builder.append(" ");
        builder.append(returnType);
        builder.append(" ");
        builder.append(name);
        builder.append("(");
        builder.append(getMethodParams(typeParams));
        builder.append("){\n");
        builder.append(body);
        builder.append("}");

        methods.add(builder.toString());
    }

    public String generate() {
        StringBuilder builder = new StringBuilder();

        addPackage(builder);
        builder.append("\n");
        addImport(builder);
        builder.append("\n");
        generateClassName(builder);
        addExtension(builder);
        addImplementations(builder);
        builder.append("{\n");
        addFields(builder);
        addMethods(builder);
        builder.append("}\n");

        return builder.toString();
    }

    private void addPackage(StringBuilder builder) {
        if(packageName != null){
            builder.append("package ");
            builder.append(packageName);
            builder.append(";\n");
        }
    }

    private void addImport(StringBuilder builder) {
        for(String imp : imports){
            builder.append("import ");
            builder.append(imp);
            builder.append(";\n");
        }
    }

    private void generateClassName(StringBuilder builder){
        builder.append(modifiers);
        builder.append(" class ");
        builder.append(name);
        builder.append(" ");
    }

    private void addExtension(StringBuilder builder) {
        if(extension != null && !extension.isEmpty()){
            builder.append("extends ");
            builder.append(extension);
            builder.append(" ");
        }
    }

    private void addImplementations(StringBuilder builder) {
        if(!implementations.isEmpty()){
            builder.append("implements ");

            for(int i=0; i<implementations.size(); i++){
                builder.append(implementations.get(i));

                if(i != implementations.size() - 1)
                    builder.append(", ");
            }

            builder.append(" ");
        }
    }

    private void addFields(StringBuilder builder) {
        for(String field : fields){
            builder.append(field);
            builder.append("\n");
        }
    }

    private void addMethods(StringBuilder builder) {
        for (String method : methods){
            builder.append("\n");
            builder.append(method);
            builder.append("\n");
        }
    }

    private String getMethodParams(String... typeParams){
        StringBuilder builder = new StringBuilder();

        if(typeParams != null) {
            for (int i=0; i<typeParams.length; i++){
                builder.append(typeParams[i]);
                builder.append(" ");
                builder.append("var");

                if(typeParams.length > 1)
                    builder.append(i + 1);

                if(i < typeParams.length - 1)
                    builder.append(", ");
            }
        }

        return builder.toString();
    }
}

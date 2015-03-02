package com.yuracool.easyparcel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    static final String[] TYPES = new String[]{
            "byte", "short", "int", "long", "float", "double", "char", "boolean", "String",
            "byte[]", "short[]", "int[]", "long[]", "float[]", "double[]", "char[]", "boolean[]", "String[]",
            "Byte", "Short", "Integer", "Long", "Float", "Double", "Character", "Boolean",
            "Byte[]", "Short[]", "Integer[]", "Long[]", "Float[]", "Double[]", "Character[]", "Boolean[]"
    };

    static final String PATH = "test/src/main/java/com/yuracool/annotations/data/";
    static final String CLASS_NAME = "Entity";
    static final String PACKAGE_NAME = "com.yuracool.data";
    static final String IMPORT_EASY_PARCEL = "com.yuracool.easyparcel.EasyParcel";
    static final int SIMPLE = 10;
    static final int ARRAY = 2;
    static final int ARRAY_ELEMENTS = 100;

    public static void main(String... params) {
        File file = new File(PATH + CLASS_NAME + ".java");
        String clazz = composeClass(new ValueGenerator());
        saveDataToFile(file, clazz);
    }

    private static String composeClass(ValueGenerator valueGenerator) {
        ClassGenerator gen = new ClassGenerator("public", CLASS_NAME);
        gen.setPackageName(PACKAGE_NAME);
        gen.addImport(IMPORT_EASY_PARCEL);
        gen.setExtension("EasyParcel");

        StringBuilder initMethodBody = new StringBuilder();

        for (String type : TYPES) {
            int count;
            if (type.contains("[]"))
                count = ARRAY;
            else
                count = SIMPLE;

            for (int i = 0; i < count; i++) {
                String varName = getFieldName(type, i);
                gen.addField("public", type, varName);
                initField(initMethodBody, type, varName, valueGenerator);
            }
        }

        gen.addMethod("public", "void", "init", initMethodBody.toString(), null);

        return gen.generate();
    }

    private static String getFieldName(String type, int index) {
        return getPrefix(type) + getName(type) + getSuffix(type, index);
    }

    private static String getPrefix(String type) {
        String tmp = type.toLowerCase();

        if (!tmp.equals(type))
            return "obj";

        return "";
    }

    private static String getName(String type) {
        if (type.contains("[]"))
            return type.replace("[]", "");

        return type;
    }

    private static String getSuffix(String type, int index) {
        String ret = "";

        if (type.contains("[]"))
            ret = "Array";

        ret += String.valueOf(index);

        return ret;
    }

    private static void initField(StringBuilder methodBody, String type, String varName, ValueGenerator valueGenerator) {
        methodBody.append(varName);
        methodBody.append(" = ");

        if(type.contains("[]")){
            methodBody.append("new ");
            methodBody.append(type);
            methodBody.append("{");

            String tmpType = type.replace("[]", "");
            for(int i=0; i<ARRAY_ELEMENTS; i++){
                methodBody.append(getValue(tmpType, valueGenerator));

                if(i < ARRAY_ELEMENTS - 1)
                    methodBody.append(", ");
            }

            methodBody.append("}");
        }else{
            methodBody.append(getValue(type, valueGenerator));
        }

        methodBody.append(";\n");
    }

    private static String getValue(String type, ValueGenerator valueGenerator){
        switch (type.toLowerCase()) {
            case "int":
            case "integer":
                return String.valueOf(valueGenerator.getInt());

            case "byte":
                return String.valueOf(valueGenerator.getByte());

            case "short":
                return String.valueOf(valueGenerator.getShort());

            case "long":
                return String.valueOf(valueGenerator.getLong()) + "L";

            case "float":
                return String.valueOf(valueGenerator.getFloat()) + "f";

            case "double":
                return String.valueOf(valueGenerator.getDouble());

            case "boolean":
                return String.valueOf(valueGenerator.getBoolean());

            case "char":
            case "character":
                return "'" + String.valueOf(valueGenerator.getChar()) + "'";

            case "string":
                return "\"" + valueGenerator.getString() + "\"";

            default:
                return "new " + type + "()";
        }
    }

    private static void saveDataToFile(File file, String data) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}

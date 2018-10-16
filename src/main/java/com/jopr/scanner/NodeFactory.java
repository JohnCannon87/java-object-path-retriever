package com.jopr.scanner;

import com.jopr.scanner.exception.PrimitiveNotFoundException;
import com.jopr.scanner.model.Node;

public class NodeFactory {

    public static Node createNode(String path, Class<?> clazz) {
        if (clazz.isPrimitive()) {
            return createPrimitiveNode(path, clazz);
        } else {
            return new Node(path, clazz);
        }
    }

    private static Node createPrimitiveNode(String path, Class<?> clazz) {
        String className = clazz.getSimpleName();
        Class<?> nonPrimitiveClass;
        switch (className) {
            case "byte":
                nonPrimitiveClass = Byte.class;
                break;
            case "short":
                nonPrimitiveClass = Short.class;
                break;
            case "int":
                nonPrimitiveClass = Integer.class;
                break;
            case "long":
                nonPrimitiveClass = Long.class;
                break;
            case "float":
                nonPrimitiveClass = Float.class;
                break;
            case "double":
                nonPrimitiveClass = Double.class;
                break;
            case "char":
                nonPrimitiveClass = Character.class;
                break;
            case "boolean":
                nonPrimitiveClass = Boolean.class;
                break;
            default:
                throw new PrimitiveNotFoundException(
                        String.format("Could not find matching object class for primitive: %s", className));
        }

        return new Node(path, nonPrimitiveClass);
    }

}

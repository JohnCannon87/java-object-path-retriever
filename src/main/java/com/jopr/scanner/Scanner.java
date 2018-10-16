package com.jopr.scanner;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import com.jopr.scanner.model.Node;

/**
 * I scan a provided class and return a collection of string parameters that map to all primitive values inside that
 * classes graph.
 *
 * @author john.cannon
 *
 */
public class Scanner {

    public static final String GET_PREFIX = "get";
    private static final String GET_CLASS = "getClass";
    public static final String SEPERATOR = ".";
    private static final List<Class<?>> SIMPLE_TYPES = Arrays.asList(String.class, Boolean.class, Character.class,
            Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Void.class, BigInteger.class,
            BigDecimal.class);

    /**
     * I return a list of strings that can be used to retrieve objects from objects.
     *
     * @param clazz
     * @return
     */
    public static <T extends Object> List<Node> getParameterGraphAsList(Class<T> clazz) {
        String basePathString = StringUtils.uncapitalize(clazz.getSimpleName());
        return getParameterGraphAsList(clazz, basePathString);
    }

    /**
     * Generate the parameter graph as a list.
     *
     * @param clazz
     * @param prefix
     * @return
     */
    private static List<Node> getParameterGraphAsList(Class<?> clazz, String prefix) {
        Method[] methods = clazz.getMethods();
        List<Node> result = getSimpleVariablesGraphAsList(prefix, methods);
        result.addAll(getComplexVariablesGraphAsList(prefix, methods));
        return result;
    }

    /**
     * Generate parameter graph of all "simple" variables (Defined above), ignores any other variable.
     *
     * @param prefix
     * @param methods
     * @return
     */
    private static List<Node> getSimpleVariablesGraphAsList(String prefix, Method[] methods) {
        List<Node> result = Arrays.stream(methods).filter(method -> !method.getName().equals(GET_CLASS))
                .filter(method -> StringUtils.startsWith(method.getName(), GET_PREFIX))
                .filter(method -> isMethodReturnTypeSimpleOrSimpleWrapper(method))
                .map(method -> NodeFactory.createNode(prefix + SEPERATOR
                        + StringUtils.uncapitalize(StringUtils.removeStart(method.getName(), GET_PREFIX)),
                        method.getReturnType()))
                .collect(Collectors.toList());
        return result;
    }

    /**
     * Generate parameter graph of all non "simple" variables (Defined above), this uses recursion and starts a new
     * parameter graph from this point, passing in the base prefix from the current location.
     *
     * @param prefix
     * @param methods
     * @return
     */
    private static List<Node> getComplexVariablesGraphAsList(String prefix, Method[] methods) {
        List<Node> result = new ArrayList<>();
        Arrays.stream(methods).filter(method -> !method.getName().equals(GET_CLASS))
                .filter(method -> StringUtils.startsWith(method.getName(), GET_PREFIX))
                .filter(method -> isMethodReturnTypeNotSimpleOrSimpleWrapper(method))
                .forEach(method -> result.addAll(getParameterGraphAsList(method.getReturnType(), prefix + SEPERATOR
                        + StringUtils.uncapitalize(StringUtils.removeStart(method.getName(), GET_PREFIX)))));
        return result;
    }

    private static boolean isMethodReturnTypeSimpleOrSimpleWrapper(Method method) {
        Class<?> returnType = method.getReturnType();
        return getSimpleTypes().contains(returnType) || returnType.isPrimitive();
    }

    private static boolean isMethodReturnTypeNotSimpleOrSimpleWrapper(Method method) {
        Class<?> returnType = method.getReturnType();
        return !getSimpleTypes().contains(returnType) && !returnType.isPrimitive();
    }

    /**
     * This exists to allow the Scanner class to be overridden and a new set of "simple" variables to be defined.
     *
     * @return
     */
    private static List<Class<?>> getSimpleTypes() {
        return SIMPLE_TYPES;
    }

}

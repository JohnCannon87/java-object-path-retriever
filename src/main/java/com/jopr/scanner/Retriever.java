package com.jopr.scanner;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.jopr.scanner.exception.PrimitiveTypesNotAllowedException;
import com.jopr.scanner.model.Node;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Retriever {

    /**
     * Convenience method that allows you to just pass in the node but you must cast the response, useful if you don't
     * care what type the response actually is.
     *
     * @param object
     * @param node
     * @return
     * @throws PrimitiveTypesNotAllowedException
     */
    public static <T> Object getValue(T object, Node node) throws PrimitiveTypesNotAllowedException {
        return getValue(object, node.getPath(), node.getClass());
    }

    /**
     * Moves over the passed in object and finds the path requestPath and casts the response to the type returnType.
     *
     * @param object
     * @param requestPath
     * @param clazz
     * @return
     * @throws PrimitiveTypesNotAllowedException
     */
    public static <T, R extends Object> R getValue(T object, String requestPath, Class<R> returnType)
            throws PrimitiveTypesNotAllowedException {
        checkForPrimitiveRequest(returnType);
        List<String> seperatedCallList =
                new LinkedList<>(Arrays.asList(StringUtils.split(requestPath, Scanner.SEPERATOR)));
        seperatedCallList.remove(0); // Remove First call as it is for the object being passed in.
        return getValue(object, requestPath, returnType, seperatedCallList);
    }

    private static <T, R> R getValue(T object, String requestPath, Class<R> returnType,
            List<String> seperatedCallList) {
        Object result = null;
        String call = seperatedCallList.get(0);
        try {
            result = object.getClass().getMethod(Scanner.GET_PREFIX + StringUtils.capitalize(call)).invoke(object);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            log.error(String.format("Failed to parse request for object %s, with path: %s, and specific call: %s",
                    object, requestPath, call), e);
        }
        if (result.getClass().equals(returnType)) {
            return returnType.cast(result);
        } else {
            seperatedCallList.remove(0); // Remove First call as it is for the object being passed in.
            return getValue(result, requestPath, returnType, seperatedCallList);
        }
    }

    private static void checkForPrimitiveRequest(Class<?> returnType) throws PrimitiveTypesNotAllowedException {
        if (returnType.isPrimitive()) {
            throw new PrimitiveTypesNotAllowedException("Primitive Types Are Not Allowed");
        }
    }

}

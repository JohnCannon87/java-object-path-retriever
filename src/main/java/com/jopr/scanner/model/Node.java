package com.jopr.scanner.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A Node on a scanned object graph, contains a string representing this nodes point in the graph and the type that this
 * node will return.
 *
 * @author john.cannon
 *
 */
@Data
@AllArgsConstructor
public class Node {

    private String path;
    private Class<?> type;

}

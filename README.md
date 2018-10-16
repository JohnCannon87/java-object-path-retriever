# java-object-path-retriever
Small Library to support accessing java variables (via get methods) via dot notation.

[![Build Status](https://travis-ci.org/JohnCannon87/java-object-path-retriever.svg?branch=master)](https://travis-ci.org/JohnCannon87/java-object-path-retriever)

# Usage
## Scanning
Scanning is the act of building up a list of available string paths for an object this is done using the Scanner class e.g.

```java
  List<Node> nodes = Scanner.getParameterGraphAsList(Foo.java);
```

A Node is a class containing both the string path to access that node and the return type (Not primitive return types will be converted to their Object equivelents (since you can't cast a primitive for the retrieval side later)

Running this against this simple java model:

```java

public class Foo {
  
  private Bar bar;
  private String fooString;
  
}

public class Bar {

  private Integer barInteger;

}

```

We would then get a response containing a List of 2 Nodes:
```
Node<"foo.fooString", String.class>
Node<"foo.bar.barInteger", Integer.class>
```


## Retrieval
With a Node (or a String and return type) you can now access any part of a java object graph via a dot notation string e.g. for the same class structure as above the following call:

```java
  Bar bar = new Bar(42);
  Foo fooInstantiation = new Foo(bar, "fooString");
  Integer result = Retriever.getValue(fooInstantiation, "foo.bar.barInteger", Integer.class);
```

Result would now have the value of 42 inside.

## Together
Using the two together we can construct a list of Nodes, allow a user or some config to pick which ones are needed and use that to retrieve values.


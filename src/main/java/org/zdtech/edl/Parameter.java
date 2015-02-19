package org.zdtech.edl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Schekalev
 * @since 17.02.15
 */
public class Parameter<T> {
    private String name;
    private List<Parameter> children;
    private T type;
    private List<ParameterListener<T>> parameterListeners;

    public Parameter(String name, List<Parameter> children, T type) {
        this.name = name;
        this.children = children;
        this.type = type;
        this.parameterListeners = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Parameter> getChildren() {
        return children;
    }

    public T getType() {
        return type;
    }

    public void addParameterListener(ParameterListener<T> parameterListener) {
        this.parameterListeners.add(parameterListener);
    }

    public void removeParameterListener(ParameterListener<T> parameterListener) {
        this.parameterListeners.remove(parameterListener);
    }
}

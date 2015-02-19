package org.zdtech.edl.system;

/**
 * @author Schekalev
 * @since 18.02.15
 */
public interface EDLParameterListener<T> {
    public void onParameterChanged(T value);
}

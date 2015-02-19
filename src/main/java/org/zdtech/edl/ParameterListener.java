package org.zdtech.edl;

/**
 * @author Schekalev
 * @since 17.02.15
 */
public interface ParameterListener<T> {

    public void onParameterChanged(T value);

}

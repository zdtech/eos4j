package org.zdtech.edl.system;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Schekalev
 * @since 18.02.15
 */
public abstract class EDLParameter<T> {
    private List<EDLParameterListener<T>> parameterListeners;
    private String name;
    private long uid;
    protected boolean isWriteEnabled;

    protected EDLParameter(long uid, String name) {
        this.name = name;
        this.uid = uid;
        this.parameterListeners = new ArrayList<>();
        this.isWriteEnabled = false;
    }

    public abstract EDLParameter getParameterByName(String name);

    public abstract boolean canWriteParameter(String name);

    public abstract boolean isParameterExists(String name);

    public boolean isWriteEnabled() {
        return isWriteEnabled;
    }

    public void setWriteEnabled(boolean isWriteEnabled) {
        this.isWriteEnabled = isWriteEnabled;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public abstract T getValue();

    public abstract void setValue(T value);

    public void addParameterListener(EDLParameterListener<T> parameterListener) {
        this.parameterListeners.add(parameterListener);
    }

    public void removeParameterListener(EDLParameterListener<T> parameterListener) {
        this.parameterListeners.remove(parameterListener);
    }

    protected List<EDLParameterListener<T>> getParameterListeners() {
        return parameterListeners;
    }

    protected void fireValueChanged(T value) {
        for (EDLParameterListener<T> edlParameterListener : this.parameterListeners) {
            edlParameterListener.onParameterChanged(value);
        }
    }
}

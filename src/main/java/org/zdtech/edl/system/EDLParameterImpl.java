package org.zdtech.edl.system;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Schekalev
 * @since 18.02.15
 */
public class EDLParameterImpl<T> extends EDLParameter<T> {
    protected T value;
    protected Class<T> clazz;
    private List<EDLParameter> children;

    public EDLParameterImpl(long uid, String name, T value, Class<T> clazz) {
        super(uid, name);
        this.value = value;
        this.clazz = clazz;
        this.children = getChildrenFromClass(clazz, value);
    }

    private List<EDLParameter> getChildrenFromClass(Class clazz, Object value) {
        List<EDLParameter> children = new ArrayList<>();
        List<Field> fields = getInheritedFields(clazz);
        for (Field field : fields) {
            if (checkIsEDLParameter(field.getType())) {
                try {
                    field.setAccessible(true);
                    EDLParameter parameter = (EDLParameter) field.get(value);
                    children.add(parameter);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return children;
    }

    private boolean checkIsEDLParameter(Class<?> type) {
        return type == EDLParameter.class || type != null && checkIsEDLParameter(type.getSuperclass());
    }

    public static List<Field> getInheritedFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    public void setValue(T value) {
        if (this.isWriteEnabled) {
            this.value = value;
            fireValueChanged(value);
        }
    }

    @Override
    public EDLParameter getParameterByName(String name) {
        boolean isCompound = isCompoundParameter(name);
        String ownName = getOwnName(name);
        for (EDLParameter edlParameter : this.children) {
            if (edlParameter.getName().equals(ownName))
                if (!isCompound) {
                    return edlParameter;
                } else {
                    return edlParameter.getParameterByName(name.substring(ownName.length() + 1));
                }
        }
        return null;
    }

    @Override
    public boolean canWriteParameter(String name) {
        EDLParameter parameter = getParameterByName(name);
        return parameter != null && parameter.isWriteEnabled();

    }

    @Override
    public boolean isParameterExists(String name) {
        boolean isCompound = isCompoundParameter(name);
        String ownName = getOwnName(name);
        for (EDLParameter parameter : this.children) {
            if (parameter.getName().equals(ownName)) {
                if (!isCompound) {
                    return true;
                } else {
                    return parameter.isParameterExists(name.substring(ownName.length() + 1));
                }
            }
        }
        return false;
    }

    private String getOwnName(String name) {
        if (name.indexOf('.') == -1)
            return name;
        return name.substring(0, name.indexOf('.'));
    }

    private boolean isCompoundParameter(String name) {
        return name.indexOf('.') != -1;
    }

    @Override
    public T getValue() {
        return this.value;
    }
}

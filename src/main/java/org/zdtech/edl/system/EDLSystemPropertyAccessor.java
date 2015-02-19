package org.zdtech.edl.system;

import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypedValue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Schekalev
 * @since 18.02.15
 */
public class EDLSystemPropertyAccessor implements PropertyAccessor {

    @Override
    public Class<?>[] getSpecificTargetClasses() {
        return null;
    }

    @Override
    public boolean canRead(EvaluationContext context, Object target, String name) throws AccessException {
        List<EDLParameter> parameters = getChildrenFromClass(target.getClass(), target);
        return isParameterExists(name, parameters);
    }

    @Override
    public TypedValue read(EvaluationContext context, Object target, String name) throws AccessException {
        List<EDLParameter> parameters = getChildrenFromClass(target.getClass(), target);
        EDLParameter parameter = getParameterByName(name, parameters);
        if (parameter == null)
            return TypedValue.NULL;
        return new TypedValue(parameter.getValue());
    }

    @Override
    public boolean canWrite(EvaluationContext context, Object target, String name) throws AccessException {
        List<EDLParameter> parameters = getChildrenFromClass(target.getClass(), target);
        return canWriteParameter(name, parameters);
    }

    @Override
    public void write(EvaluationContext context, Object target, String name, Object newValue) throws AccessException {
        List<EDLParameter> parameters = getChildrenFromClass(target.getClass(), target);
        EDLParameter parameter = getParameterByName(name, parameters);
        if (parameter != null) {
            parameter.setValue(newValue);
        }
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

    public boolean isParameterExists(String name, List<EDLParameter> parameters) {

        for (EDLParameter parameter : parameters) {
            if (parameter.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public EDLParameter getParameterByName(String name, List<EDLParameter> parameters) {
        for (EDLParameter edlParameter : parameters) {
            if (edlParameter.getName().equals(name))
                return edlParameter;
        }
        return null;
    }

    public boolean canWriteParameter(String name, List<EDLParameter> parameters) {
        EDLParameter parameter = getParameterByName(name, parameters);
        return parameter != null && parameter.isWriteEnabled();

    }
}

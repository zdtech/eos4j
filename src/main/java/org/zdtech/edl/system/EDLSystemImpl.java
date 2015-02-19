package org.zdtech.edl.system;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Schekalev
 * @since 18.02.15
 */
public class EDLSystemImpl implements EDLSystem {

    protected List<EDLParameter> parameters;
    protected EventsHolder eventsHolder;

    public EDLSystemImpl() {
        this.parameters = new ArrayList<>();
        this.eventsHolder = new EventsHolder(this);
    }

    @Override
    public List<EDLParameter> getParameters() {
        return this.parameters;
    }

    @Override
    public EventsHolder getEventsHolder() {
        return this.eventsHolder;
    }

    @Override
    public boolean isParameterExists(String name) {
        boolean isCompound = isCompoundParameter(name);
        String ownName = getOwnName(name);
        for (EDLParameter parameter : this.parameters) {
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
    public EDLParameter getParameterByName(String name) {
        boolean isCompound = isCompoundParameter(name);
        String ownName = getOwnName(name);
        for (EDLParameter edlParameter : this.parameters) {
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
}

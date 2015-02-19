package org.zdtech.edl.system;


import java.util.List;

/**
 * @author Schekalev
 * @since 17.02.15
 */
public interface EDLSystem {

    public List<EDLParameter> getParameters();

    public EventsHolder getEventsHolder();

    public boolean isParameterExists(String name);

    public EDLParameter getParameterByName(String name);

    public boolean canWriteParameter(String name);
}

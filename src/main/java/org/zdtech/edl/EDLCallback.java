package org.zdtech.edl;

import java.util.List;
import java.util.Map;

/**
 * @author Schekalev
 * @since 18.02.15
 */
public interface EDLCallback {

    public List<String> getParametersNamesForDesiredValues();

    public void onEventHappened(Event event, Map<String, Object> desiredValues);
}

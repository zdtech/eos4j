package org.zdtech.edl.system;

import org.zdtech.edl.EDLCallback;
import org.zdtech.edl.Event;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Schekalev
 * @since 17.02.15
 */
public class EventsHolder {
    private EDLSystem system;
    protected Map<Event, EventHandler> eventHandlerByEvent;

    public EventsHolder(EDLSystem system) {
        this.system = system;
        this.eventHandlerByEvent = new HashMap<>();
    }

    public void addEvent(Event event, EDLCallback callback) {
        EventHandler eventHandler = new EventHandler(this.system, event, callback);
        this.eventHandlerByEvent.put(event, eventHandler);
    }


    public void removeEvent(Event event) {
        if (this.eventHandlerByEvent.containsKey(event)){
            EventHandler eventHandler = this.eventHandlerByEvent.get(event);
            eventHandler.dispose();
            this.eventHandlerByEvent.remove(event);
        }
    }

    public EDLSystem getSystem() {
        return this.system;
    }
}

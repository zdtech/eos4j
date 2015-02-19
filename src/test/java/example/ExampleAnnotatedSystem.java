package example;

import org.zdtech.edl.annotations.Parameter;
import org.zdtech.edl.system.EventsHolder;

/**
 * @author Schekalev
 * @since 18.02.15
 */
@org.zdtech.edl.annotations.System
public class ExampleAnnotatedSystem {
    @Parameter
    private int a;

    @Parameter
    private int b;

    @Parameter
    private int c;

    private EventsHolder eventsHolder;

    @org.zdtech.edl.annotations.EventsHolder
    private EventsHolder getEventsHolder() {
        return this.eventsHolder;
    }
}

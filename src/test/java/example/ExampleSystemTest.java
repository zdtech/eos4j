package example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.zdtech.edl.EDLCallback;
import org.zdtech.edl.Event;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Schekalev
 * @since 18.02.15
 */
@RunWith(JUnit4.class)
public class ExampleSystemTest {

    @Test
    public void test() {
        ExampleSystem exampleSystem = new ExampleSystem();
        EDLCallback callback = new EDLCallback() {
            @Override
            public List<String> getParametersNamesForDesiredValues() {
                return Arrays.asList("a.b.a");
            }

            @Override
            public void onEventHappened(Event event, Map<String, Object> desiredValues) {

                if (desiredValues.containsKey("a.b.a")) {
                    System.out.println("Событие произошло в системе! + a.b.a: " + desiredValues.get("a.b.a"));
                }
            }
        };
        exampleSystem.getEventsHolder().addEvent(new Event(0, "test", 0, "a.a - b + a.b.a - c == -50"), callback);
        exampleSystem.getA().setA(20);
    }
}

package example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.zdtech.edl.EDLCallback;
import org.zdtech.edl.Event;

/**
 * @author Schekalev
 * @since 18.02.15
 */
@RunWith(JUnit4.class)
public class ExampleSystemTest {

    @Test
    public void test() {
        ExampleSystem exampleSystem = new ExampleSystem();
        EDLCallback callback = event -> {
            System.out.println("Событие произошло в системе!");
        };
        exampleSystem.getEventsHolder().addEvent(new Event(0, "test", 0, "a.a - b + a.b.a - c == -60"), callback);
        exampleSystem.getA().setA(20);
    }
}

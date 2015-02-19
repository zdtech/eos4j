package example;

import org.zdtech.edl.system.EDLParameter;
import org.zdtech.edl.system.EDLParameterImpl;
import org.zdtech.edl.system.EDLSystemImpl;

import java.util.Arrays;

/**
 * @author Schekalev
 * @since 17.02.15
 */
public class ExampleSystem extends EDLSystemImpl {

    private EDLParameter<ExampleClassType> a;
    private EDLParameter<Integer> b;
    private EDLParameter<Integer> c;

    public ExampleSystem() {
        this.a = new EDLParameterImpl<>(0l, "a", new ExampleClassType(10, 20), ExampleClassType.class);
        this.a.setWriteEnabled(true);
        this.b = new EDLParameterImpl<>(1l, "b", 40, Integer.class);
        this.b.setWriteEnabled(true);
        this.c = new EDLParameterImpl<>(2l, "c", 50, Integer.class);
        this.c.setWriteEnabled(true);
        this.parameters.addAll(Arrays.asList(a, b, c));
    }

    public ExampleClassType getA() {
        return a.getValue();
    }

    public Integer getB() {
        return b.getValue();
    }

    public void setB(Integer b) {
        this.b.setValue(b);
    }

    public Integer getC() {
        return c.getValue();
    }

    public void setC(Integer c) {
        this.c.setValue(c);
    }

}

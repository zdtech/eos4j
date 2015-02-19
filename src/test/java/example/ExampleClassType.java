package example;

import org.zdtech.edl.system.EDLParameter;
import org.zdtech.edl.system.EDLParameterImpl;

/**
 * @author Schekalev
 * @since 18.02.15
 */
public class ExampleClassType {
    public EDLParameter<Integer> a;
    public EDLParameter<ExampleClassType2> b;

    public ExampleClassType(Integer a, Integer b) {
        this.a = new EDLParameterImpl<>(0l, "a", a, Integer.class);
        this.a.setWriteEnabled(true);
        this.b = new EDLParameterImpl<>(0l, "b", new ExampleClassType2(b), ExampleClassType2.class);
        this.b.setWriteEnabled(true);
    }

    public Integer getA() {
        return a.getValue();
    }

    public void setA(Integer a) {
        this.a.setValue(a);
    }
}

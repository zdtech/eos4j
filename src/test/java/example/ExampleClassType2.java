package example;

import org.zdtech.edl.system.EDLParameter;
import org.zdtech.edl.system.EDLParameterImpl;

/**
 * @author Schekalev
 * @since 18.02.15
 */
public class ExampleClassType2 {
    public EDLParameter<Integer> a;

    public ExampleClassType2(Integer a) {
        this.a = new EDLParameterImpl<>(0l, "a", a, Integer.class);
        this.a.setWriteEnabled(true);
    }

    public Integer getA() {
        return a.getValue();
    }

    public void setA(Integer a) {
        this.a.setValue(a);
    }
}

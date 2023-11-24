package Utils;

import java.io.Serializable;

public class Pair<T1, T2> implements Serializable {
    private static final long serialVersionUID = 1L; // version for compatibility
    public T1 a;
    public T2 b;
    public Pair(T1 a, T2 b) {
        this.a = a;
        this.b = b;
    }
}

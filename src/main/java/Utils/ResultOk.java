package Utils;

import java.io.Serializable;

public class ResultOk extends Result {
    private static final long serialVersionUID = 1L; // version for compatibility
    Object data;
    ResultOk(Object result) {
        this.data = result;
    }
    public Object data() { return data; }
}

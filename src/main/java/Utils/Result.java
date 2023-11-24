package Utils;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Result implements Serializable {
    private static final long serialVersionUID = 1L; // version for compatibility

    // return the contained value or compute from handler
    public Object unwrap_or_else(Function<Error, Object> handler) {
        if (this instanceof ResultOk) {
            return ((ResultOk)this).data;
        }
        return handler.apply(((ResultError)this).error);
    }
    public static ResultOk ok(Object data) {
        var result = new ResultOk(data);
        return result;
    }
    public static ResultError error(Error error) {
        var result = new ResultError(error);
        return result;
    }
    public static ResultError error(String error) {
        var result = new ResultError(error);
        return result;
    }
}

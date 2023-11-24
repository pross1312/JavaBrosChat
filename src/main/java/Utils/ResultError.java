package Utils;

public class ResultError extends Result {
    private static final long serialVersionUID = 1L; // version for compatibility
    Error error;
    ResultError(Error error) {
        this.error = error;
    }
    ResultError(String error) {
        this.error = new Error(error);
    }
    public Error err() { return error; }
    public String msg() { return error.getMessage(); }
}

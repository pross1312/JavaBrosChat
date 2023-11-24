package Api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Consumer;

public class ApiCall implements Serializable {
    private static final long serialVersionUID = 1L; // version for compatibility
    public String service, name;
    public ArrayList<Object>  args;

    public ApiCall(String service, String name, Object... args) {
        this.service = service;
        this.name = name;
        this.args = new ArrayList<Object>(args.length);
        for (var obj : args)  {
            if (!this.args.add(obj)) {
                System.out.println("[ERROR] Could not initiate api call. We probably ran out of memory");
                System.exit(1);
            }
        }
    }
}

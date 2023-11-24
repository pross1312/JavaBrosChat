package Utils;

import java.util.function.Function;

public class Utils {
    public static void todo(String msg) {
        (new Exception()).printStackTrace();
        System.out.printf("[ERROR] Unimplemented %s\n", msg);
        System.exit(1);
    }
    public static void todo() {
        (new Exception()).printStackTrace();
        System.out.printf("[ERROR] Unimplemented\n");
        System.exit(1);
    }
}

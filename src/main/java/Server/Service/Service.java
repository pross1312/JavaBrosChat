package Server.Service;

import java.lang.reflect.InvocationTargetException;

public abstract class Service {
    public Object invoke(String func_name, Object... args) throws NoSuchMethodException, Throwable {
        var class_names = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            class_names[i] = args[i].getClass();
        }
        var method = this.getClass().getDeclaredMethod(func_name, class_names);
        try {
            return method.invoke(this, args);
        } catch (IllegalAccessException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
        return null; // unreachable
    }
}

package homework;

import annotation.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Ioc {
    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {
    }

    static TestLoggingInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(
                TestLogging.class.getClassLoader(), new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final Object myClass;
        private final Map<Method, Method> cacheMethods = new HashMap<>();

        DemoInvocationHandler(Object myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (findMethodInClassWithCash(method).isAnnotationPresent(Log.class)) {
                logger.info("invoking method:{}; args:{}", method, args);
            }
            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" + "myClass=" + myClass + '}';
        }

        private Method findMethodInClassWithCash(Method methodInterface) {
            if (cacheMethods.containsKey(methodInterface)) {
                logger.info("взял из кеша");
                return cacheMethods.get(methodInterface);
            } else {
                Method methodInClass = findMethodInClass(methodInterface);
                cacheMethods.put(methodInterface, methodInClass);
                return methodInClass;
            }
        }

        private Method findMethodInClass(Method methodInterface) {
            List<Method> methods = Arrays.stream(myClass.getClass().getDeclaredMethods())
                    .filter(x -> x.getName().equals(methodInterface.getName())
                            && x.getParameterCount() == methodInterface.getParameterCount()
                            && Arrays.equals(x.getParameterTypes(), methodInterface.getParameterTypes()))
                    .toList();

            if (methods.size() > 1) {
                throw new AppException("Однозначно не определен метод");
            }

            if (methods.isEmpty()) {
                throw new AppException("Не определен метод");
            }

            return methods.get(0);
        }
    }
}

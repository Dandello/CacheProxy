package ru.sberbank.homework;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CacheProxy implements InvocationHandler {
    private Map<CacheProxyKey, Object> cache;
    private Object delegate;

    @SuppressWarnings("unchecked")
    public static <T> T proxying(T delegate, Class<?> iface) {
        return (T) Proxy.newProxyInstance(
                iface.getClassLoader(),
                new Class<?>[] { iface },
                new CacheProxy(delegate));
    }

    CacheProxy(Object delegate) {
        cache = new HashMap<>();
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (!method.isAnnotationPresent(Cache.class))
            return method.invoke(delegate, args);
        CacheProxyKey cacheProxyKey = new CacheProxyKey(method, args);
        if(cache.containsKey(cacheProxyKey)) {
            System.out.println("The result of method's call is already cached!");
            return cache.get(cacheProxyKey);
        }
        Object result = method.invoke(delegate,args);
        cache.put(cacheProxyKey,result);
        return result;
    }

    private class CacheProxyKey {
        private Method proxyMethod;
        private Object[] args;
        CacheProxyKey( Method proxyMethod, Object[] args) {
            this.proxyMethod = proxyMethod;
            this.args = args;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CacheProxyKey that = (CacheProxyKey) o;
            if (!proxyMethod.equals(that.proxyMethod)) return false;
            return Arrays.equals(args, that.args);

        }

        @Override
        public int hashCode() {
            int result = proxyMethod.hashCode();
            result = 31 * result + Arrays.hashCode(args);
            return result;
        }
    }
}

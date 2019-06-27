package com.zzs.springboot.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy implements InvocationHandler {

    private Object object;
    public JDKProxy(Object object) {
        this.object = object;
    }

    public Object createProxy(){
        Object obj = Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),this);
        return obj;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdk动态代理增强---->前置");
        Object o = method.invoke(object,args);
        System.out.println("jdk动态代理增强---->后置");

        return o;
    }
}
class Test{
    public static void main(String[] args) {
        UserService proxy = (UserService) new JDKProxy(new UserServiceImpl()).createProxy();
        proxy.save();
    }
}

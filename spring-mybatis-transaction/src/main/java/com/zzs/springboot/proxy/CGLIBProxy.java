package com.zzs.springboot.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLIBProxy implements MethodInterceptor {

    private Object object;

    public CGLIBProxy(Object object) {
        this.object = object;
    }

    public Object createProxy(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(object.getClass());
        enhancer.setCallback(this);
        object = enhancer.create();
        return object;
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("CGLIB动态代理增强---->前置");
        Object o1 = methodProxy.invokeSuper(proxy,objects);
        System.out.println("CGLIB动态代理增强---->后置");
        return o1;
    }
}
class CTest{
    public static void main(String[] args) {
        OrderService orderService = (OrderService) new CGLIBProxy(new OrderService()).createProxy();
        orderService.add();
    }
}
package com.zzs.springboot.proxy;

public class UserServiceImpl implements UserService{
    @Override
    public void save() {
        System.out.println("UserServiceImpl--->新增");
    }

    @Override
    public void update() {
        System.out.println("UserServiceImpl--->修改");
    }

    @Override
    public void delete() {
        System.out.println("UserServiceImpl--->删除");
    }
}

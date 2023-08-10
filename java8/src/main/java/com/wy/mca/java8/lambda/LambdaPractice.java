package com.wy.mca.java8.lambda;

import com.wy.mca.java8.vo.Employee;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * @Description Lambda表达式练习
 * @Author wangyong01
 * @Date 2022/7/8 2:46 下午
 * @Version 1.0
 */
public class LambdaPractice {

    public void testBiFunc(){
        BiFunction<Integer, String, Employee> biFunction = (id, name) -> new Employee(id, name);
        Employee employee = biFunction.apply(10, "wang yong ");
        System.out.println(employee);
    }

    public void testBiConsumer(){
        BiConsumer<Integer, String> biConsumer = (id, name) -> System.out.println("id: " + id + "; name:" + name);
        biConsumer.accept(10, "wang yong");
    }

}

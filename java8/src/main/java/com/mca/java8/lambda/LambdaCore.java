package com.mca.java8.lambda;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Lambda内置的四大核心函数接口
 * 
 * 1  Consumer<T> : 消费型接口
 *    void accept(T t);
 *    
 * 2  Supplier<T> : 供给型接口
 *    T get(); 
 * 
 * 3  Function<T, R> : 函数型接口
 *    R apply(T t);
 * 
 * 4  Predicate<T> : 断言型接口
 *    boolean test(T t);
 * 
 * @author WangyongR
 * @date 2018年10月13日 下午10:03:06
 */
public class LambdaCore {

  @Test
  public void consume(){
    //1  Consumer<T> : 消费型接口
    Consumer<String> consumer = x -> System.out.println(x + ":consume");
    consumer.accept("wangyong");
    
    //2 Supplier<T> : 供给型接口
    Supplier<String> supplier = () -> "wangyong is a supplier";
    System.out.println(supplier.get());
    
    //3 Function<T, R> : 函数型接口
    Function<String,Integer> function = (x) -> (Integer.parseInt(x));
    Integer apply = function.apply("100");
    System.out.println(apply);
    
    //4 Predicate<T> : 断言型接口
    Predicate<String> predicate = (x) -> x.contains("yong");
    boolean contains = predicate.test("wangyongr");
    System.out.println(contains);
  }
  
}

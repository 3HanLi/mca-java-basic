package java.ref;

import com.wy.mac.java.basic.java8.vo.Employee;
import org.junit.Test;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 1  方法引用：若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用
 *    1.1 对象的引用 :: 实例方法名
 *    1.2 类名 :: 静态方法名
 *    1.3 类名 :: 实例方法名
 *    
 *    1.4 注意：
 *        ①方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
 *        ②若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
 * 
 * 2  构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
 *    2.1  类名 :: new
 *    
 * 3  数组引用
 *    3.1 类型[] :: new;
 * 
 * @author WangyongR
 * @date 2018年10月13日 下午10:22:05
 */
public class LambdaMethodRef {

  /**
   * 1.0 若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用
   */
  @Test
  public void test01(){
    //传统写法
    Consumer<String> con = (str) -> System.out.println(str);
    con.accept("Hello World！");
    
    //方法引用写法
    Consumer<String> consumer = System.out::println;
    consumer.accept("王勇");
  }
  
  /**
   * 1.1 对象的引用 :: 实例方法名
   */
  @Test
  public void objectRef(){
    //传统写法
    Employee employee = new Employee(1, 25, "wangyongr");
    Supplier<String> sup = () -> employee.getName();
    System.out.println(sup.get());
    
    //方法引用写法
    Supplier<String> supplier = employee::getName;
    System.out.println(supplier.get());
  }
  
  /**
   * 1.2 类名 :: 静态方法名
   */
  @Test
  public void staticMethodRef(){
    Comparator<Integer> com1 = (x, y) -> Integer.compare(x, y);
    
    Comparator<Integer> com2 = Integer::compare;
  }
  
  /**
   * 1.3 类名 :: 实例方法名
   */
  @Test
  public void instanceMethodRef(){
    BiPredicate<String, String> bp = (x, y) -> x.equals(y);
    System.out.println(bp.test("abcde", "abcde"));
    
    BiPredicate<String, String> bp2 = String::equals;
    System.out.println(bp2.test("abc", "abc"));
  }
  
  /**
   * 2  构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
   * 2.1  类名 :: new   
   */
  @Test
  public void constructRef(){
    //等同于new Employee();
    Supplier<Employee> sup = Employee::new;
    
    //等同于new Employee(Integer id);
    //或Function<Integer,Employee> fun = x -> new Employee(x);
    Function<Integer,Employee> fun = Employee::new;
    fun.apply(1);
  }
  
  /**
   * 3 数组引用
   * 3.1 类型[] :: new;
   */
  @Test
  public void arrayRef(){
    Function<Integer, String[]> fun = (args) -> new String[args];
    String[] strs = fun.apply(10);
    System.out.println(strs.length);
    
    Function<Integer, Employee[]> fun2 = Employee[] :: new;
    Employee[] emps = fun2.apply(20);
    System.out.println(emps.length);
  }
}

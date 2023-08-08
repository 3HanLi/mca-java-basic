package java.lambda;

import com.wy.mac.java.basic.java8.vo.Employee;
import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * Lambda表达式语法：
 *  1 Java8中引入了一个新的操作符 "->" 该操作符称为箭头操作符或 Lambda 操作符；
 *  
 *  2 箭头操作符将 Lambda 表达式拆分成两部分：
 *    左侧：Lambda 表达式的参数列表
 *    右侧：Lambda 表达式中所需执行的功能， 即 Lambda 体
 *  
 *  3 语法格式：
 *    3.1 无参数，无返回值
 *    3.2 有一个参数，并且无返回值/若只有一个参数，小括号可以省略不写（不建议）
 *    3.3 有两个以上的参数，有返回值，并且 Lambda 体中有多条语句，则Lambda体需要添加{}
 *    3.4 若 Lambda 体中只有一条语句， return 和 大括号都可以省略不写
 *    3.5 Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”（了解）
 * 
 *  4 使用总结：
 *    4.1 Lambda 表达式需要“函数式接口”的支持
 *    4.2 函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。 可以使用注解 @FunctionalInterface 修饰可以检查是否是函数式接口
 * @author WangyongR
 * @date 2018年10月11日 下午10:12:36
 */
public class LambdaGrama {

  /**
   * 3.1 无参数，无返回值
   */
  @Test
  public void noArgsNoReturn(){
    Runnable runnable = () -> System.out.println("wangyongr");
    runnable.run();
  }
  
  /**
   * 3.2 有一个参数，并且无返回值/若只有一个参数，小括号可以省略不写（不建议）
   */
  @Test
  public void oneArgNoReturn(){
    Consumer<String> consumer = (x) -> System.out.println("wangyongr_" + x);
    consumer.accept("beibei");
    Consumer<String> consumer2 = x -> System.out.println("wangyongr_" + x);
    consumer.accept("beibei2");
  }
  
  /**
   * 3.3 有两个以上的参数，有返回值，并且 Lambda 体中有多条语句，则Lambda体需要添加{}
   */
  @Test
  public void moreArgsReturn(){
    Comparator<Employee> comparator = (x, y) -> {
      System.out.println("param:x" + x + ";y:" + y);
      return Integer.compare(x.getAge(),y.getAge());
    };
    int compare = comparator.compare(new Employee(1, 18, "boy"), new Employee(2, 15, "girl"));
    System.out.println(compare);
  }
  
  /**
   * 3.4 若 Lambda 体中只有一条语句， return 和 大括号都可以省略不写
   */
  @Test
  public void moreArgsReturn2(){
    Comparator<Employee> comparator = (x,y) -> Integer.compare(x.getAge(),y.getAge());
    int compare = comparator.compare(new Employee(1, 18, "boy"), new Employee(2, 15, "girl"));
    System.out.println(compare);
  }
  
  /**
   * 3.5 Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”（了解）
   */
  @Test
  public void ignoreParamType(){
    Comparator<Employee> comparator1 = (Employee x,Employee y) -> Integer.compare(x.getAge(),y.getAge());
    Comparator<Employee> comparator2 = (x,y) -> Integer.compare(x.getAge(),y.getAge());
  }
}

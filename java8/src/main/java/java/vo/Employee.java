package java.vo;

import lombok.Data;

import java.util.Objects;

@Data
public class Employee {

  private int id;

  private int age;

  private String name;

  public Employee() {

  }

  public Employee(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public Employee(int id) {
    super();
    this.id = id;
  }

  public Employee(int id, int age, String name) {
    super();
    this.id = id;
    this.age = age;
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Employee employee = (Employee) o;
    return id == employee.id &&
            age == employee.age &&
            name.equals(employee.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, age, name);
  }

  @Override
  public String toString() {
    return "[id:" + id + ",name:" + name + ",age:" + age + "]";
  }

}

package com.liuliang.demo7;

public class Person {
    private String name;
    private int age;
    private int salary;
    private String sex;
    private String area;

    public Person(String name, int age, int salary, String sex, String area) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.sex = sex;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", sex='" + sex + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}

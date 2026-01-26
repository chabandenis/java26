package ru.otus.hw;

public class TestMe {

    public void before(){
        System.out.println("*** Code of before");
    }

    public void after(){
        System.out.println("*** Code of after");
    }


    public void functionOne(){
        System.out.println("*** Code of functionOne");
    }

    public void functionTwo(){
        System.out.println("*** Code of functionOne");
    }

    public void functionThree(){
        System.out.println("*** Code of functionOne");
    }

    @Override
    public String toString() {
        return "TestMe{}";
    }
}

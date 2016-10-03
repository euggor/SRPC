package tests.service;

import java.util.*;

/**
 * A test service class covering several test cases.
 * 
 * @author Yevgeny Go
 *
 */
public class Test {
    public void sleep(Long millis) throws InterruptedException {
        System.out.println("Sleeping ...");
        Thread.sleep(millis.longValue());
        System.out.println("... Sleeping DONE");
    }

    public Date getCurrentDate() {
        return new Date();
    }

    public String getHello() {
        return "Hello world!";
    }

    public String getHello(String str) {
        return "Hello " + str + "!";
    }

    public static String getStaticHello() {
        return "Hello static world!";
    }

    public void getVoid() {
        return;
    }

    public Object getNull() {
        return null;
    }

    public String getAge(String name, int age) {
        return name + ", " + age + " years old";
    }

    public String getException() throws Exception {
        throw new Exception("I'm throwing exception");
    }

    public String toString() {
        return "Service 'Test'";
    }
}

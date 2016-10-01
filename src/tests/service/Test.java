package tests.service;

import java.util.*;

/**
 * A test service class covering several test cases.
 * @author Yevgeny Go
 *
 */
public class Test {
    public void sleep(Long millis) throws InterruptedException {
        Thread.sleep(millis.longValue());
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

    public void getVoid() {
        return;
    }

    public String getException() throws Exception {
        throw new Exception("I'm throwing exception");
    }

    public String toString() {
        return "Service 'Test'";
    }
}

package tests.service;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A test service class covering several test cases.
 * 
 * @author Yevgeny Go
 *
 */
public class Test {
    private static Logger logger = LogManager.getLogger(Test.class);

    public void sleep(Long millis) throws InterruptedException {
        logger.debug("Sleeping for " + millis + " millis");
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

    public String getHello(String from, String to) {
        return "Hello from " + from + "to " + to + "!";
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

    @Override
    public String toString() {
        return "Service 'Test'";
    }
}

package service;

/**
 * A simple self-checking service echoing a received string back to the client
 * 
 * @author Yevgeny Go
 *
 */
public class Echoer {
    public String echo(String str) {
        return str;
    }

    public String toString() {
        return "Service 'Echoer'";
    }
}

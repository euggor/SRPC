/**
 * 
 */
package tests.client;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import client.Client;
import exception.SRPCClientException;

/**
 * JUnit test for remote call checks
 * 
 * @author Yevgeny Go
 *
 */
public class CallCheckerTest {
    private Client client;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        client = new Client("localhost", 2222);
    }

    @Test
    public void checkService() {
        String testStr = "Hi there!";
        
        try {
            String result = (String) client.remoteCall("echo", "echo",
                new Object[] {testStr});
            assertEquals(result, testStr);
        } catch (SRPCClientException e) {
            fail("Proper remote call to 'echo' failed");
        }
    }

    @Test
    public void properSleepCall() {
        try {
            client.remoteCall("test", "sleep", new Object[] {new Long(1000)});
        } catch (SRPCClientException e) {
            fail("Proper remote call to sleep() failed");
        }
    }

    @Test
    public void properDateCall() {
        try {
            client.remoteCall("test", "getCurrentDate", new Object[] {});
        } catch (SRPCClientException e) {
            fail("Proper remote call to getCurrentDate() failed");
        }
    }

    @Test
    public void wrongDateCallWrongParameters() {
        try {
            client.remoteCall("test", "getCurrentDate", new Object[] {"19", "73"});
            fail("Wrong remote call with wrong parameters passed");
        } catch (SRPCClientException e) {
            return;
        }
    }

    @Test
    public void wrongSleepCallEmptyParameters() {
        try {
            client.remoteCall("test", "sleep", new Object[] {});
            fail("Wrong remote call 'sleep' with empty parameters passed");
        } catch (SRPCClientException e) {
            return;
        }
    }

    @Test
    @Ignore
    public void wrongSleepCallWrongParameters() {
        try {
            client.remoteCall("test", "sleep", new Boolean[] {new Boolean(false)});
            fail("Wrong remote call 'sleep' with wrong parameters passed");
        } catch (SRPCClientException e) {
            return;
        }
    }

    @Test
    public void wrongCallUnexistingService() {
        try {
            client.remoteCall("test1", "getCurrentDate", new Object[] {});
            fail("Wrong remote call to unexisting service passed");
        } catch (SRPCClientException e) {
            return;
        }
    }

    @Test
    public void wrongCallUnexistingMethod() {
        try {
            client.remoteCall("test", "getCurrentDate1", new Object[] {});
            fail("Wrong remote call to unexisting method 'getCurrentDate' passed");
        } catch (SRPCClientException e) {
            return;
        }
    }

    @Test
    public void checkVoidCall() {
        try {
            assertNull(client.remoteCall("test", "getVoid", new Object[] {}));
        } catch (SRPCClientException e) {
            fail("Proper remote call to 'getVoid' failed");
        }
    }

    @Test
    public void checkNullCall() {
        try {
            assertNull(client.remoteCall("test", "getNull", new Object[] {}));
        } catch (SRPCClientException e) {
            fail("Proper remote call to 'getNull' failed");
        }
    }
}

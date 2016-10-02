/**
 * 
 */
package tests.client;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import client.Client;
import exception.SRPCClientException;

/**
 * @author Yevgeny Go
 *
 */
public class WrongCall {
    private Client client;
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        client = new Client("localhost", 2222);
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
            client.remoteCall("test", "getCurrentDate", new Object[]{});
        } catch (SRPCClientException e) {
            fail("Proper remote call to getCurrentDate() failed");
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
    public void wrongSleepCallWrongParameters() {
        try {
            client.remoteCall("test", "sleep", new Object[] {"WRONG"});
            fail("Wrong remote call 'sleep' with wrong parameters passed");
        } catch (SRPCClientException e) {
            return;
        }
    }

    @Test
    public void wrongCallUnexistingService() {
        try {
            client.remoteCall("test1", "getCurrentDate", new Object[]{});
            fail("Wrong remote call to unexisting service passed");
        } catch (SRPCClientException e) {
            return;
        }
    }

    @Test
    public void wrongCallUnxistingMethod() {
        try {
            client.remoteCall("test", "getCurrentDate1", new Object[]{});
            fail("Wrong remote call to unexisting method 'getCurrentDate' passed");
        } catch (SRPCClientException e) {
            return;
        }
    }
}

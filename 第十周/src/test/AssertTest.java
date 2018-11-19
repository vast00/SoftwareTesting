package test;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.xml.ws.RequestWrapper;

public class AssertTest {

    @Test
    public void assertTrue_and_assertFalse_sample() throws Exception {

        Assert.assertTrue(true);
        Assert.assertTrue(false);
    }

    @Test
    public void assertNull_and_assertNotNull_sample() throws Exception {
        Object myObject = null;
        Assert.assertNull(myObject);

        myObject = new String("Some value");
        Assert.assertNotNull(myObject);
    }

    @Test
    public void assertEquals_sample() throws Exception {
        Integer i = new Integer("5");
        Integer j = new Integer("5");

        Assert.assertEquals(i, j);
    }

    @Test
    public void assertNotSame_sample() throws Exception {
        Integer i = new Integer("5");
        Integer j = new Integer("5");

        Assert.assertNotSame(i, j);
    }

    @Test
    public void assertSame_sample() throws Exception {
        Integer i = new Integer("5");
        Integer j = i;

        Assert.assertSame(i, j);
    }

    @Test
    @Ignore ("Happy vocation")
    public void This_test_is_intented_ignore_when_we_are_on_vocation() {
        System.out.println("You should not see this message!");
    }
}
package test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ParameterizedParamTest {

    @Parameterized.Parameter(value = 0)
    public int number;

    @Parameterized.Parameter(value = 1)
    public int expectedResult;

    @Parameterized.Parameters(name = "{index}: factorial({0}) = {1}")
    public static Collection<Object[]> factorialData() {
        return Arrays.asList(new Object[][] {{0,1}, {1,1}, {2,2}, {3,6}, {4,24}, {5,120}, {6,720}});
    }

    @Test
    public void factorial() throws Exception {

        Factorial f = new Factorial();
        Assert.assertEquals(f.factorial(number), expectedResult);
    }
}
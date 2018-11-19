package test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ParameterizedTest {

    private int number;
    private int expectedResult;

    @Parameterized.Parameters
    public static Collection<Object[]> factorialData() {
        return Arrays.asList(new Object[][] {{0,1}, {1,1}, {2,2}, {3,6}, {4,24}, {5,120}, {6,720}});
    }

    public ParameterizedTest(int input, int expected) {
        number = input;
        expectedResult = expected;
    }

    @Test
    public void This_is_a_parameterized_test() throws Exception {
        Factorial f = new Factorial();
        Assert.assertEquals(f.factorial(number), expectedResult);
    }
}
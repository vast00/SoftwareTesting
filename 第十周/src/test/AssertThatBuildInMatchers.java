package test;

import org.junit.Test;

import static org.junit.Assert.*;
import static test.LessThanOrEqual.lessThanOrEqual;


public class AssertThatBuildInMatchers {

    @Test
    public void lessthanOrEquals_custom_matcher() throws Exception {

        int actualage = 42;
        assertThat(actualage, lessThanOrEqual(45));
        assertThat(actualage, lessThanOrEqual(42));

        double actualPI = 3.14;
        assertThat(actualPI, lessThanOrEqual(3.1415));
        assertThat(actualPI, lessThanOrEqual(3.14));

        String actualName = "Sun";
        assertThat(actualName, lessThanOrEqual("Ying"));
        assertThat(actualName, lessThanOrEqual("Sun"));

    }

    @Test
    public void compare_with_Maximum_Value() throws Exception {
        int actual_max = Integer.MAX_VALUE + 1;
        assertThat(actual_max, lessThanOrEqual(Integer.MAX_VALUE));
    }

    @Test
    public void compare_with_Minimal_Value() throws Exception {
        int actual_min = Integer.MIN_VALUE + 1;
        assertThat(actual_min, lessThanOrEqual(Integer.MIN_VALUE));
    }
}
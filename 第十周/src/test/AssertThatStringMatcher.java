package test;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AssertThatStringMatcher {

    @Test
    public void verify_Strings() throws Exception {

        String name = "Sun Hai Ying";

        assertThat(name, startsWith("Su"));
        assertThat(name, endsWith("ng"));
        assertThat(name, containsString(" Hai"));
    }
}
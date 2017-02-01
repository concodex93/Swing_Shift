package com.example.conor.SwingShift;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
//public class ApplicationTest extends ApplicationTestCase<Application> {
//
//    public ApplicationTest() {
//        super(Application.class);
//    }
//}

public class ApplicationTest {

    @Test
    public void testConvertFahrenheitToCelsius() {
        float actual = 1;
        // expected value is 212
        float expected = 1;
        // use this method because float is not precise
        assertEquals(expected,
                actual);
    }
}
package com.example.conor.workingvolley;

import android.test.InstrumentationTestCase;
import com.example.conor.SwingShift.Login.Employee;
import com.example.conor.SwingShift.Login.LoginFragment;
import com.example.conor.SwingShift.ShiftActivity;
import com.example.conor.SwingShift.Shift;
import com.example.conor.SwingShift.Swap;


/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */

public class ExampleUnitTest extends InstrumentationTestCase {

    ShiftActivity mainActivity;
    LoginFragment loginFragment;

    public void test_addition_isCorrect() throws Exception {
        assertEquals(4, 3);
    }

    public void test() throws Exception {
        final int expected = 1;
        final int reality = 5;
        assertEquals(expected, reality);
    }

    // Main Activity Tests
    public void test_getShift() throws Exception {
        mainActivity = new ShiftActivity();
        Shift test = mainActivity.getShift();
        Shift shift = new Shift();
        assertEquals(test, shift);
    }

    public void test_getSwapData() throws Exception{
        mainActivity = new ShiftActivity();
        Swap test = mainActivity.getSwapData();
        Swap swap = new Swap();
        assertEquals(test, swap);
    }

    public void test_ConvertSwapToShift() throws Exception{
        mainActivity = new ShiftActivity();
        Swap testSwap = new Swap();
        Shift test = mainActivity.ConvertSwapToShift(testSwap);
        Shift shift = new Shift();
        assertEquals(test, shift);
    }

    //Login Test
    public void test_checkEmployee() throws Exception {
        loginFragment = new LoginFragment();
        Employee test = loginFragment.CheckEmployeeID("1");
        Employee employee = new Employee();
        assertEquals(test, employee);
    }





}
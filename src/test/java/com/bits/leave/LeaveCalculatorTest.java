package com.bits.leave;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class LeaveCalculatorTest {

    LeaveCalculator calculator = new LeaveCalculator();

    // Test 1: Verify remaining leave calculation
    @Test
    public void testRemainingLeave() {

        int result = calculator.calculateRemainingLeave(30, 8);

        assertEquals(22, result);
    }

    // Test 2: Verify leave encashment calculation
    @Test
    public void testLeaveEncashment() {

        double result = calculator.calculateEncashment(30000, 5);

        assertEquals(5000.0, result, 0.01);
    }

    // Test 3: Verify encashment when no leave is available
    @Test
    public void testZeroLeaveEncashment() {

        double result = calculator.calculateEncashment(30000, 0);

        assertEquals(0.0, result, 0.01);
    }
}

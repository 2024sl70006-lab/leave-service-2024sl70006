package com.bits.leave;
// BITS ID: 2024sl70006
public class LeaveCalculator {

    // Calculates the employee's remaining leave balance
    public int calculateRemainingLeave(int totalLeave, int leaveTaken) {

        if (totalLeave < 0 || leaveTaken < 0) {
            throw new IllegalArgumentException("Leave values cannot be negative");
        }

        if (leaveTaken > totalLeave) {
            throw new IllegalArgumentException("Leave taken cannot exceed allocated leave");
        }

        return totalLeave - leaveTaken;
    }

    // Calculates leave encashment amount
    public double calculateEncashment(double monthlySalary, int encashableLeaveDays) {

        if (monthlySalary < 0 || encashableLeaveDays < 0) {
            throw new IllegalArgumentException("Salary and leave days cannot be negative");
        }

        double dailySalary = monthlySalary / 30.0;

        return dailySalary * encashableLeaveDays;
    }
}

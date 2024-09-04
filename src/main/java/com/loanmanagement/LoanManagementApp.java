package com.loanmanagement;

import com.loanmanagement.ui.ConsoleUI;

public class LoanManagementApp {
    public static void main(String[] args) {
        try {
            ConsoleUI ui = new ConsoleUI();
            ui.start();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
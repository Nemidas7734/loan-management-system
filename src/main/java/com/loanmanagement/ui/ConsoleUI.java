package com.loanmanagement.ui;

import com.loanmanagement.LoanManagementException;
import com.loanmanagement.model.Loan;
import com.loanmanagement.model.Payment;
import com.loanmanagement.service.LoanService;
import com.loanmanagement.service.PaymentService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI implements AutoCloseable {
    private final Scanner scanner;
    private final LoanService loanService;
    private final PaymentService paymentService;
    private final DateTimeFormatter dateFormatter;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.loanService = new LoanService();
        this.paymentService = new PaymentService();
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            printMainMenu();
            int choice = getIntInput("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        handleLoanManagement();
                        break;
                    case 2:
                        handlePaymentManagement();
                        break;
                    case 3:
                        exit = true;
                        System.out.println("Thank you for using the Loan Management System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (LoanManagementException | SQLException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n--- Loan Management System ---");
        System.out.println("1. Loan Management");
        System.out.println("2. Payment Management");
        System.out.println("3. Exit");
    }

    private void handleLoanManagement() throws LoanManagementException, SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Loan Management ---");
            System.out.println("1. Add new loan");
            System.out.println("2. View loan details");
            System.out.println("3. Update loan information");
            System.out.println("4. Remove loan");
            System.out.println("5. View all loans");
            System.out.println("6. Back to main menu");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addNewLoan();
                    break;
                case 2:
                    viewLoanDetails();
                    break;
                case 3:
                    updateLoanInformation();
                    break;
                case 4:
                    removeLoan();
                    break;
                case 5:
                    viewAllLoans();
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handlePaymentManagement() throws SQLException, LoanManagementException {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Payment Management ---");
            System.out.println("1. Record loan payment");
            System.out.println("2. View payment history for a loan");
            System.out.println("3. Back to main menu");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    recordLoanPayment();
                    break;
                case 2:
                    viewPaymentHistory();
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addNewLoan() throws SQLException, LoanManagementException {
        System.out.println("\n--- Add New Loan ---");
        int customerId = getIntInput("Enter customer ID: ");
        BigDecimal loanAmount = getBigDecimalInput("Enter loan amount: ");
        BigDecimal interestRate = getBigDecimalInput("Enter interest rate: ");
        LocalDate startDate = getDateInput("Enter start date (yyyy-MM-dd): ");
        LocalDate endDate = getDateInput("Enter end date (yyyy-MM-dd): ");
        String status = getStringInput("Enter loan status (Active/Closed): ");

        Loan loan = new Loan(0, customerId, loanAmount, interestRate, startDate, endDate, status);
        loanService.addLoan(loan);
        System.out.println("Loan added successfully with ID: " + loan.getLoanId());
    }

    private void viewLoanDetails() throws SQLException, LoanManagementException {
        int loanId = getIntInput("Enter loan ID: ");
        Loan loan = loanService.getLoanById(loanId);
        if (loan != null) {
            System.out.println(loan);
        } else {
            System.out.println("Loan not found.");
        }
    }

    private void updateLoanInformation() throws SQLException, LoanManagementException {
        int loanId = getIntInput("Enter loan ID to update: ");
        Loan loan = loanService.getLoanById(loanId);
        if (loan != null) {
            System.out.println("Current loan details: " + loan);
            BigDecimal newAmount = getBigDecimalInput("Enter new loan amount (press enter to skip): ");
            if (newAmount != null) {
                loan.setLoanAmount(newAmount);
            }
            String newStatus = getStringInput("Enter new status (Active/Closed) (press enter to skip): ");
            if (!newStatus.isEmpty()) {
                loan.setStatus(newStatus);
            }
            loanService.updateLoan(loan);
            System.out.println("Loan updated successfully.");
        } else {
            System.out.println("Loan not found.");
        }
    }

    private void removeLoan() throws SQLException, LoanManagementException {
        int loanId = getIntInput("Enter loan ID to remove: ");
        loanService.deleteLoan(loanId);
        System.out.println("Loan removed successfully.");
    }

    private void viewAllLoans() throws SQLException, LoanManagementException {
        List<Loan> loans = loanService.getAllLoans();
        if (loans.isEmpty()) {
            System.out.println("No loans found.");
        } else {
            for (Loan loan : loans) {
                System.out.println(loan);
            }
        }
    }

    private void recordLoanPayment() throws SQLException, LoanManagementException {
        System.out.println("\n--- Record Loan Payment ---");
        int loanId = getIntInput("Enter loan ID: ");
        BigDecimal paymentAmount = getBigDecimalInput("Enter payment amount: ");
        LocalDate paymentDate = getDateInput("Enter payment date (yyyy-MM-dd): ");

        Payment payment = new Payment(0, loanId, paymentAmount, paymentDate);
        paymentService.addPayment(payment);
        System.out.println("Payment recorded successfully with ID: " + payment.getPaymentId());
    }

    private void viewPaymentHistory() throws SQLException, LoanManagementException {
        int loanId = getIntInput("Enter loan ID: ");
        List<Payment> payments = paymentService.getPaymentsByLoanId(loanId);
        if (payments.isEmpty()) {
            System.out.println("No payments found for this loan.");
        } else {
            for (Payment payment : payments) {
                System.out.println(payment);
            }
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }
        }
    }

    private BigDecimal getBigDecimalInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextBigDecimal()) {
                return scanner.nextBigDecimal();
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Consume the invalid input
            }
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    private LocalDate getDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String dateString = scanner.next();
            try {
                return LocalDate.parse(dateString, dateFormatter);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }
    }

    @Override
    public void close() {
        scanner.close();
    }
}
package com.loanmanagement;

public class LoanManagementException extends Exception {
    public LoanManagementException(String message) {
        super(message);
    }

    public LoanManagementException(String message, Throwable cause) {
        super(message, cause);
    }
}
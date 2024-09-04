package com.loanmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Loan {
    private int loanId;
    private int customerId;
    private BigDecimal loanAmount;
    private BigDecimal interestRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    // Constructor
    public Loan(int loanId, int customerId, BigDecimal loanAmount, BigDecimal interestRate,
                LocalDate startDate, LocalDate endDate, String status) {
        this.loanId = loanId;
        this.customerId = customerId;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Getters and setters
    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", customerId=" + customerId +
                ", loanAmount=" + loanAmount +
                ", interestRate=" + interestRate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                '}';
    }
}
package com.loanmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {
    private int paymentId;
    private int loanId;
    private BigDecimal paymentAmount;
    private LocalDate paymentDate;

    // Constructor
    public Payment(int paymentId, int loanId, BigDecimal paymentAmount, LocalDate paymentDate) {
        this.paymentId = paymentId;
        this.loanId = loanId;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
    }

    // Getters and setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", loanId=" + loanId +
                ", paymentAmount=" + paymentAmount +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
package com.loanmanagement.service;

import com.loanmanagement.dao.PaymentDAO;
import com.loanmanagement.model.Loan;
import com.loanmanagement.model.Payment;
import java.sql.SQLException;
import java.util.List;

public class PaymentService {
    private PaymentDAO paymentDAO;

    public PaymentService() {
        this.paymentDAO = new PaymentDAO();
    }

    public void addPayment(Payment payment) throws SQLException {
        paymentDAO.addPayment(payment);
    }

    public Payment getPaymentById(int paymentId) throws SQLException {
        return paymentDAO.getPaymentById(paymentId);
    }

    public List<Payment> getPaymentsByLoanId(int loanId) throws SQLException {
        return paymentDAO.getPaymentsByLoanId(loanId);
    }

    public void updatePayment(Payment payment) throws SQLException {
        paymentDAO.updatePayment(payment);
    }

    public void deletePayment(int paymentId) throws SQLException {
        paymentDAO.deletePayment(paymentId);
    }

    // Additional business logic methods can be added here
    public boolean isPaymentValid(Payment payment) {
        // Implement payment validation logic
        return true; // Placeholder return
    }

    public void applyPaymentToLoan(Payment payment, Loan loan) {
        // Implement logic to apply payment to loan balance
    }
}
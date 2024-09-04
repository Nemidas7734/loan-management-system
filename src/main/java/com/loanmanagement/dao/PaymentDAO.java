package com.loanmanagement.dao;

import com.loanmanagement.model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    
    public void addPayment(Payment payment) throws SQLException {
        String sql = "INSERT INTO Payment (loan_id, payment_amount, payment_date) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, payment.getLoanId());
            pstmt.setBigDecimal(2, payment.getPaymentAmount());
            pstmt.setDate(3, Date.valueOf(payment.getPaymentDate()));
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating payment failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    payment.setPaymentId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating payment failed, no ID obtained.");
                }
            }
        }
    }
    
    public Payment getPaymentById(int paymentId) throws SQLException {
        String sql = "SELECT * FROM Payment WHERE payment_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, paymentId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractPaymentFromResultSet(rs);
                }
            }
        }
        return null;
    }
    
    public List<Payment> getPaymentsByLoanId(int loanId) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payment WHERE loan_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, loanId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    payments.add(extractPaymentFromResultSet(rs));
                }
            }
        }
        return payments;
    }
    
    public void updatePayment(Payment payment) throws SQLException {
        String sql = "UPDATE Payment SET loan_id = ?, payment_amount = ?, payment_date = ? WHERE payment_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, payment.getLoanId());
            pstmt.setBigDecimal(2, payment.getPaymentAmount());
            pstmt.setDate(3, Date.valueOf(payment.getPaymentDate()));
            pstmt.setInt(4, payment.getPaymentId());
            
            pstmt.executeUpdate();
        }
    }
    
    public void deletePayment(int paymentId) throws SQLException {
        String sql = "DELETE FROM Payment WHERE payment_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, paymentId);
            pstmt.executeUpdate();
        }
    }
    
    private Payment extractPaymentFromResultSet(ResultSet rs) throws SQLException {
        return new Payment(
            rs.getInt("payment_id"),
            rs.getInt("loan_id"),
            rs.getBigDecimal("payment_amount"),
            rs.getDate("payment_date").toLocalDate()
        );
    }
}
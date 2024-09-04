package com.loanmanagement.dao;

import com.loanmanagement.model.Loan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    
    public void addLoan(Loan loan) throws SQLException {
        String sql = "INSERT INTO Loan (customer_id, loan_amount, interest_rate, start_date, end_date, status) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, loan.getCustomerId());
            pstmt.setBigDecimal(2, loan.getLoanAmount());
            pstmt.setBigDecimal(3, loan.getInterestRate());
            pstmt.setDate(4, Date.valueOf(loan.getStartDate()));
            pstmt.setDate(5, Date.valueOf(loan.getEndDate()));
            pstmt.setString(6, loan.getStatus());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating loan failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    loan.setLoanId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating loan failed, no ID obtained.");
                }
            }
        }
    }
    
    public Loan getLoanById(int loanId) throws SQLException {
        String sql = "SELECT * FROM Loan WHERE loan_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, loanId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractLoanFromResultSet(rs);
                }
            }
        }
        return null;
    }
    
    public List<Loan> getAllLoans() throws SQLException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM Loan";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                loans.add(extractLoanFromResultSet(rs));
            }
        }
        return loans;
    }
    
    public void updateLoan(Loan loan) throws SQLException {
        String sql = "UPDATE Loan SET customer_id = ?, loan_amount = ?, interest_rate = ?, start_date = ?, end_date = ?, status = ? WHERE loan_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, loan.getCustomerId());
            pstmt.setBigDecimal(2, loan.getLoanAmount());
            pstmt.setBigDecimal(3, loan.getInterestRate());
            pstmt.setDate(4, Date.valueOf(loan.getStartDate()));
            pstmt.setDate(5, Date.valueOf(loan.getEndDate()));
            pstmt.setString(6, loan.getStatus());
            pstmt.setInt(7, loan.getLoanId());
            
            pstmt.executeUpdate();
        }
    }
    
    public void deleteLoan(int loanId) throws SQLException {
        String sql = "DELETE FROM Loan WHERE loan_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, loanId);
            pstmt.executeUpdate();
        }
    }
    
    private Loan extractLoanFromResultSet(ResultSet rs) throws SQLException {
        return new Loan(
            rs.getInt("loan_id"),
            rs.getInt("customer_id"),
            rs.getBigDecimal("loan_amount"),
            rs.getBigDecimal("interest_rate"),
            rs.getDate("start_date").toLocalDate(),
            rs.getDate("end_date").toLocalDate(),
            rs.getString("status")
        );
    }
}
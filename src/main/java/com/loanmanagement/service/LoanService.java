package com.loanmanagement.service;

import com.loanmanagement.LoanManagementException;
import com.loanmanagement.dao.LoanDAO;
import com.loanmanagement.model.Loan;
import java.sql.SQLException;
import java.util.List;

public class LoanService {
    private LoanDAO loanDAO;

    public LoanService() {
        this.loanDAO = new LoanDAO();
    }

    public void addLoan(Loan loan) throws LoanManagementException {
        try {
            loanDAO.addLoan(loan);
        } catch (SQLException e) {
            throw new LoanManagementException("Error adding loan", e);
        }
    }

    public Loan getLoanById(int loanId) throws LoanManagementException {
        try {
            return loanDAO.getLoanById(loanId);
        } catch (SQLException e) {
            throw new LoanManagementException("Error retrieving loan", e);
        }
    }

    // Update other methods similarly...

    public void calculateInterest(Loan loan) {
        // Implement interest calculation logic
    }

    public boolean isLoanEligible(Loan loan) {
        // Implement loan eligibility check logic
        return true; // Placeholder return
    }

	public void deleteLoan(int loanId) {
		// TODO Auto-generated method stub
		
	}

	public void updateLoan(Loan loan) {
		// TODO Auto-generated method stub
		
	}

	public List<Loan> getAllLoans() {
		// TODO Auto-generated method stub
		return null;
	}
}
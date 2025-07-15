package service;

import dao.ExpenseDAO;
import interfaces.Filterable;
import model.Expense;
import util.FileLogger;
import exceptions.InvalidExpenseException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpenseService implements Filterable {
    private ExpenseDAO dao;

    public ExpenseService() {
        dao = new ExpenseDAO();
    }

    public void addExpense(String category, double amount, LocalDate date, String description) throws InvalidExpenseException {
        if (amount <= 0) {
            throw new InvalidExpenseException("Amount must be greater than 0.");
        }
        Expense expense = new Expense(category, amount, date, description);
        dao.addExpense(expense);
        FileLogger.log("Added expense: " + expense);
    }

    public List<Expense> getAllExpenses() {
        return dao.getAllExpenses();
    }

    public double getTotalExpenses() {
        return getAllExpenses().stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public Map<String, List<Expense>> groupByCategory() {
        return getAllExpenses().stream()
                .collect(Collectors.groupingBy(Expense::getCategory));
    }

    @Override
    public List<Expense> filterByCategory(String category) {
        return getAllExpenses().stream()
                .filter(e -> e.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // ✅ New: Add a category
    public void addCategory(String name) {
        dao.addCategory(name);
    }

    // ✅ New: View all categories
    public List<String> getAllCategories() {
        return dao.getAllCategories();
    }
}

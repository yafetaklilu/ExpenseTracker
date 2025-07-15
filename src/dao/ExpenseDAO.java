package dao;

import model.Expense;
import util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
    public ExpenseDAO() {
        createTableIfNotExists();
        createCategoriesTable(); // ✅ also create categories table
    }

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS expenses (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "category TEXT," +
                     "amount REAL," +
                     "date TEXT," +
                     "description TEXT)";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public void createCategoriesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS categories (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "name TEXT UNIQUE NOT NULL)";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            System.out.println("Error creating categories table: " + e.getMessage());
        }
    }

    public void addExpense(Expense expense) {
        String sql = "INSERT INTO expenses (category, amount, date, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, expense.getCategory());
            stmt.setDouble(2, expense.getAmount());
            stmt.setString(3, expense.getDate().toString());
            stmt.setString(4, expense.getDescription());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error adding expense: " + e.getMessage());
        }
    }

    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Expense e = new Expense(
                    rs.getInt("id"),
                    rs.getString("category"),
                    rs.getDouble("amount"),
                    LocalDate.parse(rs.getString("date")),
                    rs.getString("description")
                );
                expenses.add(e);
            }
        } catch (Exception e) {
            System.out.println("Error fetching expenses: " + e.getMessage());
        }
        return expenses;
    }

    public void addCategory(String name) {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error adding category: " + e.getMessage());
        }
    }

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT name FROM categories";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                categories.add(rs.getString("name"));
            }
        } catch (Exception e) {
            System.out.println("Error fetching categories: " + e.getMessage());
        }
        return categories;
    }
}

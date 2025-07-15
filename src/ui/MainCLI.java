package ui;

import service.ExpenseService;
import model.Expense;
import exceptions.InvalidExpenseException;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainCLI {
    private ExpenseService service;
    private Scanner scanner;

    public MainCLI() {
        service = new ExpenseService();
        scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;
        do {
            printMenu();
            choice = getIntInput("Choose an option: ");

            switch (choice) {
                case 1 -> addExpense();
                case 2 -> viewAll();
                case 3 -> filterByCategory();
                case 4 -> showTotal();
                case 5 -> addCategory();
                case 6 -> viewCategories();
                case 0 -> System.out.println("Exiting... Goodbye!");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private void printMenu() {
        System.out.println("\n=== Expense Tracker Menu ===");
        System.out.println("1. Add Expense");
        System.out.println("2. View All Expenses");
        System.out.println("3. Filter by Category");
        System.out.println("4. Show Total Expenses");
        System.out.println("5. Add Category");
        System.out.println("6. View All Categories");
        System.out.println("0. Exit");
    }

    private void addExpense() {
        System.out.print("Category: ");
        String category = scanner.nextLine();

        double amount = getDoubleInput("Amount: ");

        System.out.print("Date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateStr);

        System.out.print("Description: ");
        String desc = scanner.nextLine();

        try {
            service.addExpense(category, amount, date, desc);
            System.out.println("Expense added successfully!");
        } catch (InvalidExpenseException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }

    private void viewAll() {
        List<Expense> all = service.getAllExpenses();
        System.out.println("\n--- All Expenses ---");
        all.forEach(System.out::println);
    }

    private void filterByCategory() {
        System.out.print("Enter category to filter: ");
        String cat = scanner.nextLine();
        List<Expense> filtered = service.filterByCategory(cat);
        System.out.println("\n--- Expenses in category: " + cat + " ---");
        filtered.forEach(System.out::println);
    }

    private void showTotal() {
        double total = service.getTotalExpenses();
        System.out.println("Total Expenses: " + total + " ETB");
    }

    private void addCategory() {
        System.out.print("Enter new category name: ");
        String name = scanner.nextLine();
        service.addCategory(name);
        System.out.println("Category added.");
    }

    private void viewCategories() {
        List<String> categories = service.getAllCategories();
        System.out.println("\n--- All Categories ---");
        categories.forEach(System.out::println);
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine());
    }

    private double getDoubleInput(String prompt) {
        System.out.print(prompt);
        return Double.parseDouble(scanner.nextLine());
    }
}

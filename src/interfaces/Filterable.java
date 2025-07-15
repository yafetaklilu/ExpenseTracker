package interfaces;

import model.Expense;
import java.util.List;

public interface Filterable {
    List<Expense> filterByCategory(String category);
}

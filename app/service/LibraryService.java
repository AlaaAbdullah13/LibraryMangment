package app.service;

import app.model.LibraryItem;
import app.model.Book;
import app.model.Magazine;
import app.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibraryService {
    private List<LibraryItem> items = new ArrayList<>();

    public void addBook(Book book) {
        if (!Validator.isValidText(book.getTitle())) throw new IllegalArgumentException("Invalid Book Title!");
        if (!Validator.isValidName(book.getAuthor())) throw new IllegalArgumentException("Invalid Author Name!");
        if (book.getStock() <= 0) throw new IllegalArgumentException("Stock must be positive!");
        checkDuplicateId(book.getId());
        items.add(book);
    }

    public void addMagazine(Magazine mag) {
        if (!Validator.isValidText(mag.getTitle())) throw new IllegalArgumentException("Invalid Magazine Title!");
        if (mag.getStock() <= 0) throw new IllegalArgumentException("Stock must be positive!");
        checkDuplicateId(mag.getId());
        items.add(mag);
    }

    private void checkDuplicateId(int id) {
        boolean exists = items.stream().anyMatch(i -> i.getId() == id);
        if (exists) throw new IllegalArgumentException("Item ID already exists!");
    }

    public Optional<LibraryItem> getItemById(int id) {
        return items.stream().filter(i -> i.getId() == id).findFirst();
    }

    public List<LibraryItem> getAllItems() {
        return items;
    }

    public void deleteItem(int id) {
        items.removeIf(i -> i.getId() == id);
    }
}

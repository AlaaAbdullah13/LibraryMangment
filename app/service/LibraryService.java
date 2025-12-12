package app.service;

import app.model.*;
import app.exceptions.*;
import java.util.*;

public class LibraryService {
    private final List<LibraryItem> items = new ArrayList<>();

    private void checkDuplicateId(int id) {
        boolean exists = items.stream().anyMatch(i -> i.getId() == id);
        if (exists) throw new IllegalArgumentException("Item ID already exists!");
    }

    public void addBook(Book book) {
        checkDuplicateId(book.getId());
        items.add(book);
    }

    public void addMagazine(Magazine mag) {
        checkDuplicateId(mag.getId());
        items.add(mag);
    }

    public LibraryItem getItemById(int id) throws ItemNotFoundException {
        return items.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Item with ID " + id + " not found!"));
    }

    public List<LibraryItem> getAllItems() {
        return new ArrayList<>(items);
    }

    public void deleteItem(int id) throws ItemNotFoundException {
        boolean removed = items.removeIf(i -> i.getId() == id);
        if (!removed) throw new ItemNotFoundException("Item with ID " + id + " not found!");
    }
}

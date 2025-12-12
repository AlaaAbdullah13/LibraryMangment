package app.model;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private int id;
    private String name;
    private String email;
    private List<LibraryItem> borrowedItems;

    public Client(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.borrowedItems = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<LibraryItem> getBorrowedItems() { return borrowedItems; }

    public String getClientDetails() {
        return "Client [ID=" + id + ", Name=" + name + ", Email=" + email + "]";
    }
}

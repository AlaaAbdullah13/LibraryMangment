package app.model;

public abstract class LibraryItem {
    private int id;
    private String title;
    private int stock;

    public LibraryItem(int id, String title, int stock) {
        this.id = id;
        this.title = title;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public abstract String getItemDetails();
}

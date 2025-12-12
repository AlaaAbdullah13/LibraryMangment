package app;

import app.model.Book;
import app.model.Magazine;
import app.model.Client;
import app.model.LibraryItem;
import app.service.LibraryService;
import app.service.ClientService;
import app.util.Validator;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static LibraryService libraryService = new LibraryService();
    private static ClientService clientService = new ClientService();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Add Magazine");
            System.out.println("3. Display Library Items");
            System.out.println("4. Add Client");
            System.out.println("5. Display Clients");
            System.out.println("6. Borrow Item");
            System.out.println("7. Return Item");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": addBook(); break;
                case "2": addMagazine(); break;
                case "3": displayItems(); break;
                case "4": addClient(); break;
                case "5": displayClients(); break;
                case "6": borrowItem(); break;
                case "7": returnItem(); break;
                case "0": System.out.println("Exiting..."); System.exit(0); break;
                default: System.out.println("Invalid option, try again!");
            }
        }
    }

    private static void addBook() {
        int id = readPositiveInt("Enter Book ID: ");
        String title = readValidText("Enter Book Title: ");
        String author = readValidName("Enter Author Name: ");
        int stock = readPositiveInt("Enter Stock: ");

        Book book = new Book(id, title, author, stock);
        try {
            libraryService.addBook(book);
            System.out.println(" Book added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addMagazine() {
        int id = readPositiveInt("Enter Magazine ID: ");
        String title = readValidText("Enter Magazine Title: ");
        int issue = readPositiveInt("Enter Issue Number: ");
        int stock = readPositiveInt("Enter Stock: ");

        Magazine mag = new Magazine(id, title, issue, stock);
        try {
            libraryService.addMagazine(mag);
            System.out.println(" Magazine added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void displayItems() {
        List<LibraryItem> items = libraryService.getAllItems();
        if (items.isEmpty()) System.out.println("No items in library.");
        else items.forEach(i -> System.out.println(i.getItemDetails()));
    }

    private static void addClient() {
        int id = readPositiveInt("Enter Client ID: ");
        String name = readValidName("Enter Client Name: ");
        String email = readValidEmail("Enter Client Email: ");

        try {
            clientService.createClient(id, name, email);
            System.out.println("Client added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println( e.getMessage());
        }
    }

    private static void displayClients() {
        List<Client> clients = clientService.getAllClients();
        if (clients.isEmpty()) System.out.println("No clients yet.");
        else clients.forEach(c -> System.out.println(c.getClientDetails()));
    }

    // ---------------------- Borrow/Return ----------------------
    private static void borrowItem() {
        int clientId = readPositiveInt("Enter Client ID: ");
        Client client = clientService.getClientById(clientId);
        if (client == null) { System.out.println(" Client not found!"); return; }

        int itemId = readPositiveInt("Enter Item ID to borrow: ");
        Optional<LibraryItem> itemOpt = libraryService.getItemById(itemId);
        if (itemOpt.isEmpty()) { System.out.println(" Item not found!"); return; }

        LibraryItem item = itemOpt.get();
        if (item.getStock() <= 0) { System.out.println("Item out of stock!"); return; }

        item.setStock(item.getStock() - 1);
        client.getBorrowedItems().add(item);
        System.out.println("Item borrowed successfully!");
    }

    private static void returnItem() {
        int clientId = readPositiveInt("Enter Client ID: ");
        Client client = clientService.getClientById(clientId);
        if (client == null) { System.out.println(" Client not found!"); return; }

        int itemId = readPositiveInt("Enter Item ID to return: ");
        LibraryItem borrowedItem = client.getBorrowedItems().stream()
                .filter(i -> i.getId() == itemId)
                .findFirst().orElse(null);

        if (borrowedItem == null) { System.out.println("This client did not borrow this item!"); return; }

        borrowedItem.setStock(borrowedItem.getStock() + 1);
        client.getBorrowedItems().remove(borrowedItem);
        System.out.println(" Item returned successfully!");
    }

    // ---------------------- Input Helpers ----------------------
    private static int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            if (Validator.isPositiveNumber(input)) return Integer.parseInt(input);
            System.out.println("Invalid input! Enter a positive number.");
        }
    }

    private static String readValidName(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            if (Validator.isValidName(input)) return input;
            System.out.println(" Invalid input! Only letters, minimum 3 characters.");
        }
    }

    private static String readValidText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            if (Validator.isValidText(input)) return input;
            System.out.println(" Invalid input! Use letters, numbers, common symbols.");
        }
    }

    private static String readValidEmail(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            if (Validator.isValidEmail(input)) return input;
            System.out.println(" Invalid email format!");
        }
    }
}

package app;

import app.model.*;
import app.service.*;
import app.exceptions.*;
import app.util.*;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LibraryService libraryService = new LibraryService();
    private static final ClientService clientService = new ClientService();

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            showMenu();
            int choice = readPositiveInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addBook();
                case 2 -> addMagazine();
                case 3 -> displayLibraryItems();
                case 4 -> borrowItem();
                case 5 -> returnItem();
                case 6 -> deleteLibraryItem();
                case 7 -> addClient();
                case 8 -> displayClients();
                case 9 -> deleteClient();
                case 0 -> exit = true;
                default -> System.out.println(" Invalid choice!");
            }
            System.out.println();
        }
        System.out.println("Exiting... Goodbye!");
    }

    private static void showMenu() {
        System.out.println("===== Library Menu =====");
        System.out.println("1. Add Book");
        System.out.println("2. Add Magazine");
        System.out.println("3. Display Library Items");
        System.out.println("4. Borrow Item");
        System.out.println("5. Return Item");
        System.out.println("6. Delete Item");
        System.out.println("7. Add Client");
        System.out.println("8. Display Clients");
        System.out.println("9. Delete Client");
        System.out.println("0. Exit");
    }

    // ---------------- ADD OPERATIONS ----------------
    private static void addBook() {
        int id = readPositiveInt("Enter Book ID: ");
        String title = readValidText("Enter Book Title: ");
        String author = readValidText("Enter Book Author: ");
        int stock = readPositiveInt("Enter number of copies: ");
        try {
            Book book = new Book(id, title, author, stock);
            libraryService.addBook(book);
            System.out.println(" Book added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println( e.getMessage());
        }
    }

    private static void addMagazine() {
        int id = readPositiveInt("Enter Magazine ID: ");
        String title = readValidText("Enter Magazine Title: ");
        int issueNumber = readPositiveInt("Enter Issue Number: ");
        int stock = readPositiveInt("Enter number of copies: ");
        try {
            Magazine mag = new Magazine(id, title, issueNumber, stock);
            libraryService.addMagazine(mag);
            System.out.println(" Magazine added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println( e.getMessage());
        }
    }

    private static void addClient() {
        int id = readPositiveInt("Enter Client ID: ");
        String name = readValidName("Enter Client Name: ");
        String email = readValidEmail("Enter Client Email: ");
        try {
            Client client = new Client(id, name, email);
            clientService.addClient(client);
            System.out.println(" Client added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // ---------------- DISPLAY OPERATIONS ----------------
    private static void displayLibraryItems() {
        System.out.println("----- Library Items -----");
        libraryService.getAllItems().forEach(item -> System.out.println(item.getItemDetails()));
    }

    private static void displayClients() {
        System.out.println("----- Library Clients -----");
        clientService.getAllClients().forEach(client -> System.out.println(client.getClientDetails()));
    }

    // ---------------- BORROW/RETURN ----------------
    private static void borrowItem() {
        int clientId = readPositiveInt("Enter Client ID: ");
        int itemId = readPositiveInt("Enter Item ID to borrow: ");
        try {
            Client client = clientService.getClientById(clientId);
            LibraryItem item = libraryService.getItemById(itemId);
            if (item.getStock() <= 0) {
                System.out.println(" Item not available for borrowing.");
                return;
            }
            item.setStock(item.getStock() - 1);
            client.getBorrowedItems().add(item);
            System.out.println("✅ Item borrowed successfully!");
        } catch (ClientNotFoundException | ItemNotFoundException e) {
            System.out.println( e.getMessage());
        }
    }

    private static void returnItem() {
        int clientId = readPositiveInt("Enter Client ID: ");
        int itemId = readPositiveInt("Enter Item ID to return: ");
        try {
            Client client = clientService.getClientById(clientId);
            LibraryItem itemToReturn = client.getBorrowedItems().stream()
                    .filter(i -> i.getId() == itemId)
                    .findFirst()
                    .orElse(null);
            if (itemToReturn == null) {
                System.out.println("This client did not borrow this item.");
                return;
            }
            client.getBorrowedItems().remove(itemToReturn);
            itemToReturn.setStock(itemToReturn.getStock() + 1);
            System.out.println("Item returned successfully!");
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // ---------------- DELETE OPERATIONS ----------------
    private static void deleteLibraryItem() {
        int id = readPositiveInt("Enter Item ID to delete: ");
        try {
            libraryService.deleteItem(id);
            System.out.println(" Item deleted successfully!");
        } catch (ItemNotFoundException e) {
            System.out.println( e.getMessage());
        }
    }

    private static void deleteClient() {
        int id = readPositiveInt("Enter Client ID to delete: ");
        try {
            clientService.deleteClient(id);
            System.out.println("Client deleted successfully!");
        } catch (ClientNotFoundException e) {
            System.out.println( e.getMessage());
        }
    }

    // ---------------- INPUT VALIDATION HELPERS ----------------
    private static int readPositiveInt(String prompt) {
        int num;
        while (true) {
            System.out.print(prompt);
            try {
                num = Integer.parseInt(scanner.nextLine());
                if (num <= 0) throw new NumberFormatException();
                return num;
            } catch (NumberFormatException e) {
                System.out.println(" Enter a valid positive number!");
            }
        }
    }

    private static String readValidText(String prompt) {
        String text;
        while (true) {
            System.out.print(prompt);
            text = scanner.nextLine();
            if (Validator.isValidText(text)) return text;
            System.out.println(" Invalid input! Only letters, numbers, and common punctuation allowed.");
        }
    }

    private static String readValidName(String prompt) {
        String name;
        while (true) {
            System.out.print(prompt);
            name = scanner.nextLine();
            if (Validator.isValidName(name)) return name;
            System.out.println(" Invalid name! Only letters and spaces allowed (min 3 characters).");
        }
    }

    private static String readValidEmail(String prompt) {
        String email;
        while (true) {
            System.out.print(prompt);
            email = scanner.nextLine();
            if (Validator.isValidEmail(email)) return email;
            System.out.println(" Invalid email format!");
        }
    }
}

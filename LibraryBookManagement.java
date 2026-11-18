
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/*
 * Library Book Management System
 * Data Structure: Singly Linked List
 * Fine: Rs. 10 per day after 7 days
 */

class Book {
    int bookId;
    String title;
    String author;
    boolean issued;
    LocalDate issueDate;
    Book next;

    Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.issued = false;
        this.issueDate = null;
        this.next = null;
    }
}

class BookList {

    private Book head;

    // Add a new book
    public boolean addBook(int id, String title, String author) {

        if (id <= 0 || searchById(id) != null) {
            return false;
        }

        Book newBook = new Book(id, title, author);

        if (head == null) {
            head = newBook;
            return true;
        }

        Book current = head;

        while (current.next != null) {
            current = current.next;
        }

        current.next = newBook;
        return true;
    }

    // Search book by ID
    public Book searchById(int id) {

        Book current = head;

        while (current != null) {
            if (current.bookId == id) {
                return current;
            }

            current = current.next;
        }

        return null;
    }

    // Search book by title
    public void searchByTitle(String keyword) {

        if (keyword.isEmpty()) {
            System.out.println("Please enter a title keyword.");
            return;
        }

        Book current = head;
        boolean found = false;

        while (current != null) {

            if (current.title.toLowerCase()
                    .contains(keyword.toLowerCase())) {

                printBook(current);
                found = true;
            }

            current = current.next;
        }

        if (!found) {
            System.out.println("No matching book found.");
        }
    }

    // Delete book by ID
    public boolean deleteBook(int id) {

        if (head == null) {
            return false;
        }

        if (head.bookId == id) {

            if (head.issued) {
                return false;
            }

            head = head.next;
            return true;
        }

        Book previous = head;
        Book current = head.next;

        while (current != null) {

            if (current.bookId == id) {

                if (current.issued) {
                    return false;
                }

                previous.next = current.next;
                return true;
            }

            previous = current;
            current = current.next;
        }

        return false;
    }

    // Issue book
    public boolean issueBook(int id) {

        Book book = searchById(id);

        if (book == null || book.issued) {
            return false;
        }

        book.issued = true;
        book.issueDate = LocalDate.now();

        System.out.println("\nBook issued successfully!");
        System.out.println("Issue Date: " + book.issueDate);
        System.out.println("Last Return Date: "
                + book.issueDate.plusDays(7));

        return true;
    }

    // Return book and calculate fine
    public boolean returnBook(int id) {

        Book book = searchById(id);

        if (book == null || !book.issued) {
            return false;
        }

        LocalDate returnDate = LocalDate.now();

        long totalDays = ChronoUnit.DAYS.between(
                book.issueDate, returnDate);

        long lateDays = Math.max(0, totalDays - 7);

        long fine = lateDays * 10;

        System.out.println("\n===== BOOK RETURN DETAILS =====");

        System.out.println("Book ID: " + book.bookId);
        System.out.println("Book Title: " + book.title);
        System.out.println("Issue Date: " + book.issueDate);
        System.out.println("Return Date: " + returnDate);
        System.out.println("Total Days: " + totalDays);
        System.out.println("Allowed Days: 7");
        System.out.println("Late Days: " + lateDays);
        System.out.println("Fine Per Day: Rs. 10");
        System.out.println("Total Fine: Rs. " + fine);

        if (fine == 0) {
            System.out.println("Book returned on time. No fine!");
        } else {
            System.out.println("Please pay the late return fine.");
        }

        book.issued = false;
        book.issueDate = null;

        return true;
    }

    // Display all books
    public void displayBooks() {

        if (head == null) {
            System.out.println("Library is empty.");
            return;
        }

        System.out.printf("%-10s %-25s %-25s %-12s%n",
                "Book ID", "Title", "Author", "Status");

        System.out.println(
                "--------------------------------------------------------------------------");

        Book current = head;

        while (current != null) {
            printBook(current);
            current = current.next;
        }
    }

    // Display available books
    public void displayAvailableBooks() {

        Book current = head;
        boolean found = false;

        System.out.println("\n===== AVAILABLE BOOKS =====");

        while (current != null) {

            if (!current.issued) {
                printBook(current);
                found = true;
            }

            current = current.next;
        }

        if (!found) {
            System.out.println("No available books.");
        }
    }

    // Count total books
    public int countBooks() {

        int count = 0;
        Book current = head;

        while (current != null) {
            count++;
            current = current.next;
        }

        return count;
    }

    // Display one book
    private void printBook(Book book) {

        System.out.printf("%-10d %-25s %-25s %-12s%n",
                book.bookId,
                book.title,
                book.author,
                book.issued ? "Issued" : "Available");
    }
}

public class LibraryBookManagement {

    // Read integer safely
    private static int readInt(Scanner sc, String prompt) {

        while (true) {

            System.out.print(prompt);
            String input = sc.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Please enter a valid whole number.");
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookList library = new BookList();

        int choice;

        do {

            System.out.println(
                    "\n===== LIBRARY BOOK MANAGEMENT SYSTEM =====");

            System.out.println("1. Add book");
            System.out.println("2. Display all books");
            System.out.println("3. Search book by ID");
            System.out.println("4. Search book by title");
            System.out.println("5. Issue book");
            System.out.println("6. Return book");
            System.out.println("7. Delete book");
            System.out.println("8. Display available books");
            System.out.println("9. Count total books");
            System.out.println("0. Exit");

            choice = readInt(sc, "Enter your choice: ");

            switch (choice) {

                // Add book
                case 1:

                    int id = readInt(sc, "Enter book ID: ");

                    System.out.print("Enter book title: ");
                    String title = sc.nextLine().trim();

                    System.out.print("Enter author name: ");
                    String author = sc.nextLine().trim();

                    if (title.isEmpty() || author.isEmpty()) {
                        System.out.println(
                                "Title and author cannot be empty.");
                    } else if (library.addBook(id, title, author)) {
                        System.out.println(
                                "Book added successfully!");
                    } else {
                        System.out.println(
                                "Invalid ID or duplicate book ID.");
                    }

                    break;

                // Display all books
                case 2:

                    library.displayBooks();
                    break;

                // Search by ID
                case 3:

                    int searchId = readInt(
                            sc, "Enter book ID to search: ");

                    Book result = library.searchById(searchId);

                    if (result == null) {
                        System.out.println("Book not found.");
                    } else {
                        System.out.println("\nBook Found:");
                        System.out.println("ID: " + result.bookId);
                        System.out.println("Title: " + result.title);
                        System.out.println("Author: " + result.author);
                        System.out.println("Status: "
                                + (result.issued
                                ? "Issued" : "Available"));
                    }

                    break;

                // Search by title
                case 4:

                    System.out.print("Enter title keyword: ");
                    String keyword = sc.nextLine().trim();

                    library.searchByTitle(keyword);
                    break;

                // Issue book
                case 5:

                    int issueId = readInt(
                            sc, "Enter book ID to issue: ");

                    if (!library.issueBook(issueId)) {
                        System.out.println(
                                "Cannot issue: book not found "
                                + "or already issued.");
                    }

                    break;

                // Return book and calculate fine
                case 6:

                    int returnId = readInt(
                            sc, "Enter book ID to return: ");

                    if (!library.returnBook(returnId)) {
                        System.out.println(
                                "Cannot return: book not found "
                                + "or not issued.");
                    }

                    break;

                // Delete book
                case 7:

                    int deleteId = readInt(
                            sc, "Enter book ID to delete: ");

                    System.out.println(
                            library.deleteBook(deleteId)
                            ? "Book deleted successfully!"
                            : "Cannot delete: book not found "
                            + "or currently issued.");

                    break;

                // Display available books
                case 8:

                    library.displayAvailableBooks();
                    break;

                // Count total books
                case 9:

                    System.out.println(
                            "Total books: " + library.countBooks());

                    break;

                // Exit
                case 0:

                    System.out.println(
                            "Thank you for using Library "
                            + "Book Management System.");

                    break;

                default:

                    System.out.println(
                            "Invalid menu choice. Try again.");
            }

        } while (choice != 0);

        sc.close();
    }
}


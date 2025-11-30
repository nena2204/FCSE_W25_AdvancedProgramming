package auditoriski.aud_4;

import java.util.*;

public class Book {
    private String title;
    private String category;
    private float price;

    public Book(String title, String category, float price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
    }
    @Override
    public String toString() {
        return String.format("%s (%s) - %.2f MKD", title, category, price);
    }

    //test main za dali se raboti
    public static void main(String[] args) {
        BookCollection bc = new BookCollection();
        bc.addBook(new Book("Harry Potter", "Fantasy", 450));
        bc.addBook(new Book("The Hobbit", "Fantasy", 350));
        bc.addBook(new Book("Clean Code", "Programming", 500));
        bc.addBook(new Book("Design Patterns", "Programming", 600));
        bc.addBook(new Book("Dune", "SciFi", 300));
        bc.addBook(new Book("Neuromancer", "SciFi", 280));
        bc.addBook(new Book("Starship Troopers", "SciFi", 320));

        System.out.println("=== Knigi po kategorija: Fantasy ===");
        bc.printByCategory("Fantasy");

        System.out.println("\n=== Naevtini 3 knigi ===");
        List<Book> cheapest = bc.getCheapestN(3);
        for (Book b : cheapest) {
            System.out.println(b);
        }
    }
}

    class BookPriceAndTitleComparator implements Comparator<Book> {
        @Override
        public int compare(Book b1, Book b2) {
            if (Float.compare(b1.getPrice(), b2.getPrice()) == 0)
                return b1.getTitle().compareToIgnoreCase(b2.getTitle());
            else
                return Float.compare(b1.getPrice(), b2.getPrice());
        }
    }

    class BookTitleandPriceComparator implements Comparator<Book> {
        @Override
        public int compare(Book b1, Book b2) {
            if (b1.getTitle().compareToIgnoreCase(b2.getTitle()) == 0)
                return Float.compare(b1.getPrice(), b2.getPrice());
            else
                return b1.getTitle().compareToIgnoreCase(b2.getTitle());
        }
    }

    //methods addBook
    class BookCollection {
        private List<Book> books;

        public BookCollection() {
            books = new ArrayList<Book>() {
            };
        }

        //method addbook
        public void addBook(Book book) {
            books.add(book);
        }

        public void printByCategory(String category) {
            List<Book> filteredBooks = new ArrayList<>();
            //listanje na site knigi vo kolekcijata
            for (Book b : books) {
                if (b.getCategory().compareToIgnoreCase(category) == 0) {
                    filteredBooks.add(b);
                }
            }
            filteredBooks.sort(new BookTitleandPriceComparator());

            for (Book b : filteredBooks) {
                System.out.println(b);
            }
        }

        public List getCheapestN(int n) {
            //vrakja najeftini N knigi, ako ima pomalku od N gi vrakja site
            List<Book> cheapest = new ArrayList<Book>();
            books.sort(new BookPriceAndTitleComparator());

            for (int i = 0; i < n; i++) {
                cheapest.add(books.get(i));
            }
            return cheapest;
        }
    }



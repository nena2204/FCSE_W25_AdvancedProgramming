import java.time.LocalDateTime;
import java.util.*;


class User {
    String username;
    List<String> subscribedCategories;
    List<String> subscribedAuthors;
    List<Article> articlesToDisplay;

    public User(String username) {
        this.username = username;
        this.subscribedCategories = new ArrayList<>();
        this.subscribedAuthors = new ArrayList<>();
        this.articlesToDisplay=new ArrayList<>();
    }

    public void subscribeCategory(String cat) {
        subscribedCategories.add(cat);
    }

    public void unsubscribeCat(String cat) {
        subscribedCategories.remove(cat);
    }

    public void subscribeAuthor(String aut) {
        subscribedAuthors.add(aut);
    }

    public void unsubsunsubscribeAuthor(String aut) {
        subscribedAuthors.remove(aut);
    }
}

class NewsSystem {
    List<String> categories;
    List<String> authors;
    Map<String, User> users;
    List<Article> articles;

    public NewsSystem(List<String> categoryNames, List<String> authorNames) {
        this.categories = categoryNames;
        this.authors = authorNames;
        this.users = new HashMap<>();
        this.articles = new ArrayList<>();
    }

    public void addUser(String username) {
        users.put(username, new User(username));
    }

    public void subscribeUserToCategory(String username, String categoryName) {
        users.get(username).subscribedCategories.add(categoryName);
    }

    public void unsubscribeUserFromCategory(String username, String categoryName) {
        users.get(username).subscribedCategories.remove(categoryName);
    }

    public void subscribeUserToAuthor(String username, String authorName) {
        users.get(username).subscribedAuthors.add(authorName);
    }

    public void unsubscribeUserFromAuthor(String username, String authorName) {
        users.get(username).subscribedAuthors.remove(authorName);
    }

    public void publishArticle(Article article) {
        articles.add(article);

        for (User u:users.values()){
            if (u.subscribedCategories.contains(article.getCategory()) || u.subscribedAuthors.contains(article.getAuthor())){
                u.articlesToDisplay.add(article);
            }
        }
    }

    public void printNewsForUser(String username) {
        List<String> authorsByUser = users.get(username).subscribedAuthors;
        List<String> categoriesByUser = users.get(username).subscribedCategories;
        List<Article> result = new ArrayList<>(users.get(username).articlesToDisplay);
        result.sort(Comparator.comparing(Article::getTimestamp));
        System.out.println("News for user: " + username);
        for (Article a : result) {
            System.out.println(a);
        }
    }
}

class Article {

    private final String category;
    private final String author;
    private final String content;
    private final LocalDateTime timestamp;

    public Article(String category, String author, String content, LocalDateTime timestamp) {
        this.category = category;
        this.author = author;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getCategory() {
        return category;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String toString() {
        return String.format("[%s] %s - %s\n%s", timestamp, author, category, content);
    }
}


public class NewsSystemTest {

    public static void main(String[] args) {

        // Hardcoded categories and authors
        List<String> categories = List.of(
                "Technology", "Sports", "Politics", "Health", "Science",
                "Business", "Education", "Culture", "Travel", "Entertainment"
        );

        List<String> authors = List.of(
                "MartinFowler", "JohnDoe", "AliceSmith", "BobBrown", "JaneMiller"
        );

        NewsSystem system = new NewsSystem(categories, authors);

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+", 2);
            String command = parts[0];

            switch (command) {

                case "ADD_USER":
                    system.addUser(parts[1]);
                    break;

                case "SUBSCRIBE_CATEGORY": {
                    String[] p = parts[1].split("\\s+");
                    system.subscribeUserToCategory(p[0], p[1]);
                    break;
                }

                case "UNSUBSCRIBE_CATEGORY": {
                    String[] p = parts[1].split("\\s+");
                    system.unsubscribeUserFromCategory(p[0], p[1]);
                    break;
                }

                case "SUBSCRIBE_AUTHOR": {
                    String[] p = parts[1].split("\\s+");
                    system.subscribeUserToAuthor(p[0], p[1]);
                    break;
                }

                case "UNSUBSCRIBE_AUTHOR": {
                    String[] p = parts[1].split("\\s+");
                    system.unsubscribeUserFromAuthor(p[0], p[1]);
                    break;
                }

                case "PUBLISH": {
                    // format:
                    // PUBLISH <category> <author> <timestamp> <content>
                    String[] p = parts[1].split("\\s+", 4);
                    Article article = new Article(
                            p[0],
                            p[1],
                            p[3],
                            LocalDateTime.parse(p[2])
                    );
                    system.publishArticle(article);
                    break;
                }

                case "PRINT":
                    system.printNewsForUser(parts[1]);
                    break;
            }
        }
    }
}





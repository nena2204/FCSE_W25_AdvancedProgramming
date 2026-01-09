import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

interface Quantifiable {
    double amount();
}

class License implements Quantifiable, Comparable<License>, LedgerItem<License> {
    String title;
    String category;
    String region;
    int units;
    double ratePerUnit;

    public License(String title, String category, String region, int units, double ratePerUnit) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("title required");
        if (category == null || category.isBlank())
            throw new IllegalArgumentException("category required");
        if (region == null || region.isBlank())
            throw new IllegalArgumentException("region required");
        if (units < 0)
            throw new IllegalArgumentException("units < 0");
        if (ratePerUnit < 0)
            throw new IllegalArgumentException("ratePerUnit < 0");
        this.title = title;
        this.category = category;
        this.region = region;
        this.units = units;
        this.ratePerUnit = ratePerUnit;
    }

    public double amount() {
        return units * ratePerUnit;
    }

    @Override
    public int compareTo(License o) {
        return Comparator.comparing(new Function<License, Double>() {
            @Override
            public Double apply(License l) {
                return l.amount();
            }
        }).reversed().thenComparing(new Function<License, String>() {
            @Override
            public String apply(License l) {
                return l.category;
            }
        }).thenComparing(new Function<License, String>() {
            @Override
            public String apply(License license) {
                return license.title;
            }
        }).compare(this, o);
    }

    public String toString() {
        return "%s [%s|%s] units=%d rp=%f total=%f".formatted(title, category, region, units, ratePerUnit, amount());
    }
}

interface LedgerItem<T> extends Quantifiable, Comparable<T> {
}

class Ledger<T extends LedgerItem<T>> implements Comparable<Ledger<T>> {

    List<T> items = new ArrayList<>();

    public Ledger() {

    }

    public void put(T item) {
        items.add(Objects.requireNonNull(item));
    }

    public <R> Set<R> project(Function<T, R> mapper) {
        return items.stream()
                .map(mapper)
                .collect(Collectors.toSet());
    }

    public void forEachIf(Predicate<T> condition, Consumer<T> action) {
        for (T item : items) {
            if (condition.test(item)) {
                action.accept(item);
            }
        }
    }

    @Override
    public int compareTo(Ledger<T> o) {
        return Double.compare(this.sum(), o.sum());
    }

    public double sum() {
        return items.stream().mapToDouble(new ToDoubleFunction<T>() {
                    @Override
                    public double applyAsDouble(T value) {
                        return value.amount();
                    }
                })
                .sum();
    }


}


public class MediaLicensesDemo {
    public static void main(String[] args) {
        Ledger<License> ledger = new Ledger<>();
        ledger.put(new License("Lo-Fi Beats", "music", "EU", 120_000, 0.0012));
        ledger.put(new License("Cooking B-Roll", "video", "US", 18_000, 0.02));
        ledger.put(new License("City Skyline", "photo", "EU", 7000, 0.15));
        ledger.put(new License("Nature Ambience", "music", "APAC", 90_000, 0.0014));
        ledger.put(new License("Interview Pack", "video", "EU", 9_500, 0.03));
        ledger.put(new License("Retro Poster", "photo", "US", 1500, 0.40));

        Set<String> categories = ledger.project(new Function<License, String>() {
            @Override
            public String apply(License license) {
                return license.category;
            }
        });
        System.out.println("CATEGORIES: " + categories);

        System.out.println("\nMARK HIGH-VALUE (> 100.00):");
        ledger.forEachIf(l -> l.amount() > 100.0, l -> System.out.println("★ " + l.title));

    }
}

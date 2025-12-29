package lab.lab_3;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.ToDoubleFunction;


public class MovieTheaterTester {
    public static void main(String[] args) {
        MovieTheater mt = new MovieTheater();
        try {
            mt.readMovies(System.in);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("SORTING BY RATING");
        mt.printByRatingAndTitle();
        System.out.println("\nSORTING BY GENRE");
        mt.printByGenreAndTitle();
        System.out.println("\nSORTING BY YEAR");
        mt.printByYearAndTitle();
    }
}

class Movie {
    String title;
    String genre;
    int year;
    double avgRating;

    public Movie(String title, String genre, int year, double avgRating) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.avgRating = avgRating;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public double getAvgRating() {
        return avgRating;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %d, %.2f", title, genre, year, avgRating);
    }
}

class MovieTheater {
    private ArrayList<Movie> movies;

    public MovieTheater() {
        movies = new ArrayList<>();
    }

    public void readMovies(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        int n = Integer.parseInt(reader.readLine());
        for (int i = 0; i < n; i++) {
            String title = reader.readLine();
            String genre = reader.readLine();
            int year = Integer.parseInt(reader.readLine());
//            System.out.println("ratings line = [" + line + "]");
            String[] ratings = reader.readLine().trim().split("\\s+");
            double d = Arrays.stream(ratings).mapToDouble(new ToDoubleFunction<String>() {
                @Override
                public double applyAsDouble(String value) {
                    return Double.parseDouble(value);
                }
            }).average().orElse(0);
            Movie m = new Movie(title, genre, year, d);
            movies.add(m);
        }
    }

    //sort spored genre pa naslov
    public void printByGenreAndTitle() {
        movies.stream().sorted(new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                int genreCompare = o1.getGenre().compareToIgnoreCase(o2.getGenre());
                if (genreCompare != 0) {
                    return genreCompare;
                }
                return o1.getTitle().compareToIgnoreCase(o2.getTitle());
            }
        }).forEach(new Consumer<Movie>() {
            @Override
            public void accept(Movie movie) {
                System.out.println(movie);
            }
        });
    }

    //sort year pa naslov
    public void printByYearAndTitle() {
        movies.stream().sorted(new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                int yearCompare = Integer.compare(o1.getYear(),o2.getYear());
                if (yearCompare!=0){
                    return yearCompare;
                }
                return o1.getTitle().compareToIgnoreCase(o2.getTitle());
            }
        }).forEach(new Consumer<Movie>() {
            @Override
            public void accept(Movie movie) {
                System.out.println(movie);
            }
        });
    }

    //sort avgr pa naslov
    public void printByRatingAndTitle() {
        movies.stream().sorted(new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                int avgratingCmp=Double.compare(o2.getAvgRating(),o1.getAvgRating());
                if (avgratingCmp!=0){
                    return avgratingCmp;
                }
                return o1.getTitle().compareToIgnoreCase(o2.getTitle());
            }
        }).forEach(new Consumer<Movie>() {
            @Override
            public void accept(Movie movie) {
                System.out.println(movie);
            }
        });
    }
}

import java.util.*;

class Airports {

    Map<String, Airport> airports;

    public Airports() {
        this.airports = new TreeMap<>();
    }

    public void addAirport(String name, String country, String code, int passengers) {
        airports.put(code, new Airport(name, country, code, passengers));
    }

    public void addFlights(String from, String to, int time, int duration) {
        Airport airport = airports.get(from);
        airport.addFlight(from, to, time, duration);
    }

    public void showFlightsFromAirport(String code) {
        Airport airport = airports.get(code);
        System.out.println(airport);
        int i = 1;

        for (String toCode : airport.flights.keySet()) {
            Set<Flight> flights = airport.flights.get(toCode);
            for (Flight fl : flights) {
                System.out.printf("%d. %s\n", i++, fl);
            }
        }
    }

    public void showDirectFlightsFromTo(String from, String to) {
        Airport fromAirport = airports.get(from);
        Set<Flight> directFlights = fromAirport.flights.get(to);
        if (directFlights != null) {
            for (Flight f : directFlights)
                System.out.println(f);
        } else System.out.printf("No flights from %s to %s\n", from, to);
    }

    public void showDirectFlightsTo(String to) {
        Set<Flight> directflights = new TreeSet<>();
        for (Airport ap : airports.values()) {
            Set<Flight> flightsTo = ap.flights.get(to);
            //ke gi zememe site flights to FOR EACH AP
            if (flightsTo != null){
                directflights.addAll(flightsTo);
            }
        }
        for (Flight flight:directflights){
            System.out.println(flight);
        }
    }
}

class Airport {
    String name;
    String country;
    String code;
    int passengers;
    Map<String, Set<Flight>> flights;

    public Airport(String name, String country, String code, int passengers) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.passengers = passengers;
        flights = new TreeMap<>();
    }

    public void addFlight(String from, String to, int time, int duration) {
        Set<Flight> flightSet = flights.get(to);
        if (flightSet == null) {
            flightSet = new TreeSet<>();
            flights.put(to, flightSet);
        }
        flightSet.add(new Flight(from, to, time, duration));
    }

    @Override
    public String toString() {
        return String.format("%s (%s)\n%s\n%d", name, code, country, passengers);
    }
}

class Flight implements Comparable<Flight> {
    String from;
    String to;
    int time;
    int duration;

    public Flight(String from, String to, int time, int duration) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.duration = duration;
    }

    @Override
    public int compareTo(Flight o) {
        int x = Integer.compare(this.time, o.time);
        if (x == 0) {
            return this.from.compareTo(o.from);
        }
        return x;
    }

    @Override
    public String toString() {
        int end = time + duration;
        int plus = end / (24 * 60);
        end %= (24 * 60);
        return String.format("%s-%s %02d:%02d-%02d:%02d%s %dh%02dm", from, to, time / 60, time % 60, end / 60, end % 60, plus > 0 ? " +1d" : "", duration / 60, duration % 60);
    }
}

public class AirportsTester {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}

// vashiot kod ovde

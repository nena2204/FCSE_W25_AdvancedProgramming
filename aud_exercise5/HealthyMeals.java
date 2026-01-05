import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Person {
    int id;
    int healthyCount;
    List<String> eats = new ArrayList<>();

    public Person(int id, int healthycount) {
        this.id = id;
        this.healthyCount = healthycount;
    }

    public Person(String line){
        String[] parts = line.split("\\s+");
        this.id = Integer.parseInt(parts[0]);
        this.eats.addAll(Arrays.stream(Arrays.copyOfRange(parts,1,parts.length)).collect(Collectors.toList()));
    }

    public int getNumHealthy(List<String> healthyMeals){
        return (int) eats.stream().filter(healthyMeals::contains).collect(Collectors.toSet()).size();
    }
}

public class HealthyMeals {
    private List<String> healthyMeals;

    public HealthyMeals() {
        healthyMeals = new ArrayList<>();
    }

    public void evaluate(InputStream is, OutputStream os) throws IOException {
        //healthyMeal1 healthyMeal2 healthyMeal3 …
        // - PERSON_ID meal1 meal2 meal3 …
        // - PERSON_ID meal1 meal2 …
        //kolku od vnesenite obroci se zdravi = kolku se pojavuvaat vo listata na zdravi obroci
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        PrintWriter pw = new PrintWriter(os);
        healthyMeals = Arrays.stream(br.readLine().split("\\s+")).collect(Collectors.toList());

        List<Person> people = br.lines()
                .map(Person::new)
                .collect(Collectors.toList());

        people.sort(Comparator.comparingInt((Person p) -> p.healthyCount).reversed()
                .thenComparing(p -> p.id)
        );
        people.forEach(p -> pw.printf("Person ID: %d (healthy meals: %d)%n",
                p.id, p.getNumHealthy(healthyMeals))
        );

        pw.flush();

    }

    public static void main(String[] args) {
        HealthyMeals healthyMeals=new HealthyMeals();
        try {
            healthyMeals.evaluate(System.in, System.out);
        } catch (IOException e) {
            System.out.print("error!");
        }
    }
}

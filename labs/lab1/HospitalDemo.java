import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.Consumer;

//disclaimer: ima greska vo vtor primer, doc. Petar od level 1 ako se zgolemi ke bide 2 a vo expected output bara 3
//Користете conditionalMutate за да ја зголемите експертизата за 1 само на докторите со повеќе од 30 пациенти. (ovoj uslov znaci >30 a ne >=30)
//so uslovot >=30 pominuva zadacata


// TODO: Create a functional interface to evaluate the doctors
interface DoctorEvaluator {
    boolean evaluate(Doctor doctor);
}

// TODO: Create a HighExpertiseEvaluator class that will implement the functional interface and override the evaluate method
class HighExpertiseEvaluator implements DoctorEvaluator {
    @Override
    public boolean evaluate(Doctor doctor) {
        return doctor.getLevel() >= 7;
    }
}

class Doctor {
    private final int licenseNumber;    // e.g., 1234
    private String name;
    private int level;                  // 1..10 (10 = chief)

    private int patients;

    public Doctor(int licenseNumber, String name, int level, int patients) {
        this.licenseNumber = licenseNumber;
        this.name = name;
        this.level = level;
        this.patients = patients;
    }

    public int getLicenseNumber() {
        return licenseNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level > 10) {
            this.level = 10;
            return;
        }
        if (level < 1) {
            this.level = 1;
            return;
        }
        this.level = level;
    }

    public int getPatients() {
        return patients;
    }

    public void setPatients(int patients) {
        this.patients = patients;
    }

    @Override
    public String toString() {
        return String.format("%s (%d) %d %d %s", name, licenseNumber, level, patients, level == 10 ? "[Chief]" : "");
    }
}

class EmergencyRoom {
    private final String hospitalName;
    private final Doctor[] doctors;
    private int size = 0;

    public EmergencyRoom(String title, int doctorCapacity) {
        this.hospitalName = title;
        this.doctors = new Doctor[doctorCapacity];
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return doctors.length;
    }

    /**
     * Add a doctor provided by a Supplier. Demonstrates Supplier<T>.
     */
    public boolean treat(Supplier<Doctor> supplier) {
        if (size >= doctors.length) {
            return false;
        }
        doctors[size++] = supplier.get();
        return true;
    }

    /**
     * Apply a Consumer to each doctor (side effects allowed, e.g., print or mutate).
     */
    public void forEach(Consumer<Doctor> action) {
        for (int i = 0; i < size; i++) {
            action.accept(doctors[i]);
        }
    }

    /**
     * Count doctors satisfying a Predicate.
     */
    public int count(Predicate<Doctor> predicate) {
        int c = 0;
        for (int i = 0; i < size; i++) {
            if (predicate.test(doctors[i])) {
                c++;
            }
        }
        return c;
    }

    /**
     * Find first doctor that matches; returns null if none.
     */
    public Doctor findFirst(Predicate<Doctor> predicate) {
        for (int i = 0; i < size; i++) {
            if (predicate.test(doctors[i])) {
                return doctors[i];
            }
        }
        return null;
    }

    /**
     * Filter doctors into a NEW array (still no collections).
     */
    public Doctor[] filter(Predicate<Doctor> predicate) {
        // 1st pass: count matches to size array exactly
        int matches = count(predicate);
        Doctor[] out = new Doctor[matches];
        int j = 0;
        for (int i = 0; i < size; i++) {
            if (predicate.test(doctors[i])) {
                out[j++] = doctors[i];
            }
        }
        return out;
    }

    /**
     * Map doctors to Strings (labels) with a Function.
     * (We return String[] to avoid generics + array creation complexity.)
     */
    public String[] mapToLabels(Function<Doctor, String> mapper) {
        String[] out = new String[size];
        for (int i = 0; i < size; i++) {
            out[i] = mapper.apply(doctors[i]);
        }
        return out;
    }

    /**
     * In-place update using a Consumer (mutation allowed).
     * Example: increase level +1, cap at 10.
     */
    public void mutate(Consumer<Doctor> mutator) {
        for (int i = 0; i < size; i++) {
            mutator.accept(doctors[i]);
        }
    }

    public void conditionalMutate(Predicate<Doctor> condition, Consumer<Doctor> mutator) {
        for (int i = 0; i < size; i++) {
            if (condition.test(doctors[i])) {
                mutator.accept(doctors[i]);
            }
        }

    }

    public int countForEvaluation(DoctorEvaluator evaluator) {
        int c = 0;
        for (int i = 0; i < size; i++) {
            if (evaluator.evaluate(doctors[i])) {
                c++;
            }
        }
        return c;
    }

    public Doctor[] evaluate(DoctorEvaluator evaluator) {
        int outSize = countForEvaluation(evaluator);
        Doctor[] out = new Doctor[outSize];
        int j = 0;
        for (int i = 0; i < size; i++) {
            if (evaluator.evaluate(doctors[i])) {
                out[j++] = doctors[i];
            }
        }
        return out;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hospital: ").append(hospitalName).append(" (").append(size).append("/").append(doctors.length).append(" doctors)\n");
        return sb.toString();

    }
}

public class HospitalDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmergencyRoom er = new EmergencyRoom("University Clinic", 10);

        int n = sc.nextInt();
        
        //TODO: Supplier that reads line by line
        Supplier<Doctor> doctorSupplier = () -> {
            int license = sc.nextInt();
            String name = sc.next();
            int level = sc.nextInt();
            int patioents = sc.nextInt();
            return new Doctor(license, name, level, patioents);
        };
        //TODO: Add n doctors to treat using the supplier
        for (int i = 0; i < n; i++) {
            boolean added = er.treat(doctorSupplier);
            if (!added) {
                System.out.println("Capacity reached");
                break;
            }
        }
        sc.close(); // close scanner after done

        //TODO: Print all of the doctors with
        //forEach
        System.out.println("Doctors that are treating:");
        Consumer<Doctor> printDoctor = doctor -> System.out.println(doctor);
        er.forEach(printDoctor);

        //TODO: Print all doctors that treat
        // using Consumer + forEach ---
        System.out.println("\n=== All Doctors ===");
        er.forEach(d -> System.out.println(d));


        //TODO: Use Functional Interface to filter the good doctors ---
        DoctorEvaluator highExp = new HighExpertiseEvaluator();
        DoctorEvaluator manyPatients = d -> d.getPatients() > 20;
        DoctorEvaluator combined = d -> highExp.evaluate(d) && manyPatients.evaluate(d);
        Doctor passing[] = er.evaluate(combined);

        System.out.println("\n=== Doctors with higher number of patients and a higher level of expertise ===");
        for (Doctor d : passing)
            System.out.println(d);

        //TODO: Print the chief of the department (level = 10)

        System.out.println("\n=== Chief doctor (level = 10) ===");
        Doctor chief = er.findFirst(d -> d.getLevel() == 10);
        System.out.println(chief != null ? chief : "No chief found");

        //TODO: Increase the level of expertise for every doctor by 1
        System.out.println("\n=== Increase all expertise levels by 1 (max 10) ===");
        er.mutate(d->d.setLevel(d.getLevel()+1));
        er.forEach(printDoctor);


        //TODO: Conditional mutation: increase the level of expertise of every doctor that has more than 30 patients
        System.out.println("\n=== Increase the level of expertise of every doctor by 1 ===");
        er.conditionalMutate(d->d.getPatients()>=30, d->d.setLevel(d.getLevel()+1));

        //TODO: Map doctors to labels in the format: Name: name, Level: level
        System.out.println("\n=== Map doctors to labels ===");
        String[] labels=er.mapToLabels(d->String.format("Name: %s, Level: %d",d.getName(),d.getLevel()));
        for (String label: labels){
            System.out.println(label);
        }
    }
    
}

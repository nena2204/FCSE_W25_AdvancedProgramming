package aud_2;

import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

class Student{
    private final String index;
    private String name;
    private Integer grade;
    private Integer attendance;

    public Student(String index, String name, Integer grade, Integer attendance){
        this.index=index;
        this.name=name;
        this.grade=grade;
        this.attendance=attendance;


    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString(){
        return name + " ( "+index+" ), grade = "+grade+" ,attendance= "+attendance+"%";
    }
    public Integer getGrade() {
        return grade;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setGrade(Integer grade){
        if (grade>10){
            grade=10;
        }
        if (grade<5){
            grade=5;
        }
        this.grade=grade;
    }
}
class Course {
    private final Student[] students;
    private String title;
    private Integer numStudents = 0;

    public Course(String title, Integer capacity) {
        this.title = title;
        this.students = new Student[capacity];
    }

    public int capacity() {
        return students.length;
    }

    public Integer size() {
        return numStudents;
    }

    //1. enroll(Supplier<Student> supplier)
    public boolean enroll(Supplier<Student> supplier) {
        if (numStudents >= students.length) {
            return false;
        }
        students[numStudents++] = supplier.get();
        return true;
    }

    //2. forEach(Consumer<Student> condition
    public void forEach(Consumer<Student> action) {
        for (int i = 0; i < numStudents; i++) {
            action.accept(students[i]);
        }
    }

    //3.count(Predicate<Student> condition) (site so ispolnuvat uslov)
    public int count(Predicate<Student> predicate) {
        int c = 0;
        for (int i = 0; i < numStudents; i++) {
            if (predicate.test(students[i])) {
                c++;
            }
        }
        return c;
    }



    //4. findFirst(Predicate<Student> condition) (prv so taj uslov ili null
    public Student findFirst(Predicate<Student> predicate){
        for (int i = 0; i < numStudents; i++) {
            if(predicate.test(students[i])){
                return students[i];
            }
        }
        return null;
    }

    //5. filter(Predicate<Student> condition) lista studenti koi ispolnuvaat uslov

    public Student[] filter(Predicate<Student> predicate){
        int matches=count(predicate);
        Student[] newStudents=new Student[matches];
        int j=0;
        for (int i = 0; i < numStudents; i++) {
            if(predicate.test(students[i])){
                newStudents[j++]=students[i];
            }
        }
        return newStudents;
    }
    //6. mapToLabels(Function<Student, String> mapper) niza text opisi dobieni pri transform so dadena funkcija
    public String[] mapToLabels(Function<Student, String> mapper){
        String[] newOutput=new String[numStudents];
        for (int i = 0; i < numStudents; i++) {
            newOutput[i]=mapper.apply(students[i]);
        }
        return newOutput;
    }
    //7. mutate(Consumer<Student> mutator) primenuva promena na site studenti
    public void mutate(Consumer<Student> mutator){
        for (int i = 0; i < numStudents; i++) {
            mutator.accept(students[i]);
        }
    }

    //8. conditionalMutate(Predicate<Student> condition, Consumer<Student> mutator) menuva samo kaj tie so ispolnuvat uslov
    public void conditionalMutate(Predicate<Student> condition, Consumer<Student> mutator){
        for (int i = 0; i < numStudents; i++) {
            if (condition.test(students[i])){
                mutator.accept(students[i]);
            }
        }
    }

    //9. toString() tekstualen opis na kursot
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("Course: "+title+" ("+numStudents+"/"+students.length+" students)");
        for (Student student: students){
            sb.append(student.toString()).append("\n");
        }
        return sb.toString();
    }
}

public class CourseDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Course AP = new Course("Advanced Programing", 10);
        int n = sc.nextInt(); //broj studentii
        //Креирај Supplier<Student> кој чита податоци за еден студент од конзолата (индекс, име, оцена, присуство) и враќа нов објект Student.

        Supplier<Student> studentsFromInput = () -> {
            String index = sc.next();
            String name = sc.next();
            int grade = sc.nextInt();
            int attendance = sc.nextInt();
            sc.nextLine();
            return new Student(index, name, grade, attendance);
        };

        //Запиши n студенти во курсот користејќи го методот enroll.
        for (int i = 0; i < n; i++) {
            AP.enroll(studentsFromInput);
        }
        sc.close();
        //Користи Consumer<Student> заедно со forEach за да ги испечатиш сите запишани студенти.
        AP.forEach(System.out::println);

        Consumer<Student> printer= System.out::println;
        AP.forEach(printer);
        //Дефинирај Predicate<Student> за студенти кои:
        //имаат оцена поголема или еднаква на 6
        //имаат присуство најмалку 70%
        //Комбинирај ги двете состојби со .and() и користи го методот filter за да ги прикажеш само тие студенти.

        Predicate<Student> isPassing= s-> s.getGrade()>=6;
        Predicate<Student> goodAttendance= s-> s.getAttendance()>70;
        Predicate<Student> passingAndAttendance= isPassing.and(goodAttendance);
        Student[] passing=AP.filter(passingAndAttendance);
        for (Student s: passing) System.out.println(s);


        //Користи findFirst за да го пронајдеш и прикажеш првиот студент
        // со оцена поголема или еднаква на 9.

        Student first9= AP.findFirst(s->s.getGrade()>=9);
        System.out.println(first9);

        //Користи mutate за да ја зголемиш оцената на сите студенти за 1.
        Consumer<Student> curveAllgrades= s-> s.setGrade(s.getGrade()+1);
        AP.mutate(curveAllgrades);
        AP.forEach(printer);
        
        //Користи conditionalMutate за да ја зголемиш оцената за 1 
        //само на студентите со присуство поголемо или еднакво на 90%.
        //conditional mutation
        AP.conditionalMutate(
                s->s.getAttendance()>=90,
                s->s.setGrade(s.getGrade()+1)
        );
        
    }
}

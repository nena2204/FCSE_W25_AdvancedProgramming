package auditoriski.aud_4;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.io.InputStream;

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

class F1Race {
    private ArrayList<Driver> drivers;
    // vashiot kod ovde
    //kоја ќе чита од влезен тек (стандарден влез, датотека, …)
    //Driver_name lap1 lap2 lap3 (lap: mm:ss:nnn)


    public F1Race() {
        this.drivers = new ArrayList<Driver>();
    }

    public void readResults(InputStream is){
        Scanner sc=new Scanner(is);
        while(sc.hasNextLine()){
            String line=sc.nextLine();
            if (line.isEmpty()) break;

            String[] parts=line.split(" ");
            Driver driver=new Driver(parts[0],Driver.StringToTime(parts[1]),
                    Driver.StringToTime(parts[2]),
                    Driver.StringToTime(parts[3]));
            drivers.add(driver);
        }
        sc.close();
    }
    public void printSorted(OutputStream os){
        //site soferi spored best time (prv so najdobro)
        //formar driver besttime
        Collections.sort(drivers);
        PrintWriter printW=new PrintWriter(os);
        int counter=1;
        for (Driver driver: drivers){
            printW.printf("%d. %s\n", counter++, driver);
        }
        printW.flush();
    }
}

class Driver implements Comparable<Driver> {
    private String name;
    private int lap1;
    private int lap2;
    private int lap3;
    private int best;
    //forma tmm:ss:nnn


    public Driver(String name, int lap1, int lap2, int lap3) {
        this.name = name;
        this.lap1 = lap1;
        this.lap2 = lap2;
        this.lap3 = lap3;
        this.best = Math.min(Math.min(lap1, lap2), lap3);
    }
    public static int StringToTime(String time){
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 60 * 1000
                + Integer.parseInt(parts[1]) * 1000
                + Integer.parseInt(parts[2]);
    }
    public static String timeToString(int time) {
        int min = (time / 1000) / 60;
        int sec = (time - min * 1000 * 60) / 1000;
        int ns = time % 1000;
        return String.format("%d:%02d:%03d", min,sec,ns);
    }

    @Override
    public int compareTo(Driver o){
        return this.best-o.best;
    }
    @Override
    public String toString() {
        return String.format("%-10s%10s", name, timeToString(best));
    }
}


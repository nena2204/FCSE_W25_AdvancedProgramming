import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class StatisticsService {
    List<Integer> numbers = new ArrayList<>();

    public synchronized void addNumber(Integer nToAdd) {
        numbers.add(nToAdd);
    }

    public synchronized int getCount() {
        return numbers.size();
    }

    public synchronized double getMax() {
        double max=0;
        for (Integer num:numbers){
            if (num>max){
                max=num;
            }
        }
        return max;
    }

    public synchronized double getMin() {
        double min=numbers.get(0);
        for (Integer num:numbers){
            if (num<min){
                min=num;
            }
        }
        return min;
    }
    public synchronized double getAverage(){
        int sum=0;
        for (Integer num:numbers){
            sum+=num;
        }
        return (double) sum /numbers.size();
    }

}
class SubmitNumberTask implements Callable<String>{
    StatisticsService service;
    int number;

    public SubmitNumberTask(StatisticsService service, int number){
        this.service=service;
        this.number=number;
    }

    @Override
    public String call() throws Exception {
        service.addNumber(number);
        return String.format("NUMBER %d ADDED. Total numbers: %d",number,service.getCount());
    }
}
class GetAverageTask  implements Callable<String>{
    StatisticsService service;

    public GetAverageTask(StatisticsService service){
        this.service=service;
    }
    @Override
    public String call() throws Exception {

        service.getAverage();
        return String.format("AVERAGE: %.2f",service.getAverage());
    }
}
class GetMinTask  implements Callable<String>{
    StatisticsService service;

    public GetMinTask(StatisticsService service){
        this.service=service;
    }
    @Override
    public String call() throws Exception {
        service.getMin();
        return String.format("MIN: %.2f",service.getMin());
    }
}
class GetMaxTask  implements Callable<String>{
    StatisticsService service;

    public GetMaxTask(StatisticsService service){
        this.service=service;
    }
    @Override
    public String call() throws Exception {
        service.getMax();
        return String.format("MAX: %.2f",service.getMax());
    }
}
class ConcurrentService{
    static List<Future<String>> submitAll(int numThreads, List<Callable<String>> tasks){

        ExecutorService executor=Executors.newFixedThreadPool(numThreads);
        List<Future<String>> futures=new ArrayList<>();

        for (Callable<String> task:tasks){
            futures.add(executor.submit(task));
        }
        executor.shutdown();
        return futures;
    }
}
public class StatisticsExam {

    public static void main(String[] args) throws Exception {

        StatisticsService service = new StatisticsService();

        int k;
        Scanner scanner = new Scanner(System.in);
        k = scanner.nextInt();


        List<Callable<String>> tasks = new ArrayList<>();

    

        int added = 0;
        int avg = 0;
        int min = 0;
        int max = 0;

        int expectedMin = 10;
        int expectedMax = 10;

        for (int i = 1; i < k * 100; i++) {
            int value = i * 10;
            tasks.add(new SubmitNumberTask(service, value));
            expectedMax = Math.max(expectedMax, value);
            added++;
        }

        for (int i = 0; i < k * 5; i++) {
            tasks.add(new GetAverageTask(service));
            avg++;
            tasks.add(new GetMinTask(service));
            min++;
            tasks.add(new GetMaxTask(service));
            max++;
        }

        for (int i = 100; i <= k * 200; i += 10) {
            tasks.add(new SubmitNumberTask(service, i));
            added++;
            expectedMax = Math.max(expectedMax, i);
            tasks.add(new GetAverageTask(service));
            avg++;
            tasks.add(new GetMinTask(service));
            min++;
            tasks.add(new GetMaxTask(service));
            max++;
        }

        List<Future<String>> results = ConcurrentService.submitAll(6, tasks);


        List<String> finalResults = new ArrayList<>();
        for (Future<String> f : results) {
            try {
                finalResults.add(f.get());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        int numberAddedMessage = 0, minInvoked = 0, maxInvoked = 0, averageInvoked = 0;

        for (String finalResult : finalResults) {
            if (finalResult.startsWith("AVERAGE")) {
                averageInvoked++;
            }
            if (finalResult.startsWith("MIN")) {
                minInvoked++;
            }
            if (finalResult.startsWith("MAX")) {
                maxInvoked++;
            }
            if (finalResult.contains("Total numbers: ")) {
                numberAddedMessage++;
            }
        }

        if (minInvoked != min) {
            System.out.println("GetMinTask was not invoked the correct number of times");
        }

        if (maxInvoked != max) {
            System.out.println("GetMaxTask was not invoked the correct number of times");
        }

        if (averageInvoked != avg) {
            System.out.println("GetAverageTask was not invoked the correct number of times");
        }

        if (numberAddedMessage != added) {
            System.out.println("Number of added tasks was not invoked the correct number of times");
        }

        int finalCount = service.getCount();


        if (finalCount != added) {
            throw new RuntimeException(
                    String.format("ERROR: Expected %d numbers, but got %d", added, finalCount)
            );
        }

        if (service.getMin() != expectedMin) {
            throw new RuntimeException(
                    "ERROR: Expected MIN = " + expectedMin
            );
        }

        if (service.getMax() != expectedMax) {
            throw new RuntimeException(
                    "ERROR: Expected MAX = " + expectedMax
            );
        }

        System.out.println("✔ FINAL CHECKS PASSED");
    }
}

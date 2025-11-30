package auditoriski.aud_3;

import java.util.ArrayList;

//staticki metod standard deviation
public class MyMathClass<T> {

    public static <T extends Number> double standardDeviation(ArrayList<T> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }

        //sredna vrend mean
        double sum = 0;
        for (T num : list) {
            sum += num.doubleValue();
        }
        double mean = sum / (list.size());

        //suma na kvadratni otstapuvanja (xi - mean)
        double squaredSum = 0;
        for (T num : list) {
            double difference = num.doubleValue() - mean;
            squaredSum += difference * difference;
        }

        //standardna dev
        return Math.sqrt(squaredSum / (list.size()));

    }

    public static void main(String[] args) {
        ArrayList<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(10);
        ints.add(20);
        ints.add(30);
        ints.add(40);
        ints.add(50);
        System.out.println(String.format("STD: %.2f",
                MyMathClass.standardDeviation(ints)));
        ArrayList<Double> doubles = new ArrayList<>();
        doubles.add(3.4);
        doubles.add(2.2);
        System.out.println(String.format("STD: %.2f",
                MyMathClass.standardDeviation(doubles)));
    }
}

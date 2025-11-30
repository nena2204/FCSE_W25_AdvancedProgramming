package auditoriski.aud_3;
//genericka klasa koja simulira iscrtuvanje na slucaen
//predmet od nekoja kutija
//sodrzi lista so iminja i izbira edno slucajno ime
//ili lista broevi i izbria eden slucajno

//method ADD za dodavanje objekt od soodvetnio tip
//method ISEMPTY dali kutijata e prazna
//method DRAWITEM koj slucajno izbira objekt ako e prazna null


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Box<T> {
    private ArrayList<T> items;

    public Box() {
        items = new ArrayList<>();
    }

    public void add(T item) {
        items.add(item);
    }

    public boolean isEmpty() {
        return items.size() == 0;
    }

    public T drawItem() {
        if (isEmpty()) {
            return null;
        }
        Random random = new Random();
        return items.get(random.nextInt(items.size()));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose type");
        System.out.println("1 - String");
        System.out.println("2 - Integer");


        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            Box<String> box = new Box<>();
            for (int i = 0; i < 5; i++) {
                box.add(sc.nextLine());
            }
            System.out.println("Random element: " + box.drawItem());
        } else if (choice == 2) {
            Box<Integer> box = new Box<>();
            for (int i = 0; i < 5; i++) {
                box.add(sc.nextInt());
            }
            System.out.println("Random element: " + box.drawItem());
        }
    }
}



package auditoriski.aud_3;
//pq e pod struktura vo koja sekoj element e pridruzen
//so negoviot prioritet (cel broj)
//method add(item,priority)
//method remove (el so najgolem prioritet i go brise)
//ako e prazno vrakja null

import java.util.ArrayList;

public class PriorityQueue<T> {
    private ArrayList<T> q;
    private ArrayList<Integer> priorities;

    public PriorityQueue() {
        q = new ArrayList<>();
        priorities = new ArrayList<>();
    }

    public void add(T item, Integer priority) {
        int i;
        for (i = 0; i < priorities.size(); i++) {
            if (priorities.get(i) < priority) {
                break;
            }
        }
        q.add(i,item);
        priorities.add(i,priority);
    }
    public T remove(){
        if (q.isEmpty()){
            return null;
        }
        T itemFirst=q.get(0);
        q.remove(0);
        priorities.remove(0);
        return itemFirst;
    }

    public static void main(String[] args) {
        PriorityQueue<String> pq=new PriorityQueue<>();
        pq.add("Miroslav",0);
        pq.add("Sara",3);
        pq.add("Filip",10);
        pq.add("Koki",5);

        System.out.println(pq.remove());
        System.out.println(pq.remove());
        System.out.println(pq.remove());
    }
}

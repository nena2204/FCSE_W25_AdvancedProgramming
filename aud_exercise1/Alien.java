package aud_1.firstEx;

//Кодот не е многу објектно ориентиран и не
//подржува криење на информациите во класата Alien.

//Да се пренапише, така што ќе се искористи наследување за да се
//репрезентираат различни типови вонземјани, наместо да се користи
//параметарот type. Исто така пренапишете jа класата Alien така
//што ќе ги крие инстанцните променливи и додадете метод getDamage
//коj за секоја од изведените класа ќе jа врак̀а штетата која jа
//предизвикува. На крај пренапишете го методот calculateDamage да го
//користи getDamage и напишете main метод да ја тестирате класата.

import java.util.Scanner;

public abstract class Alien {
    public static final int SNAKE_ALIEN = 0;
    public static final int OGRE_ALIEN = 1;
    public static final int MARSHMALLOW_MAN_ALIEN = 2;
//    private int type; // Stores one of the three above types
    private int health; // 0=dead, 100=full strength
    private String name;

    public Alien(int health, String name) {
//        this.type = type;
        this.health = health;
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }
    public abstract int getDamage();

    //test (ne se bara vo zadaca
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        AlienPack pack=new AlienPack(n);

        for (int i = 0; i < n; i++) {
            String type=sc.next().toLowerCase();
            String name=sc.next();
            int health=sc.nextInt();

            Alien alien;
            if (type.equals("snake")){
                alien=new SnakeAlien(health,name);
            }else if(type.equals("ogre")){
                alien=new OgreAlien(health,name);
            }else {
                alien=new MarshmallowAlien(health,name);
            }
            pack.addAlien(alien,i);
        }

        System.out.println("alien summary:");
        pack.printAliens();
        System.out.println("total damage: "+ pack.calculateDamage());
    }
}
class AlienPack {
    private Alien[] aliens;

    public AlienPack(int numAliens) {
        aliens = new Alien[numAliens];
    }

    public void addAlien(Alien newAlien, int index) {
        aliens[index] = newAlien;
    }

    public Alien[] getAliens() {
        return aliens;
    }

    public int calculateDamage() {
        int damage = 0;
        for(Alien alien: aliens){
            damage+=alien.getDamage();
        }
        return damage;
    }
    public void printAliens() {
        for (Alien alien : aliens) {
            System.out.println(alien.getName() + " -> " + alien.getDamage() + " damage");
        }
    }
}
class SnakeAlien extends Alien{
    public SnakeAlien(int health, String name){
        super(health,name);
    }
    @Override
    public int getDamage(){
        return 10;
    }
}
class OgreAlien extends Alien{
    public OgreAlien(int health,String name){
        super(health,name);
    }
    @Override
    public int getDamage(){
        return 6;
    }
}
class MarshmallowAlien extends Alien{
    public MarshmallowAlien(int health,String name){
        super(health,name);
    }
    @Override
    public int getDamage(){
        return 1;
    }
}


//promeni:
//1. nasleduvanje, each alien diff class
//2. enkapsulacija, public -> private + get methods
//3. polimorfizam, sekoj tip alien svoj damage

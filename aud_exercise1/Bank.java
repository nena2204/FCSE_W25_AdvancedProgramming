package aud_1.firstEx;

import java.util.ArrayList;
import java.util.List;

//Дадени се следниве пет класи:

// Bank
// Account
// NonInterestCheckingAccount
// InterestCheckingAccount
// PlatinumCheckingAccount
// како и интефејс наречен InterestBearingAccount кои се однесуваат на следниот начин:

// Во Bank чува листа од сите видови сметки, вклучувајќи сметки за штедење и за трошење, некои од нив подложни на камата, а некои не. Во Bank постои метод totalAssets кој ја враќа сумата на состојбата на сите сметки. Исто така содржи метод addInterest кој го повикува методот addInterest на сите сметки кои се подложни на камата.
// Account е апстрактна класа. Во секој сметка се чуваат името на сопственикот на сметката, бројот на сметката (секвенцијален број доделен автоматски), моменталната состојба. Во класата се имплементираат конструктор за иницијализација на податочните членови, методи за пристап до моменталната состојба, како и за додавање и одземање од моменталната состојба.
// InterestBearingAccount интерфејсот декларира единствен метод addInterest (без параметри и не враќа ништо - void) кој ја зголемува состојбата со соодветната камата за овој вид на сметка.
// InterestCheckingAccount е сметка Account која е исто така InterestBearingAccount. Повикување addInterest ја зголемува состојбата за 3%.
// PlatinumCheckingAccount е InterestCheckingAccount. Повикување addInterest ја зголемува состојбата двојно од каматата за InterestCheckingAccount (колку и да е таа).
// NonInterestCheckingAccount е сметка Account но не е InterestBearingAccount. Нема дополнителни функционалности надвор од основните од класата Account.
// За оваа задача, потребно е да се имплментира функционалност дадена во претходниот текст:
// Пет од шест класи од споменатите формираат хиерархија. За овие класи да се нацрта оваа хиерархија.
// Да се имплементира Account.
// Да се имплементира NonInterestCheckingAccount.
// Да се напише InterestBearingAccount интерфејсот.
// Да се имплементира Bank.
// Да се имплементира InterestCheckingAccount.
// Да се имплементира PlatinumCheckingAccount.

abstract class Account{

    private static long ACC_NUMBER=1L;

    private String name;
    private long accountNum;
    private double balance;

    public Account(String name, double balance){
        this.name=name;
        this.balance=balance;
        this.accountNum=ACC_NUMBER++;
    }

    public  long getAccNumber() {
        return accountNum;
    }

    public void setAccountNum(long accountNum) {
        this.accountNum = accountNum;
    }

    public String getAccountHolder() {
        return name;
    }

    public void setAccountHolder(String accName){
        this.name=accName;
    }

    public long getAccountNum() {
        return accountNum;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    //dodavanje i odzemanje na momentalna sostojba
    public double deposit(double amount){
        balance+=amount;
        return balance;
    }

    public double withdraw(double amount){
        if(balance>=amount){
            balance-=amount;
        }
        return balance;
    }
//    public abstract AccountType getAccountType();
}

class NonInterestCheckingAccount extends Account {

    public NonInterestCheckingAccount(String holderName, double currentAmount) {
        super(holderName, currentAmount);
    }
}
interface InterestBearingAccount {
    void addInterest();
}

class InterestCheckingAccount extends Account implements InterestBearingAccount{
    public static final double INTEREST_RATE=.03;

    public InterestCheckingAccount(String name,double amount){
        super(name,amount);
    }
    @Override
    public void addInterest() {
        deposit(getBalance() * INTEREST_RATE);
    }
}

class PlatinumCheckingAccount extends Account implements InterestBearingAccount{
    public PlatinumCheckingAccount(String name, double balance){
        super(name,balance);
    }

    @Override
    public void addInterest(){
        deposit(getBalance()*InterestCheckingAccount.INTEREST_RATE *2);

    }
}

public class Bank {
    private List<Account> accounts;

    public Bank(){
        accounts=new ArrayList<>();
    }
    public double totalAssets(){
        double total=0;

        for (Account acc: accounts){
            total+=acc.getBalance();
        }
        return total;
    }
    //TO DO: add Interest - povikuva metod addInterest na site smetki koi se podlozni na kamata

    public void addInterest(){
        for (Account account: accounts){
            if (account instanceof InterestBearingAccount){
                InterestBearingAccount iba = (InterestBearingAccount) account;
                iba.addInterest();
            }
        }

    }


    //TEST ZA PROVERKA (NE SE BARA VO ZADACA)
    public void addAccount(Account a) {
        accounts.add(a);
    }

    public static void main(String[] args) {

        Bank bank=new Bank();

        NonInterestCheckingAccount acc1=new NonInterestCheckingAccount("Ana",1000);
        InterestCheckingAccount acc2=new InterestCheckingAccount("Stefan",2000);
        PlatinumCheckingAccount acc3=new PlatinumCheckingAccount("Bojan",5000);

        bank.addAccount(acc1);
        bank.addAccount(acc2);
        bank.addAccount(acc3);

        System.out.println("Total assets before interest: "+ bank.totalAssets());

        bank.addInterest();
        System.out.println("Total assets after interest: "+bank.totalAssets());

        //each acc
        System.out.println("Ana balance (non_interest): " + acc1.getBalance());
        System.out.println("Stefan balance (3%-interest): " + acc2.getBalance());
        System.out.println("Bojan balance (double interest): " + acc3.getBalance());
    }

}

import java.util.*;
import java.util.stream.Collectors;

class Account {
    private static long NEXT_ID = 1;
    private final long id;
    private String name;
    private double saldo;

    public Account(String name, double saldo) {
        this.name = name;
        this.saldo = saldo;
        this.id = NEXT_ID++;
    }

    public String getName() {
        return name;
    }

    public long getID() {
        return id;
    }

    public double getBalance() {
        return saldo;
    }

    public void setBalance(double balance) {
        this.saldo = balance;
    }

    @Override
    public String toString() {
        return String.format("Name: %s%nBalance:%.2f$%n", name,saldo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) {
            return false;
        }
        Account other=(Account) o;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}

abstract class Transaction {
    private final long withdraw_id;
    private final long deposit_id;
    private final String text;
    private final double amount;

    public Transaction(long withdraw, long deposit, String text, double amount) {
        this.withdraw_id = withdraw;
        this.deposit_id = deposit;
        this.amount = amount;
        this.text = text;
    }

    public long getWithdraw_id() {
        return withdraw_id;
    }

    public long getDeposit_id() {
        return deposit_id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return text;
    }

}

//Исто така треба да се преоптовари equals(Object o):boolean методот и за двете класи.

class FlatAmountProvisionTransaction extends Transaction {
    private final double flatProvision;

    public FlatAmountProvisionTransaction(long fromId, long toId, double amount, double flatProvision) {
        super(fromId, toId, "FlatAmount", amount);
        this.flatProvision = flatProvision;
    }

    public double getFlatAmount() {
        return flatProvision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof FlatAmountProvisionTransaction))
            return false;
        FlatAmountProvisionTransaction other = (FlatAmountProvisionTransaction) o;
        return getWithdraw_id() == other.getWithdraw_id()
                && getDeposit_id() == other.getDeposit_id()
                && Double.compare(getAmount(), other.getAmount()) == 0
                && Double.compare(flatProvision, other.flatProvision) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWithdraw_id(), getDeposit_id(), getAmount(), flatProvision);
    }
}

class FlatPercentProvisionTransaction extends Transaction {
    private int percentage;

    public FlatPercentProvisionTransaction(long fromId, long toId, double amount, int centsPerDolar) {
        super(fromId, toId, "FlatPercent", amount);
        this.percentage = centsPerDolar;
    }

    public int getPercent() {
        return percentage;
    }

    @Override //boolean
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof FlatPercentProvisionTransaction))
            return false;
        FlatPercentProvisionTransaction other = (FlatPercentProvisionTransaction) o;
        return getWithdraw_id() == other.getWithdraw_id()
                && getDeposit_id() == other.getDeposit_id()
                && Double.compare(getAmount(), other.getAmount()) == 0
                && percentage == other.percentage;
    }

    @Override //hash
    public int hashCode() {
        return Objects.hash(getWithdraw_id(), getDeposit_id(), getAmount(), percentage);
    }

}

class Bank {
    private final Account[] accounts;
    private final String name;
    private double totalTransfers;
    private double totalProvision;

    public Bank(String name, Account[] accounts) {
        this.name = name;
        this.accounts =Arrays.copyOf(accounts,accounts.length);
    }

    public Account[] getAccounts() {
        return Arrays.copyOf(accounts,accounts.length);
    }

    private Account findById(long id) {
        for (Account a : accounts) {
            if (a.getID() == id) return a;
        }
        return null;
    }

    public boolean makeTransaction(Transaction t) {
        Account from = findById(t.getWithdraw_id());
        Account to = findById(t.getDeposit_id());

        double amount = t.getAmount();
        double provision = 0.0;

        if (t instanceof FlatAmountProvisionTransaction) {
            FlatAmountProvisionTransaction fa = (FlatAmountProvisionTransaction) t;
            provision = fa.getFlatAmount();
        } else if (t instanceof FlatPercentProvisionTransaction) {
            FlatPercentProvisionTransaction fp = (FlatPercentProvisionTransaction) t;
            provision = amount * fp.getPercent() / 100.0;
        }

        double needed = amount + provision;
        if (from.getBalance() < needed)
            return false;


        from.setBalance(from.getBalance() - needed);
        to.setBalance(to.getBalance() + amount);

        totalProvision+=provision;
        totalTransfers+=amount;
        return true;
    }

    public double totalProvision() {
        return totalProvision;
    }

    public double totalTransfers() {
        return totalTransfers;
    }
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("Name: ").append(name).append("\n\n");
        for (Account a : accounts) sb.append(a.toString());
        return sb.toString();
    }
    @Override
    public boolean equals(Object o){
        if (this==o){
            return true;
        }
        if(o==null || getClass() !=o.getClass()){
            return false;
        }

        Bank other=(Bank) o;
        if(!Objects.equals(this.name,other.name))
            return false;
        if(Double.compare(this.totalTransfers,other.totalTransfers)!=0)
            return false;
        if(Double.compare(this.totalProvision,other.totalProvision)!=0)
            return false;

        if(this.accounts.length!=other.accounts.length)
            return false;
        for (int i = 0; i < accounts.length; i++) {
            if(!Objects.equals(this.accounts[i],other.accounts[i]))
                return false;
        }
        return true;
    }

}

public class BankTester {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        String test_type = jin.nextLine();
        switch (test_type) {
            case "typical_usage":
                testTypicalUsage(jin);
                break;
            case "equals":
                testEquals();
                break;
        }
        jin.close();
    }

    private static double parseAmount(String amount) {
        return Double.parseDouble(amount.replace("$", ""));
    }

    private static void testEquals() {
        Account a1 = new Account("Andrej", 20.0);
        Account a2 = new Account("Andrej", 20.0);
        Account a3 = new Account("Andrej", 30.0);
        Account a4 = new Account("Gajduk", 20.0);
        List<Account> all = Arrays.asList(a1, a2, a3, a4);
        if (!(a1.equals(a1) && !a1.equals(a2) && !a2.equals(a1) && !a3.equals(a1)
                && !a4.equals(a1)
                && !a1.equals(null))) {
            System.out.println("Your account equals method does not work properly.");
            return;
        }
        Set<Long> ids = all.stream().map(Account::getID).collect(Collectors.toSet());
        if (ids.size() != all.size()) {
            System.out.println("Different accounts have the same IDS. This is not allowed");
            return;
        }
        FlatAmountProvisionTransaction fa1 = new FlatAmountProvisionTransaction(10, 20, 20.0, 10.0);
        FlatAmountProvisionTransaction fa2 = new FlatAmountProvisionTransaction(20, 20, 20.0, 10.0);
        FlatAmountProvisionTransaction fa3 = new FlatAmountProvisionTransaction(20, 10, 20.0, 10.0);
        FlatAmountProvisionTransaction fa4 = new FlatAmountProvisionTransaction(10, 20, 50.0, 50.0);
        FlatAmountProvisionTransaction fa5 = new FlatAmountProvisionTransaction(30, 40, 20.0, 10.0);
        FlatPercentProvisionTransaction fp1 = new FlatPercentProvisionTransaction(10, 20, 20.0, 10);
        FlatPercentProvisionTransaction fp2 = new FlatPercentProvisionTransaction(10, 20, 20.0, 10);
        FlatPercentProvisionTransaction fp3 = new FlatPercentProvisionTransaction(10, 10, 20.0, 10);
        FlatPercentProvisionTransaction fp4 = new FlatPercentProvisionTransaction(10, 20, 50.0, 10);
        FlatPercentProvisionTransaction fp5 = new FlatPercentProvisionTransaction(10, 20, 20.0, 30);
        FlatPercentProvisionTransaction fp6 = new FlatPercentProvisionTransaction(30, 40, 20.0, 10);
        if (fa1.equals(fa1) &&
                !fa2.equals(null) &&
                fa2.equals(fa1) &&
                fa1.equals(fa2) &&
                fa1.equals(fa3) &&
                !fa1.equals(fa4) &&
                !fa1.equals(fa5) &&
                !fa1.equals(fp1) &&
                fp1.equals(fp1) &&
                !fp2.equals(null) &&
                fp2.equals(fp1) &&
                fp1.equals(fp2) &&
                fp1.equals(fp3) &&
                !fp1.equals(fp4) &&
                !fp1.equals(fp5) &&
                !fp1.equals(fp6)) {
            System.out.println("Your transactions equals methods do not work properly.");
            return;
        }
        Account accounts[] = new Account[]{a1, a2, a3, a4};
        Account accounts1[] = new Account[]{a2, a1, a3, a4};
        Account accounts2[] = new Account[]{a1, a2, a3};
        Account accounts3[] = new Account[]{a1, a2, a3, a4};

        Bank b1 = new Bank("Test", accounts);
        Bank b2 = new Bank("Test", accounts1);
        Bank b3 = new Bank("Test", accounts2);
        Bank b4 = new Bank("Sample", accounts);
        Bank b5 = new Bank("Test", accounts3);

        if (!(b1.equals(b1) &&
                !b1.equals(null) &&
                !b1.equals(b2) &&
                !b2.equals(b1) &&
                !b1.equals(b3) &&
                !b3.equals(b1) &&
                !b1.equals(b4) &&
                b1.equals(b5))) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        accounts[2] = a1;
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        long from_id = a2.getID();
        long to_id = a3.getID();
        Transaction t = new FlatAmountProvisionTransaction(from_id, to_id, 3.0, 3.0);
        b1.makeTransaction(t);
        if (b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        b5.makeTransaction(t);
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        System.out.println("All your equals methods work properly.");
    }

    private static void testTypicalUsage(Scanner jin) {
        String bank_name = jin.nextLine();
        int num_accounts = jin.nextInt();
        jin.nextLine();
        Account accounts[] = new Account[num_accounts];
        for (int i = 0; i < num_accounts; ++i)
            accounts[i] = new Account(jin.nextLine(), parseAmount(jin.nextLine()));
        Bank bank = new Bank(bank_name, accounts);
        while (true) {
            String line = jin.nextLine();
            switch (line) {
                case "stop":
                    return;
                case "transaction":
                    String descrption = jin.nextLine();
                    double amount = parseAmount(jin.nextLine());
                    double parameter = parseAmount(jin.nextLine());
                    int from_idx = jin.nextInt();
                    int to_idx = jin.nextInt();
                    jin.nextLine();
                    Transaction t = getTransaction(descrption, from_idx, to_idx, amount, parameter, bank);
                    System.out.println("Transaction amount: " + String.format("%.2f$", t.getAmount()));
                    System.out.println("Transaction description: " + t.getDescription());
                    System.out.println("Transaction successful? " + bank.makeTransaction(t));
                    break;
                case "print":
                    System.out.println(bank.toString());
                    System.out.println("Total provisions: " + String.format("%.2f$", bank.totalProvision()));
                    System.out.println("Total transfers: " + String.format("%.2f$", bank.totalTransfers()));
                    System.out.println();
                    break;
            }
        }
    }

    private static Transaction getTransaction(String description, int from_idx, int to_idx, double amount, double o, Bank bank) {
        switch (description) {
            case "FlatAmount":
                return new FlatAmountProvisionTransaction(bank.getAccounts()[from_idx].getID(),
                        bank.getAccounts()[to_idx].getID(), amount, o);
            case "FlatPercent":
                return new FlatPercentProvisionTransaction(bank.getAccounts()[from_idx].getID(),
                        bank.getAccounts()[to_idx].getID(), amount, (int) o);
        }
        return null;
    }


}

package stateDP;

import java.util.ArrayList;
import java.util.List;

class Product {
    String title;
    double price;

    public Product(String title, double price) {
        this.title = title;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{title=" + title + ", price=" + price + "}";
    }
}

interface State {
    // TODO: тука ти треба да ги додадеш методите
    void insertCoin(double amount);

    void selectProduct(int index);

    void dispense();

    void cancel();
}

class VendingMachine {
    List<Product> products;
    double insertedAmount;
    int selectIndex;

    // TODO: полиња за состојбите (idle, hasCoins, dispensing...)

    State idle;
    State hasCoins;
    State dispensing;

    final void createStates() {
        idle = new IdleState(this);
        hasCoins = new HasCoinsState(this);
        dispensing = new DispensingState(this);
        state = idle;
    }

    State state;

    public State getState() {
        return state;
    }

    public State getDispensing() {
        return dispensing;
    }

    public State getHasCoins() {
        return hasCoins;
    }

    public State getIdle() {
        return idle;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getSelectedIndex() {
        return selectIndex;
    }

    public void setSelectedIndex(int index) {
        this.selectIndex = index;
    }


    public VendingMachine(List<Product> products) {
        this.products = products;
        this.insertedAmount = 0;
        createStates();
    }

    public double getInsertedAmount() {
        return insertedAmount;
    }

    public void setInsertedAmount(double amount) {
        this.insertedAmount = amount;
    }

    public void insertCoin(double amount) {
        state.insertCoin(amount);
    }

    public void selectProduct(int index) {
        state.selectProduct(index);
    }

    public void dispense() {
        state.dispense();
    }

    public void cancel() {
        state.cancel();
    }

    public Product getProduct(int index) {
        return products.get(index);
    }

    @Override
    public String toString() {
        return "VendingMachine{ insertedAmount=" + insertedAmount + ", products=" + products + "}";
    }
}

class IdleState extends AbstractState2 {

    public IdleState(VendingMachine vm) {
        super(vm);
    }

    @Override
    public void insertCoin(double amount) {
        vm.setInsertedAmount(amount + vm.getInsertedAmount());
        System.out.println("Amount inserted: " + vm.getInsertedAmount());
        vm.setState(vm.getHasCoins());
    }

    @Override
    public void selectProduct(int index) {
        System.out.println("Please insert coins");
    }

    @Override
    public void dispense() {
        System.out.println("cannot dispense now");
    }

    @Override
    public void cancel() {
        System.out.println("nothing to cancel");
    }
}

class HasCoinsState extends AbstractState2 {

    public HasCoinsState(VendingMachine vm) {
        super(vm);
    }

    @Override
    public void insertCoin(double amount) {
        vm.setInsertedAmount(vm.getInsertedAmount() + amount);
        System.out.println("Amount inserted: " + vm.getInsertedAmount());
    }

    @Override
    public void selectProduct(int index) {
        Product p = vm.getProduct(index);
        if (vm.getInsertedAmount() >= p.price) {
            System.out.println("Product " + index + " selected");
            vm.setSelectedIndex(index);
            vm.setState(vm.getDispensing());
        } else {
            System.out.println("insufficient funds");
        }
    }

    @Override
    public void dispense() {
        System.out.println("cannot dispense now");
    }

    @Override
    public void cancel() {
        System.out.println("Returning " + vm.getInsertedAmount());
        vm.setInsertedAmount(0);
        vm.setState(vm.getIdle());
    }
}
class DispensingState extends AbstractState2 {
    public DispensingState(VendingMachine vm) {
        super(vm);
    }

    @Override
    public void insertCoin(double amount) {
        System.out.println("cannot insert now");
    }

    @Override
    public void selectProduct(int index) {
        System.out.println("illegal action");
    }

    @Override
    public void dispense() {
        Product p=vm.getProduct(vm.getSelectedIndex());
        System.out.println("dispensing product: "+p.title);
        double change=vm.getInsertedAmount()-p.price;
        if (change>0){
            System.out.println("change: "+change);
        }
        vm.setInsertedAmount(0);
        vm.setState(vm.getIdle());
    }

    @Override
    public void cancel() {
        System.out.println("returning: "+vm.getInsertedAmount());
        vm.setInsertedAmount(0);
        vm.setState(vm.getIdle());
    }
}
abstract class AbstractState2 implements State {
    VendingMachine vm;

    public AbstractState2(VendingMachine vm) {
        this.vm = vm;
    }
}

public class VendingTest {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Chips", 1.50));
        products.add(new Product("Soda", 2.00));

        VendingMachine vm = new VendingMachine(products);

        vm.selectProduct(0);       // "Please insert coins first"
        vm.insertCoin(1.00);       // "Amount inserted: 1.0"
        vm.selectProduct(0);       // "Insufficient funds"
        vm.insertCoin(1.00);       // "Amount inserted: 2.0"
        vm.selectProduct(0);       // "Product 0 selected"
        vm.dispense();             // "Dispensing product: Chips", "Returning change: 0.5"
        vm.cancel();                // "Nothing to cancel"
    }
}

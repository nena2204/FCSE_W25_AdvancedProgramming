package aud_2;

interface Operation {
    int apply(int a, int b);
}

//klasa koja go implementira interfejsot
class Addition implements Operation {
    @Override
    public int apply(int a, int b) {
        return a + b;
    }
}

interface MessageProvider {
    String getMessage();
}

//klasa koja go implementira interfejsot
class StaticMessage implements MessageProvider {
    @Override
    public String getMessage() {
        return "Hello world!";
    }
}

public class InterfaceDemo {
    public static void main(String[] args) {
        // interface with arguments
        Operation op1 = new Addition();
        System.out.println("Addition: " + op1.apply(5, 3));

        Operation op2 = new Operation() {
            @Override
            public int apply(int a, int b) {
                return a * b;
            }
        };
        System.out.println("Multiply: " + op2.apply(5, 3));

        Operation op3 = (a, b) -> a - b;
        System.out.println("Subtract: "+op3.apply(5,3));

        //INTERFACE WITHOUT ARGUMENTS
        MessageProvider m1=new StaticMessage();
        System.out.println(m1.getMessage());

        MessageProvider m2=new MessageProvider() {
            @Override
            public String getMessage() {
                return "Hello from anonymous class!!";
            }
        };

        System.out.println(m2.getMessage());

        MessageProvider m3=()->"Hello from lambda!!!";
        System.out.println(m3.getMessage());
    }
}

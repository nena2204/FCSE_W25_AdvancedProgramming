package aud_2;

import java.util.Scanner;

interface Operation1 {
    Double apply(double currentResult, double value);
}

class Operationfactory {
    private static final char ADD_op = '+';
    private static final char SUBTRACT_op = '-';
    private static final char MULTIPLY_op = '*';
    private static final char DIVIDE_op = '/';

    //static lambda
    private static final Operation1 ADD = (r, v) -> r + v;
    private static final Operation1 SUBTRACT = (r, v) -> r - v;
    private static final Operation1 MULTIPLY = (r, v) -> r * v;
    private static final Operation1 DIVIDE = (r, v) -> r / v;

    //FACTORY
    public static Operation1 getOperation(char operator) throws Calculator.UnknownOperatorException{
        if(operator==ADD_op){
            return ADD;
        }else if (operator==SUBTRACT_op){
            return SUBTRACT;
        }else if (operator==MULTIPLY_op){
            return MULTIPLY;
        }else if (operator==DIVIDE_op){
            return DIVIDE;
        }else{
            throw new Calculator.UnknownOperatorException(operator);
        }
    }
}

//simple calculator
public class Calculator{
    private double result;

    public Calculator() {
        result = 0.0;
    }

    public String init() {
        return String.format("result = %.2f", result);
    }

    public double getResult() {
        return result;
    }

    public static class UnknownOperatorException extends Exception {
        public UnknownOperatorException(char op) {
            super(op + " is an unknown operation");
        }
    }
    public String execute(char operator, double value) throws UnknownOperatorException {
        Operation1 op = Operationfactory.getOperation(operator);
        result = op.apply(result, value);
        return String.format("result %c %.2f = %.2f", operator, value, result);
    }
    public String toString(){
        return String.format("updated result = %.2f", this.getResult());
    }

    public static void main(String[] args) {
        System.out.println("Calculator is on.");

        Scanner sc = new Scanner(System.in);
        while (true) {
            Calculator calculator=new Calculator();
            System.out.println(calculator.init());


            while (true) {
                String input = sc.nextLine().trim();
                if (input.isEmpty())
                    continue;

                //dALI da break-ne
                char c = Character.toLowerCase(input.charAt(0));
                if (c == 'r') {
                    System.out.printf("Final result = %.1f%n", calculator.getResult());
                    break;
                }

                String[] parts=input.split("\\s+");
                if (parts.length>2){
                    System.out.println("please enter: <operator> <number>");
                    continue;
                }
//        System.out.println(op);
//        System.out.println(value);
                char operator=parts[0].charAt(0);
                double value=Double.parseDouble(parts[1]);

                //calculate
                try {
                    String result=calculator.execute(operator,value);
                    System.out.println(result);
                    System.out.println(calculator.toString());
                } catch (UnknownOperatorException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Reenter, your last line: ");
                }

            }
            System.out.println("Again? (y/n)");
            String again = sc.nextLine().trim();
            if (!again.isEmpty() && Character.toLowerCase(again.charAt(0)) == 'n') {
                System.out.println("End of program");
                break;
            }
            //yes za nova presmetka
        }
    }

}

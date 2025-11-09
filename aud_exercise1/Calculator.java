package aud_1.firstEx;

import java.util.Scanner;

//simple calculator
public class Calculator {
    private double result=0.0;
    private static final char ADD='+';
    private static final char SUBTRACT='-';
    private static final char MULTIPLY='*';
    private static final char DIVIDE='/';

    public Calculator(){
        result=0.0;
    }

    public double getResult() {
        return result;
    }
    public static class UnknownOperatorException extends Exception{
        public UnknownOperatorException(char op){
            super(op + " is an unknown operation");
        }
    }

    public static void main(String[] args) {
        System.out.println("Calculator is on.");

        Scanner sc = new Scanner(System.in);
        while (true) {
            double result = 0.0;
            System.out.println("result = " + result);
            while (true) {
                String input = sc.nextLine().trim();
                if(input.isEmpty())
                    continue;

                //dALI da break-ne
                char c=Character.toLowerCase(input.charAt(0));
                if(c=='r'){
                    System.out.println("Final result = "+result);
                    break;
                }

                char op = input.charAt(0);
                double value = Double.parseDouble(input.substring(1).trim());
//        System.out.println(op);
//        System.out.println(value);

                //calculate
                try {
                    if (op == ADD) {
                        result += value;
                    } else if (op == SUBTRACT) {
                        result -= value;
                    } else if (op == MULTIPLY) {
                        result *= value;
                    } else if (op == DIVIDE) {
                        result /= value;
                    } else {
                        throw new UnknownOperatorException(op);
                    }
                }
                catch (UnknownOperatorException e){
                    System.out.println(e.getMessage()+".");
                    System.out.println("Reenter, your last line:");
                    continue;
                }
                System.out.println("result " + op + value + " = " + result);
                System.out.println("updated result= " + result);
            }

            System.out.println("Again? (y/n)");
            String again=sc.nextLine().trim();
            if(!again.isEmpty() && Character.toLowerCase(again.charAt(0))=='n'){
                System.out.println("End of program");
                break;
            }
            //yes za nova presmetka
        }
    }
}

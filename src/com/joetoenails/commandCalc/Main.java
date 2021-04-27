package com.joetoenails.commandCalc;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        double[] leftVals = {100.0d,25.0d,225.0d,11.0d};
        double[] rightVals = {50.0d,20.0d,100.0d,45.0d};
        char [] opCodes = {'d','a','s','m'};
        double[] results = new double[opCodes.length];

        if (args.length==0){
            for (int i = 0; i < opCodes.length; i++) {
                results[i] = execute(opCodes[i], leftVals[i], rightVals[i]);
            }

            for (double num : results){
                System.out.println(num);

            }
        }
        else if(args.length==1 && args[0].equals("interactive")){
            executeInteractive();
        }
        else if(args.length==3){
            handleCommandLine(args);
        }

        else{
            System.out.println("Incorrect number of args.");
        }

    }

    static void executeInteractive(){
        System.out.println("Enter an operation and two numbers:");
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        String[] parts = userInput.split(" ");
        performOperation(parts);
    }

    private static void performOperation(String[] parts) {
        char opCode = opCodeFromString(parts[0]);

        if (opCode=='w'){
            handleWhen(parts);
        }
        else {

            double leftVal = numFromString(parts[1]);
            double rightVal = numFromString(parts[2]);

            Map<String, String> charCodes = Map.of("a", "+", "s", "-", "d", "/", "m", "*");
            String stringCode = charCodes.get(Character.toString(opCode));

            double result = execute(opCode, leftVal, rightVal);
            String finalAns = resultBuilder(stringCode, leftVal, rightVal, result);
            System.out.println(finalAns);
        }
    }

    private static void handleWhen(String[] parts) {
        LocalDate startDate = LocalDate.parse(parts[1]);
        long daysToAdd = (long) numFromString(parts[2]);

        LocalDate newDate = startDate.plusDays(daysToAdd);

        String output = String.format("%s plus %d days is %s!",startDate,daysToAdd,newDate);
        System.out.println(output);
    }

    private static String resultBuilder(String stringCode,double leftVal,double rightVal,double result){
        return String.format("%.1f %s %.1f = %.1f",leftVal,stringCode,rightVal,result);
    }

    private static void handleCommandLine(String[] args) {

        char opCode = args[0].charAt(0);
        double leftVal = Double.parseDouble(args[1]);
        double rightVal = Double.parseDouble(args[2]);
        System.out.println(execute(opCode,leftVal,rightVal));

    }

    static double execute(char opCode,double leftVal, double rightVal){
        double result;
        switch (opCode) {
            case 'a':
                result = leftVal + rightVal;
                break;
            case 's':
                result = leftVal - rightVal;
                break;
            case 'm':
                result = leftVal * rightVal;
                break;
            case 'd':
                result = rightVal!= 0 ? leftVal / rightVal : 0.0d;
                break;
            default:
                result = 0.0d;
                break;
        }
        return result;
    }

    static char opCodeFromString(String operationName){
        return operationName.toLowerCase(Locale.ROOT).charAt(0);

    }
    static double numFromString(String numString){
        String[] numList = {"zero","one","two","three","four","five","six","seven","eight","nine"};
        double result = java.util.Arrays.asList(numList).indexOf(numString);

        if (result==-1){
            return Double.parseDouble(numString);
        }
        return result;
    }
}




package lastpencil;
import java.util.Scanner;

public class Main {
    static StringBuilder stringPencils = new StringBuilder();
    static Scanner sc = new Scanner(System.in);
    static String[] player = {"John", "Jack"};
    static String playerName = "";
    static int pencilQuantity = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("How many pencils would you like to use:");

        boolean pencilQuantityEnd = true;
        while (pencilQuantityEnd) {
            try {
                String pencils = sc.nextLine();
                pencilQuantity = Integer.parseInt(pencils);
                if (pencilQuantity > 0 && !pencils.isEmpty())
                    pencilQuantityEnd = false;
                else if (pencils.isEmpty())
                    throw new NumberFormatException();
                else
                    System.out.println("The number of pencils should be positive");
            } catch (Exception e) {
                System.out.println("The number of pencils should be numeric");
            }
        }

        System.out.printf("Who will be the first (%s, %s)%n", player[0], player[1]);
        while (playerName.isEmpty()) {
            String playerIndexOrName = sc.nextLine();
            try {
                playerName = player[Integer.parseInt(playerIndexOrName) - 1];
            } catch (NumberFormatException e) {
                if (player[0].equals(playerIndexOrName)) {
                    playerName = player[0];
                }
                else if (player[1].equals(playerIndexOrName)) {
                    playerName = player[1];
                }
                else {
                    System.out.println("Choose between '" + player[0] + "' and '" + player[1] + "'");
                }
            }
        }

        String botName = player[1];
        stringPencils = generateStringPencils(pencilQuantity);

        while (true) {
            System.out.println(playerName + "'s turn");

            if(isBot(playerName, botName)) {
                removePencilsQuantity(botsMove(stringPencils.length()));
            } else {
                removePencilsQuantity(inputPlayer());
            }
            if(stringPencils.isEmpty()){
                System.out.println(playerName + " won!");
                break;
            }
        }


    }

    public static StringBuilder generateStringPencils(int pencilQuantity){
        StringBuilder sb = new StringBuilder("|".repeat(pencilQuantity));
        System.out.println(sb);
        return sb;
    }

    public static boolean isBot(String playerName, String botName){
        return playerName.equals(botName);
    }

    public static boolean winningPosition(int pencilsQuantity){
        boolean bool = false;
        if(pencilsQuantity % 4 == 0 || (pencilsQuantity + 1) % 4 == 0 || (pencilsQuantity + 2) % 4 == 0 )
            return true;
        return bool;
    }

    public static int botsMove(int quantity){
        int botQuantityRemove = 1;
        if(winningPosition(quantity)) {
            if (quantity % 4 == 0)
                botQuantityRemove = 3;
            else if ((quantity + 1) % 4 == 0)
                botQuantityRemove = 2;
            else if ((quantity + 2) % 4 == 0)
                botQuantityRemove = 1;
        } else {
            if(quantity >= 3)
                botQuantityRemove = (int) ((Math.random() * (3 - 1)) + 1);
        }
        System.out.println(botQuantityRemove);
        return botQuantityRemove;
    }

    public static int inputPlayer(){
        int pencilsQuantitySelectedByPlayer = 0;
        do {
            try{
                String userInput = "";
                while (userInput.isEmpty()) {
                    userInput = sc.nextLine();
                }
                pencilsQuantitySelectedByPlayer = Integer.parseInt(userInput);
                if (pencilsQuantitySelectedByPlayer > 3 || pencilsQuantitySelectedByPlayer <= 0)
                    throw new NumberFormatException();
            } catch (NumberFormatException e){
                pencilsQuantitySelectedByPlayer = 0;
                generateStringPossibilities();
            }
        } while (pencilsQuantitySelectedByPlayer < 1);
        return pencilsQuantitySelectedByPlayer;
    }

    private static void removePencilsQuantity(int pencilsQuantityToRemove){
        if (stringPencils.length() - pencilsQuantityToRemove >= 0) {
            removePencils(stringPencils, pencilsQuantityToRemove);
            playerName = playerName.equals(player[0]) ? player[1] : player[0];
        }
        else System.out.println("Too many pencils were taken");
    }

    private static void removePencils(StringBuilder stringPencils, int quantityToRemove){
        stringPencils.delete(stringPencils.length() - quantityToRemove, stringPencils.length());
        if(!stringPencils.isEmpty())
            System.out.println(stringPencils);
    }

    private static void generateStringPossibilities(){
        System.out.println("Possible values: '1', '2' or '3'");
    }
}
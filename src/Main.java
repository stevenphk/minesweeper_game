
import java.util.Arrays;
import java.util.Scanner;

public class Main{

    public static int TEMP = 20;
    public static int LENGTH = 10;
    public static int game[][] = new int[LENGTH][LENGTH];
    public static String display[][] = new String[LENGTH][LENGTH];
    public static int xAxis [] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    public static int yAxis [] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    public static boolean isMine = false;
    public static int mineNum = (int)(LENGTH * LENGTH * 0.1);
    public static int emptyNum = LENGTH * LENGTH - TEMP;
    public static boolean win = false;

    public static void main(String[] args) {
        generateGame();

        while (!isMine && !win) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();
            int y = Integer.parseInt(input.substring(1, 2));
            int x = Integer.parseInt(input.substring(2, 3));

            if (win || isMine) {
                break;
            }
            else if (input.charAt(0) == 'f'){
                putFlag(x, y);
                displayArray();
                System.out.println("Empty cells left: " + emptyNum);
            } else if(input.charAt(0) == 's'){
                revealCell(x, y);
                displayArray();
                System.out.println("Empty cells left: " + emptyNum);
            }
        }

        if(isMine) System.out.println("*************You lost!!! Git gud!*************");
        else if(win) System.out.println("*************You win! Congratulations!!*************");
    }

    public static void displayArray(){
        System.out.print("  ");
        for (int i = 0; i < xAxis.length; i++){
            System.out.print(xAxis[i] + " ");
        }
        for (int i = 0; i < display.length; i++){
            System.out.println();
            System.out.print(yAxis[i] + " ");
            for (int j = 0; j < display[i].length; j++){
                System.out.print(display[i][j] + " ");
            }
        }
        System.out.println();
    }

    public static void generateGame(){
        //display guidelines
        System.out.println("\nWelcome to minesweeper!");
        System.out.println("To play the game, enter 1 character and 2 digits, e.g. s11, f22, etc.");
        System.out.println("For minesweeping, use 's'; for flagging, use 'f'.\n");

        //create game and display array
        for (int i = 0; i < game.length; i++){
            for (int j = 0; j < game[i].length; j++){
                game[i][j] = 0;
            }
        }
        for (int i = 0; i < display.length; i++){
            for (int j = 0; j < display[i].length; j++){
                display[i][j] = "-";
            }
        }

        //generate mines
        //may have duplicate mines


        for (int n = mineNum; n > 0; n--) {
            game[(int) (Math.random() * 10)][(int) (Math.random() * 10)] = 1;
        }


        /*
        for (int n = 9; n >= 0; n--){
           game[0][n] = 1;
           game[4][n] = 1;
        }

         */


        displayArray();
    }


    public static void putFlag(int x, int y){
        if (display[x][y].equals("-")) display[x][y] = "?";
        else display[x][y] = "-";
    }

    public static void revealCell(int x, int y){
        //System.out.println("revealCell");
        if (display[x][y].equals("?")) {
            System.out.println("This is a flag");
        } else if (game[x][y] == 0){
            int count = calSum(x, y);
            display[x][y] = String.valueOf(count);
            emptyNum--;
            if (emptyNum == 0){
                //System.out.println("*************You win! Congratulations!!*************");
                win = true;
            }
            else if (count == 0){
                revealNear(x, y);
            }
        } else {
            isMine = true;
            for(int i = 0; i < display.length; i++){
                Arrays.fill(display[i], "B");
            }
            //System.out.println("*************You lost!!! Git gud!*************");
        }
    }
    public static void revealNear(int x, int y){
        //System.out.println("revealNear");
        for (int i = x-1; i <= x+1; i++){
            if (i <= 9 && i >= 0){
                for (int j = y-1; j <= y+1; j++){
                    if (j <= 9 && j >= 0){
                        //skip revealed block
                        if (!display[i][j].equals("-")) {
                            continue;
                        }
                        else {
                            revealCell(i ,j);
                        }
                    }
                }
            }
        }
    }

    public static int calSum(int x, int y){
        int count = 0;
        for (int i = x-1; i <= x+1; i++){
            if (i >= 0 && i <= 9){
                for (int j = y-1; j <= y+1; j++){
                    if (j >= 0 && j <= 9) {
                        if (game[i][j] == 1) count++;
                    }
                }
            }
        }
        return count;
    }


}


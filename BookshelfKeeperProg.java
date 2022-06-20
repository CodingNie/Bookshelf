// Name: Ning Nie
// USC NetID: nnie
// CSCI455 PA2
// Spring 2022

import java.util.Scanner;
/**
 * Class BookshelfKeeperProg
 *
 * main method contains in this class. The BookshelfKeeperProg enables users perform a series of
 * put and pick operations and show the bookshelf's current status after each operation, program can also exit when type in "end".
 * It enables error checking and can print them out, some type of spaces are valid.
 * It can also be run in a batch mode by using input and output redirection.
 */
public class BookshelfKeeperProg {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Bookshelf books = new Bookshelf();
        BookshelfKeeper booksKeeper = new BookshelfKeeper(books);
        //define a "flag" to indicate whether it is the initial arrangement input
        //true for initial input and show the prompt of new commands
        boolean[] initialize = new boolean[1];
        initialize[0] = true;
        System.out.println("Please enter initial arrangement of books followed by newline:");
        runTester(in, books, booksKeeper, initialize);
    }
    /**
     * runTester function starts from the user interface and test all kinds of the user input continuously
     * all the PRE are same as requested, books and booksKeeper should be valid input
     */
    private static void runTester(Scanner in, Bookshelf books, BookshelfKeeper booksKeeper, boolean[] initialize){
        outerloop:
        while (in.hasNextLine()){
            // read the whole line in one time to string
            String s = in.nextLine();
            if (isEnd(s)){
                break;
            }
            //scan the one line string
            Scanner line = new Scanner(s);
            while (line.hasNext()){
                String next = line.next();
                //if the first element is number, this is the initial arrangement of the books
                if (isNumber(next)){
                    int value = Integer.parseInt(next);
                    if (!isPositive(value)){break outerloop;}
                    books.addLast(value);
                }else{
                    // if the first element is command, check the type and execute
                    initialize[0] = false;
                    if (next.equals("put")){
                        int value = line.nextInt();
                        if (isNegativePut(value)){ break outerloop;}
                        booksKeeper.putHeight(value);
                    }else if (next.equals("pick")){
                        int value = line.nextInt();
                        if (!isValidPick(value, booksKeeper)){ break outerloop;}
                        booksKeeper.pickPos(value);
                    }else{
                        printInvalidCommand();
                        break outerloop;
                    }
                }
            }
            if (!isSorted(books)){break;}
            System.out.println(booksKeeper.toString());
            if(initialize[0]){
                System.out.println("Type pick <index> or put <height> followed by newline. Type end to exit.");
            }
        }
    }
    /**
     * isNumber is to check both positive and negative numbers in the string
     * positive number: if the first char of the string is number;
     * negative number: if the first char of the string is '-' and the second char of the string is number;
     */
    private static boolean isNumber(String s){
        boolean nonNegativeNum = Character.isDigit(s.charAt(0));
        boolean negativeNum = s.charAt(0) == ('-') && Character.isDigit(s.charAt(1));
        return Character.isDigit(s.charAt(0)) || negativeNum;
    }
    /**
     * isEnd is to check whether the input line string is "end" and print the message;
     */
    private static boolean isEnd(String s){
        if (s.equals("end")){
            System.out.println("Exiting Program.");
            return true;
        }
        return false;
    }
    /**
     * isNegativePut is to check whether the input after the command "put" is negative;
     * if it is negative, print the invalid message;
     */
    private static boolean isNegativePut(int height){
        if (height <= 0){
            System.out.println("ERROR: Height of a book must be positive.");
            System.out.println("Exiting Program.");
            return true;
        }
        return false;
    }
    /**
     * isValidPick is to check whether the input index after the command "pick" is valid;
     * valid index should be non-negative and within the boundary of the number of books;
     * print the invalid message when the index is out of boundary;
     */
    private static boolean isValidPick(int index, BookshelfKeeper booksKeeper){
        if (index <0 || index >= booksKeeper.getNumBooks()){
            System.out.println("ERROR: Entered pick operation is invalid on this shelf.");
            System.out.println("Exiting Program.");
            return false;
        }
        return true;
    }
    /**
     * printInvalidCommand is to print the error message when the command type is invalid;
     */
    private static void printInvalidCommand(){
        System.out.println("ERROR: Invalid command. Valid commands are pick, put, or end.");
        System.out.println("Exiting Program.");
    }
    /**
     * isPositive is to check whether the bookshelf has the negative height of books;
     * print the invalid message when the height is not positive;
     */
    private static boolean isPositive(int value){
        if (value <= 0){
            System.out.println("ERROR: Height of a book must be positive.");
            System.out.println("Exiting Program.");
            return false;
        }
        return true;
    }
    /**
     * isSorted is to check whether the books are in increasing order;
     * print the valid message when the order is not valid.
     */
    private static boolean isSorted(Bookshelf books){
        if (!books.isSorted()){
            System.out.println("ERROR: Heights must be specified in non-decreasing order.");
            System.out.println("Exiting Program.");
            return false;
        }
        return true;
    }
}

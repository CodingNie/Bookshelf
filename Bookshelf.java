// Name: Ning Nie
// USC NetID: nnie
// CSCI455 PA2
// Spring 2022


/**
 * Class Bookshelf
 * Implements idea of arranging books into a bookshelf.
 * Books on a bookshelf can only be accessed in a specific way so books don’t fall down;
 * You can add or remove a book only when it’s on one of the ends of the shelf.
 * However, you can look at any book on a shelf by giving its location (starting at 0).
 * Books are identified only by their height; two books of the same height can be
 * thought of as two copies of the same book.
 */
import java.util.ArrayList;

public class Bookshelf {

    /**
     Representation invariant:

     the pileOfBooks stores the book lists, books in it must be positive；
     the arraylist can not be null;
     */

    // <add instance variables here>
    private ArrayList<Integer> pileOfBooks;


    /**
     * Creates an empty Bookshelf object i.e. with no books
     */
    public Bookshelf() {
        pileOfBooks = new ArrayList<Integer>();
        assert isValidBookshelf();  // sample assert statement (you will be adding more of these calls)
    }

    /**
     * Creates a Bookshelf with the arrangement specified in pileOfBooks. Example
     * values: [20, 1, 9].
     *
     * PRE: pileOfBooks contains an array list of 0 or more positive numbers
     * representing the height of each book.
     */
    public Bookshelf(ArrayList<Integer> pileOfBooks) {
        ArrayList<Integer> copy = new ArrayList<>(pileOfBooks);
        this.pileOfBooks = copy;
        assert isValidBookshelf();
    }

    /**
     * Inserts book with specified height at the start of the Bookshelf, i.e., it
     * will end up at position 0.
     *
     * PRE: height > 0 (height of book is always positive)
     */
    public void addFront(int height) {
        this.pileOfBooks.add(0, height);
        assert isValidBookshelf();
    }

    /**
     * Inserts book with specified height at the end of the Bookshelf.
     *
     * PRE: height > 0 (height of book is always positive)
     */
    public void addLast(int height) {
        this.pileOfBooks.add(height);
        assert isValidBookshelf();
    }

    /**
     * Removes book at the start of the Bookshelf and returns the height of the
     * removed book.
     *
     * PRE: this.size() > 0 i.e. can be called only on non-empty BookShelf
     */
    public int removeFront() {
        int height = pileOfBooks.remove(0);
        assert isValidBookshelf();
        return height;

    }

    /**
     * Removes book at the end of the Bookshelf and returns the height of the
     * removed book.
     *
     * PRE: this.size() > 0 i.e. can be called only on non-empty BookShelf
     */
    public int removeLast() {
        int size = pileOfBooks.size();
        int height = pileOfBooks.remove(size - 1);
        assert isValidBookshelf();
        return height;
    }

    /*
     * Gets the height of the book at the given position.
     *
     * PRE: 0 <= position < this.size()
     */
    public int getHeight(int position) {
        int height = pileOfBooks.get(position);
        assert isValidBookshelf();
        return height;

    }

    /**
     * Returns number of books on the this Bookshelf.
     */
    public int size() {
        assert isValidBookshelf();
        return pileOfBooks.size();
    }

    /**
     * Returns string representation of this Bookshelf. Returns a string with the height of all
     * books on the bookshelf, in the order they are in on the bookshelf, using the format shown
     * by example here:  “[7, 33, 5, 4, 3]”
     */
    public String toString() {
        if (this.size () == 0){
            assert isValidBookshelf();
            return "[]";
        }
        String s1 = "[";
        String s2 = "";
        String s3 = pileOfBooks.get(pileOfBooks.size() - 1).toString();
        String s4 = "]";
        for (int i = 0; i < pileOfBooks.size() - 1;i++){
            s2 = s2 + pileOfBooks.get(i).toString() + ", ";
        }
        assert isValidBookshelf();
        return s1+s2+s3+s4;

    }

    /**
     * Returns true iff the books on this Bookshelf are in non-decreasing order.
     * (Note: this is an accessor; it does not change the bookshelf.)
     */
    public boolean isSorted() {
        if (size() == 0 || size() == 1){
            assert isValidBookshelf();
            return true;
        }
        for (int i = 0; i < pileOfBooks.size() - 1; i++){
            if (pileOfBooks.get(i) > pileOfBooks.get(i + 1)){
                assert isValidBookshelf();
                return false;
            }
        }
        assert isValidBookshelf();
        return true;  // dummy code to get stub to compile
    }
    /**
     * Returns true iff all the Bookshelf data are positive.
     */
    public boolean isPositive() {
        for (Integer i : pileOfBooks) {
            if (i <= 0) {
                return false;
            }
        }
        return true;
    }
    /**
     * Returns true iff the Bookshelf data is in a valid state.
     * (See representation invariant comment for more details.)
     */
    private boolean isValidBookshelf() {
        if (pileOfBooks == null){
            System.out.println("ERROR: The pileOfBooks can not be null.");
            return false;
        }

        if (!isPositive()){
            System.out.println("ERROR: Height of a book must be positive.");
            return false;
        }
        return true;
    }
}

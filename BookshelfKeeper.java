// Name:Ning Nie
// USC NetID: nnie
// CSCI455 PA2
// Spring 2022


/**
 * Class BookshelfKeeper
 *
 * Enables users to perform efficient putPos or pickHeight operation on a bookshelf of books kept in
 * non-decreasing order by height, with the restriction that single books can only be added
 * or removed from one of the two *ends* of the bookshelf to complete a higher level pick or put
 * operation.  Pick or put operations are performed with minimum number of such adds or removes.
 */
public class BookshelfKeeper {

    /**
     Representation invariant:

     the bookshelf can not be null;
     the bookshelf is sorted non-decreasing with positive height, if the bookshelf is not empty;
     the bookshelf is sorted non-decreasing after each mutator call;
     the lastOperation is no more than the totalOperation, and both of them are non-negative;
     */

    // <add instance variables here>
    //the totalOperations is the number of mutator calls from the last pickPos or putHeight since the bookshelf created;
    //the lastOperations is the number of mutator calls from the last pickPos or putHeight operation;
    private Bookshelf bookshelf;
    private int totalOperations;
    private int lastOperations;
    /**
     * Creates a BookShelfKeeper object with an empty bookshelf
     */
    public BookshelfKeeper() {
        this.bookshelf = new Bookshelf();
        this.totalOperations = 0;
        assert isValidBookshelfKeeper();
    }

    /**
     * Creates a BookshelfKeeper object initialized with the given sorted bookshelf.
     * Note: method does not make a defensive copy of the bookshelf.
     *
     * PRE: sortedBookshelf.isSorted() is true.
     */
    public BookshelfKeeper(Bookshelf sortedBookshelf) {
        this.bookshelf = sortedBookshelf;
        this.totalOperations = 0;
        assert isValidBookshelfKeeper();
    }


    /**
     * Removes a book from the specified position in the bookshelf and keeps bookshelf sorted
     * after picking up the book.
     *
     * Returns the number of calls to mutators on the contained bookshelf used to complete this
     * operation. This must be the minimum number to complete the operation.
     *
     * PRE: 0 <= position < getNumBooks()
     */
    public int pickPos(int position) {
        // position is beyond the boundary
        if (position >= this.bookshelf.size()){
            return 0;
        }
        int[] numCalls = new int[1];
        int curSize = this.getNumBooks();
        int mid = (curSize - 1) / 2;
        // pick the book from the left side will cost less
        if (position <= mid){
            // store the books temporarily removed on the left side, including the book in the position;
            int[] partialBooks = new int[position + 1];
            // remove the book on the left side increasingly, including the book in the position;
            partialBooksRemove(partialBooks, numCalls, 0, position, true);
            //put back the books on the left side decreasingly, excluding the book in the position;
            partialBooksInsert(partialBooks, numCalls, 0, position - 1, true);
        }else{ // pick the book from the right side will cost less
            // store the books temporarily removed on the right side, including the book in the position;
            int length = curSize - position;
            int[] partialBooks = new int[length];
            // remove the book on the right side decreasingly, including the book in the position;
            partialBooksRemove(partialBooks, numCalls, 0, length - 1, false);
            //put back the books on the right side increasingly, excluding the book in the position;
            partialBooksInsert(partialBooks, numCalls, 1, length - 1, false);
        }
        this.totalOperations += numCalls[0];
        this.lastOperations = numCalls[0];
        assert isValidBookshelfKeeper();
        return numCalls[0];
    }

    /**
     * Inserts book with specified height into the shelf.  Keeps the contained bookshelf sorted
     * after the insertion.
     *
     * Returns the number of calls to mutators on the contained bookshelf used to complete this
     * operation. This must be the minimum number to complete the operation.
     *
     * PRE: height > 0
     */
    public int putHeight(int height) {
        int[] numCalls = new int[1];
        int startPosition = 0; // startPosition is the first position suitable for insert;
        int endPosition = 0;   // endPosition is the second position suitable for insert, given duplicate elements;
        int curSize = this.getNumBooks();
        //find the suitable location to insert
        while (startPosition <= curSize - 1 && this.bookshelf.getHeight(startPosition) < height){
            startPosition++;
        }
        if (startPosition < curSize){
            if (this.bookshelf.getHeight(startPosition) == height){
                // duplicate element exists, we need to locate the second position for insert
                endPosition = startPosition;
                while (endPosition < curSize && this.bookshelf.getHeight(endPosition) == height){
                    endPosition++;
                }
                compareAndPut(startPosition, curSize - endPosition, numCalls, height);
//                if (startPosition <= curSize - endPosition){
//                    //distance to the leftmost duplicate element is near
//                    int[] partialBooks = new int[startPosition];
//                    partialBooksRemove(partialBooks, numCalls, 0, startPosition - 1, true);
//                    this.bookshelf.addFront(height);
//                    numCalls[0]++;
//                    partialBooksInsert(partialBooks, numCalls, 0, startPosition - 1, true);
//                }else{
//                    //distance to the rightmost duplicate element is near
//                    int length = curSize - endPosition;
//                    int[] partialBooks = new int[length];
//                    partialBooksRemove(partialBooks, numCalls, 0, length - 1, false);
//                    this.bookshelf.addLast(height);
//                    numCalls[0]++;
//                    partialBooksInsert(partialBooks, numCalls, 0, length - 1, false);
//                }
            }else{
                //a certain position to insert, check the distance of the two ends and choose the nearest.
                compareAndPut(startPosition, curSize - startPosition, numCalls, height);
//                if (startPosition <= curSize - startPosition){
//                    //left side costs less
//                    int[] partialBooks = new int[startPosition];
//                    partialBooksRemove(partialBooks, numCalls, 0, startPosition - 1, true);
//                    this.bookshelf.addFront(height);
//                    numCalls[0]++;
//                    partialBooksInsert(partialBooks, numCalls, 0, startPosition - 1, true);
//                }else{
//                    // right side costs less
//                    int length = curSize - startPosition;
//                    int[] partialBooks = new int[length];
//                    partialBooksRemove(partialBooks, numCalls, 0, length - 1, false);
//                    this.bookshelf.addLast(height);
//                    numCalls[0]++;
//                    partialBooksInsert(partialBooks, numCalls, 0, length - 1, false);
//                }
            }
        }else{ // the height to insert is largest, we only need to insert from the right side
            this.bookshelf.addLast(height);
            numCalls[0]++;
        }
        this.totalOperations += numCalls[0];
        this.lastOperations = numCalls[0];
        assert isValidBookshelfKeeper();
        return numCalls[0];
    }

    /**
     * the operation of temporarily remove some books from either side, given the index range of the books;
     * the boolean leftSide returns true if the operation is in left side, false if in right side;
     */
    private void partialBooksRemove(int[] array, int[] numCalls, int leftIndex, int rightIndex, boolean leftSide){
        if (leftSide){
            for (int i = leftIndex; i <= rightIndex; i++){
                array[i] = this.bookshelf.removeFront();
                numCalls[0]++;
            }
        }else{
            for (int i = rightIndex; i >= leftIndex; i--){
                array[i] = this.bookshelf.removeLast();
                numCalls[0]++;
            }
        }
        assert isValidBookshelfKeeper();
    }

    /**
     * the operation of inserting the temporarily removed books back, given the index range of the books;
     * the boolean leftSide returns true if the operation is in left side, false if in right side;
     */
    private void partialBooksInsert(int[] array, int[] numCalls, int leftIndex, int rightIndex, boolean leftSide){
        if (leftSide){
            for (int i = rightIndex; i >= leftIndex; i--) {
                this.bookshelf.addFront(array[i]);
                numCalls[0]++;
            }
        }else{
            for (int i = leftIndex; i <= rightIndex; i++) {
                this.bookshelf.addLast(array[i]);
                numCalls[0]++;
            }
        }
        assert isValidBookshelfKeeper();
    }
    /**
     * compare the distance of the leftSide and rightSide, and decide the smaller distance to put;
     * when removing the element, use an int[] array to store the temporarily removed element, and put back later;
     * every time we remove or put back the book, update the numCalls;
     */
    private void compareAndPut(int leftSide, int rightSide, int[] numCalls, int height){
        if (leftSide <= rightSide){
            //distance to the leftmost duplicate element is near
            int[] partialBooks = new int[leftSide];
            partialBooksRemove(partialBooks, numCalls, 0, leftSide - 1, true);
            this.bookshelf.addFront(height);
            numCalls[0]++;
            partialBooksInsert(partialBooks, numCalls, 0, leftSide - 1, true);
        }else{
            //distance to the rightmost duplicate element is near
            int[] partialBooks = new int[rightSide];
            partialBooksRemove(partialBooks, numCalls, 0, rightSide - 1, false);
            this.bookshelf.addLast(height);
            numCalls[0]++;
            partialBooksInsert(partialBooks, numCalls, 0, rightSide - 1, false);
        }
    }

    /**
     * Returns the total number of calls made to mutators on the contained bookshelf
     * so far, i.e., all the ones done to perform all of the pick and put operations
     * that have been requested up to now.
     */
    public int getTotalOperations() {
        assert isValidBookshelfKeeper();
        return this.totalOperations;
    }

    /**
     * Returns the number of books on the contained bookshelf.
     */
    public int getNumBooks() {
        assert isValidBookshelfKeeper();
        return this.bookshelf.size();
    }

    /**
     * Returns string representation of this BookshelfKeeper. Returns a String containing height
     * of all books present in the bookshelf in the order they are on the bookshelf, followed
     * by the number of bookshelf mutator calls made to perform the last pick or put operation,
     * followed by the total number of such calls made since we created this BookshelfKeeper.
     *
     * Example return string showing required format: “[1, 3, 5, 7, 33] 4 10”
     *
     */
    public String toString() {
        String book = bookshelf.toString();
        String result = book + " " + this.lastOperations + " " + this.totalOperations;
        assert isValidBookshelfKeeper();
        return result;

    }

    /**
     * Returns true iff the BookshelfKeeper data is in a valid state.
     * (See representation invariant comment for details.)
     */
    private boolean isValidBookshelfKeeper() {
        return this.bookshelf.isSorted();
    }

    // add any other private methods here


}

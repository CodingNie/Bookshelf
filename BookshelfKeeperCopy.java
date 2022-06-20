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
public class BookshelfKeeperCopy {

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
    public BookshelfKeeperCopy() {
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
    public BookshelfKeeperCopy(Bookshelf sortedBookshelf) {
        this.bookshelf = sortedBookshelf;
        this.totalOperations = 0;
        assert isValidBookshelfKeeper();
    }

    /**
     * the operation of temporarily remove some books from either side, given the index range of the books;
     * the boolean leftSide returns true if the operation is in left side, false if in right side;
     */
//    private void partialBooksRemove(int[] array, int[] numCalls, int leftIndex, int rightIndex, boolean leftSide){
//        if (leftSide){
//            for (int i = leftIndex; i <= rightIndex; i++){
//                array[i] = this.bookshelf.removeFront();
//                numCalls[0]++;
//            }
//        }else{
//            for (int i = rightIndex; i >= leftIndex; i--){
//                array[i] = this.bookshelf.removeLast();
//                numCalls[0]++;
//            }
//        }
//        assert isValidBookshelfKeeper();
//    }
//
//    /**
//     * the operation of inserting the temporarily removed books back, given the index range of the books;
//     * the boolean leftSide returns true if the operation is in left side, false if in right side;
//     */
//    private void partialBooksInsert(int[] array, int[] numCalls, int leftIndex, int rightIndex, boolean leftSide){
//        if (leftSide){
//            for (int i = rightIndex; i >= leftIndex; i--) {
//                this.bookshelf.addFront(array[i]);
//                numCalls[0]++;
//            }
//        }else{
//            for (int i = leftIndex; i <= rightIndex; i++) {
//                this.bookshelf.addLast(array[i]);
//                numCalls[0]++;
//            }
//        }
//        assert isValidBookshelfKeeper();
//    }
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
        int numCalls = 0;
        int curSize = this.getNumBooks();
        int mid = (curSize - 1) / 2;
        // pick the book from the left side will cost less
        if (position <= mid){
            // store the books temporarily removed on the left side, including the book in the position;
            int[] partialBooks = new int[position + 1];
            // remove the book on the left side increasingly, including the book in the position;
            for (int i = 0; i <= position; i++){
                partialBooks[i] = this.bookshelf.removeFront();
                numCalls++;
            }
            //put back the books on the left side decreasingly, excluding the book in the position;
            for (int i = position - 1; i >= 0; i--){
                this.bookshelf.addFront(partialBooks[i]);
                numCalls++;
            }
        }else{ // pick the book from the right side will cost less
            // store the books temporarily removed on the right side, including the book in the position;
            int length = curSize - position;
            int[] partialBooks = new int[length];
            // remove the book on the right side decreasingly, including the book in the position;
            for (int i = length - 1; i >= 0 ; i--) {
                partialBooks[i] = this.bookshelf.removeLast();
                numCalls++;
            }
            //put back the books on the right side increasingly, excluding the book in the position;
            for (int i = 1; i < partialBooks.length ; i++) {
                this.bookshelf.addLast(partialBooks[i]);
                numCalls++;
            }
        }
        this.totalOperations += numCalls;
        this.lastOperations = numCalls;
        assert isValidBookshelfKeeper();
        return numCalls;   // dummy code to get stub to compile
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
        int numCalls = 0;
        int startPosition = 0; // startPosition is the first position suitable for insert;
        int endPosition = 0;   // endPosition is the second position suitable for insert, given repeating elements;
        int curSize = this.getNumBooks();
        //find the suitable location to insert
        while (startPosition <= curSize - 1 && this.bookshelf.getHeight(startPosition) < height){
            startPosition++;
        }
        if (startPosition < curSize){
            if (this.bookshelf.getHeight(startPosition) == height){ // we need to locate the second position for insert
                endPosition = startPosition;
                while (endPosition < curSize && this.bookshelf.getHeight(endPosition) == height){
                    endPosition++;
                }
                if (startPosition <= curSize - endPosition){//left side costs less
                    int[] partialBooks = new int[startPosition];
                    for (int i = 0; i < startPosition; i++) {
                        partialBooks[i] = this.bookshelf.removeFront();
                        numCalls++;
                    }
                    this.bookshelf.addFront(height);
                    numCalls++;
                    for (int i = startPosition - 1; i >= 0; i--) {
                        this.bookshelf.addFront(partialBooks[i]);
                        numCalls++;
                    }
                }else{ // right side costs less
                    int length = curSize - endPosition;
                    int[] partialBooks = new int[length];
                    for (int i = length - 1; i >= 0 ; i--) {
                        partialBooks[i] = bookshelf.removeLast();
                        numCalls++;
                    }
                    this.bookshelf.addLast(height);
                    numCalls++;
                    for (int i = 0; i < length; i++) {
                        this.bookshelf.addLast(partialBooks[i]);
                        numCalls++;
                    }
                }
            }else{
                //a certain position to insert, check the distance of the two ends and choose the nearest.
                if (startPosition <= curSize - startPosition){ //left side costs less
                    int[] partialBooks = new int[startPosition];
                    for (int i = 0; i < startPosition; i++) {
                        partialBooks[i] = this.bookshelf.removeFront();
                        numCalls++;
                    }
                    this.bookshelf.addFront(height);
                    numCalls++;
                    for (int i = startPosition - 1; i >= 0 ; i--) {
                        this.bookshelf.addFront(partialBooks[i]);
                        numCalls++;
                    }
                }else{ // right side costs less
                    int length = curSize - startPosition;
                    int[] partialBooks = new int[length];
                    for (int i = length - 1; i >= 0 ; i--) {
                        partialBooks[i] = this.bookshelf.removeLast();
                        numCalls++;
                    }
                    this.bookshelf.addLast(height);
                    numCalls++;
                    for (int i = 0; i < length; i++) {
                        this.bookshelf.addLast(partialBooks[i]);
                        numCalls++;
                    }
                }
            }

        }else{ // the height to insert is largest, we only need to insert from the right side
            this.bookshelf.addLast(height);
            numCalls++;
        }
        this.totalOperations += numCalls;
        this.lastOperations = numCalls;
        assert isValidBookshelfKeeper();
        return numCalls;   // dummy code to get stub to compile
    }

    /**
     * Returns the total number of calls made to mutators on the contained bookshelf
     * so far, i.e., all the ones done to perform all of the pick and put operations
     * that have been requested up to now.
     */
    public int getTotalOperations() {
        assert isValidBookshelfKeeper();
        return this.totalOperations;   // dummy code to get stub to compile
    }

    /**
     * Returns the number of books on the contained bookshelf.
     */
    public int getNumBooks() {
        assert isValidBookshelfKeeper();
        return this.bookshelf.size();   // dummy code to get stub to compile
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
        return result;   // dummy code to get stub to compile

    }

    /**
     * Returns true iff the BookshelfKeeper data is in a valid state.
     * (See representation invariant comment for details.)
     */
    private boolean isValidBookshelfKeeper() {
        if (this.bookshelf.isSorted()){
            return true;
        }
        return false;  // dummy code to get stub to compile
    }

    // add any other private methods here


}

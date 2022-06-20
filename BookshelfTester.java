
import java.util.ArrayList;

public class BookshelfTester{
    public static void main(String[] args){
        //test the two constructors and rhe toString() method
        Bookshelf shelf1 = new Bookshelf();

        ArrayList<Integer> bookList = new ArrayList<>();
        bookList.add(3);
        bookList.add(4);
        bookList.add(5);
        bookList.add(7);
        bookList.add(19);

        Bookshelf shelf2 = new Bookshelf(bookList);

        String shelf1Str = shelf1.toString();
        String shelf2Str = shelf2.toString();

        System.out.println("the shelf1 string is [exp:[]] :" + shelf1Str);
        System.out.println("the shelf2 string is [exp:[3, 4, 5, 7, 19]] :" + shelf2Str);
        System.out.println("========================================================================" );

        Bookshelf shelf3 = new Bookshelf(bookList);
        //test basic
        System.out.println("the shelf3 now is [exp:[3, 4, 5, 7, 19]] :" + shelf3.toString());

        //test addFront
        shelf3.addFront(1);
        System.out.println("the shelf3 now is [exp:[1, 3, 4, 5, 7, 19]] :" + shelf3.toString());

        //test addLast
        shelf3.addLast(999);
        System.out.println("the shelf3 now is [exp:[1, 3, 4, 5, 7, 19, 999]] :" + shelf3.toString());

        //test removeFront
        shelf3.removeFront();
        System.out.println("the shelf3 now is [exp:[3, 4, 5, 7, 19, 999]] :" + shelf3.toString());

        //test removeLast
        shelf3.removeLast();
        System.out.println("the shelf3 now is [exp:[3, 4, 5, 7, 19]] :" + shelf3.toString());
        System.out.println("========================================================================" );

        Bookshelf shelf4 = new Bookshelf(bookList);

        //test basic
        System.out.println("the shelf4 now is [exp:[3, 4, 5, 7, 19]] :" + shelf4.toString());
        //test getHeight
        int testHeight = shelf4.getHeight(3);
        System.out.println("the position 3 of shelf4 is [exp: 7]: " + testHeight);

        //test size
        int testSize = shelf4.size();
        System.out.println("the size now for shelf4 is [exp: 5]: " + testSize);

        //test sort
        boolean shelf4Sort = shelf4.isSorted();
        if (shelf4Sort) {
            System.out.println("shelf4 is sorted");
        }else if (!shelf4Sort){
            System.out.println("shelf4 is unsorted");
        }

        ArrayList<Integer> bookList2 = new ArrayList<>();
        bookList2.add(3);
        bookList2.add(2);
        bookList2.add(5);

        Bookshelf bookshelf5 = new Bookshelf(bookList2);
        boolean shelf5Sort = bookshelf5.isSorted();
        if (shelf5Sort) {
            System.out.println("shelf5 is sorted");
        }else if (!shelf5Sort){
            System.out.println("shelf5 is unsorted");
        }
    }
}
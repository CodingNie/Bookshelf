import java.util.ArrayList;

public class TestAssert{
    public static void main(String[] args){

        ArrayList<Integer> myBook1 = new ArrayList<>();
        myBook1.add(3);
        myBook1.add(4);
        myBook1.add(5);
        ArrayList<Integer> myBook2 = new ArrayList<>();
        myBook2.add(3);
        myBook2.add(8);
        myBook2.add(4);
        ArrayList<Integer> myBook3 = new ArrayList<>();
        myBook3.add(-3);
        myBook3.add(-2);
        myBook3.add(5);

        Bookshelf shelf1 = new Bookshelf(myBook1);
        Bookshelf shelf2 = new Bookshelf(myBook2);
        Bookshelf shelf3 = new Bookshelf(myBook3);
    }
}
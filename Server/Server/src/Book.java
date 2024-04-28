import java.util.*;

public class Book extends Item {
    private int pages;
    public String author;
    //private List<Copy> copies;
    public List<String> holds;

    Book() {}


    // Constructor, getters, setters, and other methods
    Book(String itemType, String title, String author, int pages, String year, String imageURL) {
        this.itemType = itemType;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.year = year;
        this.imageURL = imageURL;
        holds = new ArrayList<>();
//        this.copies = new ArrayList<Copy>();
//        for (int i = 0; i < copyNum; i++) {
//            copies.add(new Copy(i));
//        }
    }

    public List<String> getHolds() {
        return holds;
    }

    public void setHolds(List<String> holds) {
        this.holds = holds;
    }

    public String getAuthor() {
         return author;
     }

     public int getPages() {
         return pages;
     }

//    public List<Copy> getCopies() {
//        return copies;
//    }
}

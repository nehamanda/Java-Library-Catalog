public class Book extends Item {
    private int pages;
    public String author;

    Book() {}


    // Constructor, getters, setters, and other methods
    Book(String itemType, String title, String author, int pages, String year) {
        this.itemType = itemType;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.year = year;
    }

     public String getAuthor() {
         return author;
     }

     public int getPages() {
         return pages;
     }
}

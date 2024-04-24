

public class Book extends Item {
    private int pages;
    public String author;
    //private List<Copy> copies;

    Book() {}


    // Constructor, getters, setters, and other methods
    Book(String itemType, String title, String author, int pages, String year, String imageURL) {
        this.itemType = itemType;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.year = year;
        this.imageURL = imageURL;
//        this.copies = new ArrayList<Copy>();
//        for (int i = 0; i < copyNum; i++) {
//            copies.add(new Copy(i));
//        }
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

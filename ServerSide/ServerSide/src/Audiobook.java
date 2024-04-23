public class Audiobook extends Item {

    private String length;
    private String author;

    public Audiobook() {}


    // Constructor, getters, setters, and other methods
    public Audiobook(String itemType, String title, String author, String length, String year) {
        this.itemType = itemType;
        this.title = title;
        this.author = author;
        this.length = length;
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public String getLength() {
        return length;
    }

    // Constructor, getters, setters, and other methods
}

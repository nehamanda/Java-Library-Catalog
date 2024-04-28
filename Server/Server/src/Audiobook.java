import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Audiobook extends Item {

    private String length;
    private String author;
    public List<String> holds;

    public Audiobook() {}


    // Constructor, getters, setters, and other methods
    public Audiobook(String itemType, String title, String author, String length, String year, String imageURL) {
        this.itemType = itemType;
        this.title = title;
        this.author = author;
        this.length = length;
        this.year = year;
        this.imageURL = imageURL;
        holds = new ArrayList<>();
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

    public String getLength() {
        return length;
    }

    // Constructor, getters, setters, and other methods
}

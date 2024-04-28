import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Movie extends Item {

    private String length;
    public List<String> holds;

    public Movie() {}


    // Constructor, getters, setters, and other methods
    public Movie(String itemType, String title, String length, String year, String imageURL) {
        this.itemType = itemType;
        this.title = title;
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


    public String getLength() {
        return length;
    }

    // Constructor, getters, setters, and other methods
}

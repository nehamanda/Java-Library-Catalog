import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Game extends Item {
    private String length;
    public List<String> holds;

    public Game() {}


    // Constructor, getters, setters, and other methods
    public Game(String itemType, String title, String year, String imageURL) {
        this.itemType = itemType;
        this.title = title;
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


    // Constructor, getters, setters, and other methods
}

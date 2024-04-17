import java.util.List;

public class Member {
    private String username;
    private List<Item> borrowedItems;
    // Other properties and constructors

    public synchronized void borrowItem(Item item) {
        borrowedItems.add(item);
    }

    public synchronized void returnItem(Item item) {
        borrowedItems.remove(item);
    }

    // Other methods
}
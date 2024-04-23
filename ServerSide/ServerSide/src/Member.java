import java.util.List;

public class Member {
    private String username;
    private String password;
    private List<Item> borrowedItems;
    // Other properties and constructors
    public Member() {}
    public Member (String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public synchronized void borrowItem(Item item) {
        borrowedItems.add(item);
    }

    public synchronized void returnItem(Item item) {
        borrowedItems.remove(item);
    }

    // Other methods
}
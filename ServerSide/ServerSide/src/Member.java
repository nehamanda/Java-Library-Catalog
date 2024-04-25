import java.util.ArrayList;
import java.util.List;

public class Member {
    private String username;
    private String password;
    private List<Item> borrowedItems;

    private String profilePic;
    // Other properties and constructors
    public Member() {}
    public Member (String username, String password) {
        this.username = username;
        this.password = password;
        borrowedItems = new ArrayList<Item>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public List<Item> getBorrowedItems() {
        return borrowedItems;
    }

    public synchronized void borrowItem(Item item) {
        borrowedItems.add(item);
    }

    public synchronized void returnItem(Item item) {
        borrowedItems.remove(item);
    }

    // Other methods
}
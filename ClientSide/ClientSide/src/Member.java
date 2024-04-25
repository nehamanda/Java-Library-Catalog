import java.util.ArrayList;
import java.util.List;

public class Member {
    private String username;
    private String password;
    public List<Item> borrowedItems;

    private String profilePic;
    // Other properties and constructors
    public Member() {}
    public Member(String username) {
        this.username = username;
        borrowedItems = new ArrayList<Item>();
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
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

    public boolean hasItem(Item item) {
        if (borrowedItems.contains(item)) {
            return true;
        }
        return false;
    }

    // Other methods
}
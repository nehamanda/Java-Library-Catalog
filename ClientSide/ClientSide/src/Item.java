import java.io.Serializable;

public class Item implements Serializable {
    public String title;
    public String itemType;
    public boolean available = true;
    public String year;

    public String imageURL;
    public String getTitle() {
        return title;
    }
    public boolean isAvailable() {
        return available;
    }

    public synchronized void setAvailable(boolean available) {
        this.available = available;
    }

    public String getItemType() {
        return itemType;
    }

    public String getYear() {
        return year;
    }

    public String getImageURL() {
        return imageURL;
    }

    // Common methods for all types of items
}
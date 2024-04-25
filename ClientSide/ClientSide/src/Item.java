import java.io.Serializable;
import java.util.Objects;

public class Item implements Serializable {
    public String title;
    public String itemType;
    public boolean available = true;
    public String year;

    public String imageURL;

    public int copies;

    public int getCopies() {
        return copies;
    }
    public void setCopies(int copies) {
        this.copies = copies;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return available == item.available &&
                Objects.equals(title, item.title) &&
                Objects.equals(itemType, item.itemType) &&
                Objects.equals(year, item.year) &&
                Objects.equals(imageURL, item.imageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, itemType, available, year, imageURL);
    }

    // Common methods for all types of items
}
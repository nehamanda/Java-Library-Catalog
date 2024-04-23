public class Item {
    public String title;
    public String itemType;
    public boolean available = true;
    public String year;
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

    // Common methods for all types of items
}


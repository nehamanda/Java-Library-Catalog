public class Item {
    private String itemType;
    private String title;
    private String author;
    private boolean available;
    // Other properties and constructors

    public synchronized boolean isAvailable() {
        return available;
    }

    public synchronized void setAvailable(boolean available) {
        this.available = available;
    }

    // Other methods like checkIn, checkOut, etc.
}
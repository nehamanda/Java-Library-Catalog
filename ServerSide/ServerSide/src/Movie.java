public class Movie extends Item {

    private String length;

    public Movie() {}


    // Constructor, getters, setters, and other methods
    public Movie(String itemType, String title, String length, String year) {
        this.itemType = itemType;
        this.title = title;
        this.length = length;
        this.year = year;
    }


    public String getLength() {
        return length;
    }

    // Constructor, getters, setters, and other methods
}

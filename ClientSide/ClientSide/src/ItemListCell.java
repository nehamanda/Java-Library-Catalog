import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ItemListCell extends ListCell<Item> {
    @Override
    protected void updateItem(Item item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (item instanceof Book) {
                Book book = (Book) item;
                String author = book.getAuthor();
                String title = book.getTitle();
                String itemType = book.getItemType();
                int pages = book.getPages();
                String year = book.getYear();
                boolean a = book.isAvailable();
                String availability = a ? "1 of 1 Available" : "0 of 1 Available";

                // Customize the formatting as needed
                String formattedText = "Title: " + title + "\n"
                        + "Author: " + author + "\n"
                        + "Format: " + itemType + "\n" + "Pages: " + pages + "\nYear: " + year + "\n" + availability;

                setText(null); // Clear existing text
                setGraphic(null); // Clear existing graphic

                // Set the formatted text
                setText(formattedText);

            }
            else if (item instanceof Game) {
                Game game = (Game) item;
                String title = game.getTitle();
                String itemType = game.getItemType();
                String year = game.getYear();
                boolean a = game.isAvailable();
                String availability = a ? "1 of 1 Available" : "0 of 1 Available";

                // Customize the formatting as needed
                String formattedText = "Title: " + title + "\n"
                        + "Format: " + itemType + "\n" + "Year: " + year + "\n" + availability;

                setText(null); // Clear existing text
                setGraphic(null); // Clear existing graphic

                // Set the formatted text
                setText(formattedText);

            }
            else if (item instanceof Movie) {
                Movie movie = (Movie) item;
                String title = movie.getTitle();
                String itemType = movie.getItemType();
                String length = movie.getLength();
                String year = movie.getYear();
                boolean a = movie.isAvailable();
                String availability = a ? "1 of 1 Available" : "0 of 1 Available";

                // Customize the formatting as needed
                String formattedText = "Title: " + title + "\n"
                        + "Format: " + itemType + "\n" + "Length: " + length + "\nYear: " + year + "\n" + availability;

                setText(null); // Clear existing text
                setGraphic(null); // Clear existing graphic

                // Set the formatted text
                setText(formattedText);

            }
            else if (item instanceof Audiobook) {
                Audiobook Abook = (Audiobook) item;
                String author = Abook.getAuthor();
                String title = Abook.getTitle();
                String itemType = Abook.getItemType();
                String length = Abook.getLength();
                String year = Abook.getYear();
                boolean a = Abook.isAvailable();
                String availability = a ? "1 of 1 Available" : "0 of 1 Available";


                // Customize the formatting as needed
                String formattedText = "Title: " + title + "\n" + "Author: " + author + "\n"
                        + "Format: " + itemType + "\n" + "Length: " + length + "\nYear: " + year + "\n" + availability;

                setText(null); // Clear existing text
                setGraphic(null); // Clear existing graphic

                // Set the formatted text
                setText(formattedText);

            }


            // Load and display item image
            ImageView imageView = new ImageView(new Image(item.getImageURL()));
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);
            setGraphic(imageView);
        }
    }
}

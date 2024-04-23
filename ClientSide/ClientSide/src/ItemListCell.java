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
            if (item.getItemType().equals("book")) {
                Book book = (Book) item;
                String author = book.getAuthor();
                String title = book.getTitle();
                String itemType = book.getItemType();

                // Customize the formatting as needed
                String formattedText = "Author: " + author + "\n"
                        + "Title: " + title + "\n"
                        + "Item Type: " + itemType;

                setText(null); // Clear existing text
                setGraphic(null); // Clear existing graphic

                // Set the formatted text
                setText(formattedText);

            }
            else if (item.getItemType().equals("game")) {
                Game game = (Game) item;
                String title = game.getTitle();
                String itemType = game.getItemType();

                // Customize the formatting as needed
                String formattedText = "Title: " + title + "\n"
                        + "Item Type: " + itemType;

                setText(null); // Clear existing text
                setGraphic(null); // Clear existing graphic

                // Set the formatted text
                setText(formattedText);

            }
            else if (item.getItemType().equals("movie")) {
                Movie movie = (Movie) item;
                String title = movie.getTitle();
                String itemType = movie.getItemType();

                // Customize the formatting as needed
                String formattedText = "Title: " + title + "\n"
                        + "Item Type: " + itemType;

                setText(null); // Clear existing text
                setGraphic(null); // Clear existing graphic

                // Set the formatted text
                setText(formattedText);

            }
            else if (item.getItemType().equals("audiobook")) {
                Audiobook Abook = (Audiobook) item;
                String author = Abook.getAuthor();
                String title = Abook.getTitle();
                String itemType = Abook.getItemType();

                // Customize the formatting as needed
                String formattedText = "Author: " + author + "\n"
                        + "Title: " + title + "\n"
                        + "Item Type: " + itemType;

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

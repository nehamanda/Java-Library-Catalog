import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;
import java.util.List;

public class MongoDbPojo {
    private static MongoClient mongo;
    private static MongoDatabase database;

    private static MongoCollection<Member> collection;
    private static MongoCollection<Item> collection2;

    private static final String URI = "mongodb+srv://neharmanda:aAqZOq4ZHj08CfXx@cluster0.0qulmhi.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DB = "library";
    private static final String COLLECTION = "users";
    private static final String COLLECTION2 = "items";


    public static void main(String[] args) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).register(Member.class).register(Book.class).register(Game.class).register(Audiobook.class).register(Movie.class).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));


        mongo = MongoClients.create(URI);
        database = mongo.getDatabase(DB).withCodecRegistry(pojoCodecRegistry);
        collection = database.getCollection(COLLECTION, Member.class);
        collection2 = database.getCollection(COLLECTION2, Item.class);

        //CREATE

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Book("book", "Anxious People", "Backman, Fredrik", 352, "2020", "https://m.media-amazon.com/images/I/81+NiUsL3qL._AC_UF1000,1000_QL80_.jpg"));
        itemList.add(new Book("book", "Wonder", "Palacio, R.J.", 310, "2012", "https://upload.wikimedia.org/wikipedia/en/0/03/Wonder_Cover_Art.png"));
        itemList.add(new Game("game", "Life", "1990", "https://m.media-amazon.com/images/I/81s1uoBIf0L._AC_UF894,1000_QL80_.jpg"));
        itemList.add(new Movie("movie", "Chungking Express", "1h 37m", "1994", "https://s3.amazonaws.com/nightjarprod/content/uploads/sites/192/2021/11/01151557/43I9DcNoCzpyzK8JCkJYpHqHqGG.jpg"));
        itemList.add(new Audiobook("audiobook", "The Poppy War", "Kuang, R. F.", "19h 27m", "2018", "https://m.media-amazon.com/images/I/71ZVpkRIGsL._AC_UF1000,1000_QL80_.jpg"));
        collection2.insertMany(itemList);


        /*ArrayList<Member> memberList = new ArrayList<>();
        memberList.add(new Member("neha", "0818"));
        memberList.add(new Member("shikha", "0511"));
        memberList.add(new Member("nehak", "0213"));
        memberList.add(new Member("jake", "0712"));
        memberList.add(new Member("connor", "0319"));
        collection.insertMany(memberList);*/

        //FIND AND READ
        //Item item = collection2.find(Filters.eq("name", "Adidas Ultraboost")).first();
        //System.out.println(item);

        //UPDATE
        //item.setName("Adidas Megaboost");
        //collection2.findOneAndReplace(Filters.eq("name", "Adidas Ultraboost"), item);

        //READ ALL
        MongoCursor cursor = collection2.find(Filters.empty()).cursor();
        while(cursor.hasNext()) {
            System.out.println("ITERATING: " + ((Item)cursor.next()).toString());
        }

        //DELETE
        //Document filterByItemId = new Document("_id", item.getId());
        //collection2.deleteOne(filterByItemId);

    }
    public static void initialize() {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).register(Member.class).register(Book.class).register(Game.class).register(Audiobook.class).register(Movie.class).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));


        mongo = MongoClients.create(URI);
        database = mongo.getDatabase(DB).withCodecRegistry(pojoCodecRegistry);
        collection = database.getCollection(COLLECTION, Member.class);
        collection2 = database.getCollection(COLLECTION2, Item.class);
    }

    public static boolean authenticate(String username, String password) {
        Document userQuery = new Document("username", username).append("password", password);
        Member user = collection.find(userQuery).first();
        return user != null;
    }

    public static List retrieveItems() {
        List<Item> items = new ArrayList<>();
        for (Item doc : collection2.find()) {
            // Parse document fields and create Item objects based on item type
            String itemType = doc.getItemType();

            // Create corresponding item object based on item type
            Item item;
            switch (itemType) {
                case "book":
                    item = createBookFromDocument(doc);
                    break;
                case "movie":
                    item = createMovieFromDocument(doc);
                    break;
                case "audiobook":
                    item = createAudiobookFromDocument(doc);
                    break;
                case "game":
                    item = createGameFromDocument(doc);
                    break;
                default:
                    // Handle unknown item types or throw an exception
                    throw new IllegalArgumentException("Unknown item type: " + itemType);
            }

            // Add item to the list
            items.add(item);
        }
        return items;
    }
    private static Book createBookFromDocument(Item doc) {
        // Extract fields from document and create Book object
        Book book = (Book) doc;
        String itemType = book.getItemType();
        String title = book.getTitle();
        String author = book.getAuthor();
        int pageCount = book.getPages();
        String year = doc.getYear();
        String imageUrl = doc.getImageURL();

        return new Book(itemType, title, author, pageCount, year, imageUrl);
    }

    private static Movie createMovieFromDocument(Item doc) {
        // Extract fields from document and create Movie object
        Movie movie = (Movie) doc;
        String itemType = movie.getItemType();
        String title = movie.getTitle();
        String length = movie.getLength();
        String year = movie.getYear();
        String imageUrl = movie.getImageURL();

        return new Movie(itemType, title, length, year, imageUrl);
    }

    private static Audiobook createAudiobookFromDocument(Item doc) {
        // Extract fields from document and create Audiobook object
        Audiobook Abook = (Audiobook) doc;
        String itemType = Abook.getItemType();
        String title = Abook.getTitle();
        String author = Abook.getAuthor();
        String length = Abook.getLength();
        String year = Abook.getYear();
        String imageUrl = Abook.getImageURL();

        return new Audiobook(itemType, title, author, length, year, imageUrl);
    }

    private static Game createGameFromDocument(Item doc) {
        // Extract fields from document and create Game object
        Book book = (Book) doc;
        String itemType = book.getItemType();
        String title = book.getTitle();
        String year = doc.getYear();
        String imageUrl = doc.getImageURL();

        return new Game(itemType, title, year, imageUrl);
    }

}

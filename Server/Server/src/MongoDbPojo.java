import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.mindrot.bcrypt.BCrypt;
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
import java.util.Queue;

public class MongoDbPojo {
    private static MongoClient mongo;
    private static MongoDatabase database;

    private static MongoCollection<Document> collection;
    private static MongoCollection<Document> collection2;

    private static final String URI = "mongodb+srv://neharmanda:aAqZOq4ZHj08CfXx@cluster0.0qulmhi.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DB = "library";
    private static final String COLLECTION = "users";
    private static final String COLLECTION2 = "items";


    public static void main(String[] args) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).register(Member.class).register(Book.class).register(Game.class).register(Audiobook.class).register(Movie.class).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));


        mongo = MongoClients.create(URI);
        database = mongo.getDatabase(DB).withCodecRegistry(pojoCodecRegistry);
        collection = database.getCollection(COLLECTION);
        collection2 = database.getCollection(COLLECTION2);

        //CREATE

        /*ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Book("book", "Anxious People", "Backman, Fredrik", 352, "2020", "https://m.media-amazon.com/images/I/81+NiUsL3qL._AC_UF1000,1000_QL80_.jpg"));
        itemList.add(new Book("book", "Wonder", "Palacio, R.J.", 310, "2012", "https://upload.wikimedia.org/wikipedia/en/0/03/Wonder_Cover_Art.png"));
        itemList.add(new Game("game", "Life", "1990", "https://m.media-amazon.com/images/I/81s1uoBIf0L._AC_UF894,1000_QL80_.jpg"));
        itemList.add(new Movie("movie", "Chungking Express", "1h 37m", "1994", "https://s3.amazonaws.com/nightjarprod/content/uploads/sites/192/2021/11/01151557/43I9DcNoCzpyzK8JCkJYpHqHqGG.jpg"));
        itemList.add(new Audiobook("audiobook", "The Poppy War", "Kuang, R. F.", "19h 27m", "2018", "https://m.media-amazon.com/images/I/71ZVpkRIGsL._AC_UF1000,1000_QL80_.jpg"));
        //collection2.insertMany(itemList);*/


//        ArrayList<Member> memberList = new ArrayList<>();
//        memberList.add(new Member("neha", "0818"));
//        memberList.add(new Member("shikha", "0511"));
//        memberList.add(new Member("nehak", "0213"));
//        memberList.add(new Member("jake", "0712"));
//        memberList.add(new Member("connor", "0319"));

        Document item = collection2.find(Filters.eq("title", "The Poppy War")).first();
        Audiobook abook = createAudiobookFromDocument(item);
        item.replace("holds", abook.getHolds());
        collection2.findOneAndReplace(Filters.eq("title", "The Poppy War"), item);
        //collection.insertMany(memberList);

        //FIND AND READ
        //Item item = collection2.find(Filters.eq("name", "neha")).first();
        //System.out.println(item);

        //UPDATE
        //item.setName("Adidas Megaboost");
        //collection2.findOneAndReplace(Filters.eq("name", "Adidas Ultraboost"), item);

        //READ ALL
        /*MongoCursor cursor = collection2.find(Filters.empty()).cursor();
        while(cursor.hasNext()) {
            System.out.println("ITERATING: " + ((Item)cursor.next()).toString());
        }*/

        //DELETE
        //Document filterByItemId = new Document("_id", item.getId());
        //collection2.deleteOne(filterByItemId);

    }
    public static void initialize() {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).register(Member.class).register(Book.class).register(Game.class).register(Audiobook.class).register(Movie.class).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));


        mongo = MongoClients.create(URI);
        database = mongo.getDatabase(DB).withCodecRegistry(pojoCodecRegistry);
        collection = database.getCollection(COLLECTION);
        collection2 = database.getCollection(COLLECTION2);
    }

    public static boolean authenticate(String username, String password) {
        Document find = collection.find(Filters.eq("username", username)).first();
        if (find == null) {
            return false;
        }
        String hash = find.getString("hash");
        String pw = BCrypt.hashpw(password, hash);
        return pw.equals(find.getString("password"));
    }
    public static boolean createaccount(String username, String password) {
        Document find = collection.find(Filters.eq("username", username)).first();
        if (find != null) {
            return false;
        }
        String newHash = BCrypt.gensalt();
        String pw = BCrypt.hashpw(password, newHash);
        Member newM = new Member(username, pw);
        Document document = new Document();
        document.append("username", username);
        document.append("password", pw);
        document.append("borrowedItems", newM.getBorrowedItems());
        document.append("hash", newHash);
        document.append("profilePic", "https://i.pinimg.com/736x/c0/27/be/c027bec07c2dc08b9df60921dfd539bd.jpg");
        collection.insertOne(document);
        return true;
    }

    public static boolean changepw(String username, String password) {
        Document find = collection.find(Filters.eq("username", username)).first();
        if (find == null) {
            return false;
        }
        String hash = find.getString("hash");
        String pw = BCrypt.hashpw(password, hash);
        find.replace("password", pw);
        collection.findOneAndReplace(Filters.eq("username", username), find);
        return true;
    }
    public static boolean newpfp(String username, String newpfp) {
        Document find = collection.find(Filters.eq("username", username)).first();
        if (find == null) {
            return false;
        }
        find.replace("profilePic", newpfp);
        collection.findOneAndReplace(Filters.eq("username", username), find);
        return true;
    }

    public static List retrieveItems() {
        List<Item> items = new ArrayList<>();
        for (Document doc : collection2.find()) {
            // Parse document fields and create Item objects based on item type
            String itemType = doc.getString("itemType");
            //items.add(doc);

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

    public static boolean borrow(String itemName, String username) {
        Document item = collection2.find(Filters.eq("title", itemName)).first();
        synchronized (collection2) {
            if (item.getBoolean("available")) {
                Document query = new Document("username", username);
                Document member = collection.find(query).first();
                List<Item> borrowList = (List) member.get("borrowedItems");
                String itemType = item.getString("itemType");
                Item checkedout;
                switch (itemType) {
                    case "book":
                        checkedout = createBookFromDocument(item);
                        break;
                    case "movie":
                        checkedout = createMovieFromDocument(item);
                        break;
                    case "audiobook":
                        checkedout = createAudiobookFromDocument(item);
                        break;
                    case "game":
                        checkedout = createGameFromDocument(item);
                        break;
                    default:
                        // Handle unknown item types or throw an exception
                        throw new IllegalArgumentException("Unknown item type: " + itemType);
                }
                item.replace("copies", item.getInteger("copies")-1);
                if (item.getInteger("copies") <= 0) {
                    item.replace("available", true, false);
                }
                collection2.findOneAndReplace(Filters.eq("title", itemName), item);
                checkedout.setCopies(checkedout.getCopies() - 1);
                if (checkedout.getCopies() <= 0) {
                    checkedout.setAvailable(false);
                }
                borrowList.add(checkedout);
                member.replace("borrowedItems", borrowList);
                collection.findOneAndReplace(Filters.eq("username", username), member);
                return true;
            }
        }

        return false;
    }

    public static boolean hold(String itemName, String username) {
        synchronized (collection2) {
            Document item = collection2.find(Filters.eq("title", itemName)).first();
            ArrayList<String> held = (ArrayList<String>) item.get("holds");
            if (held.contains(username)) {return false;}
            Document query = new Document("username", username);
            Document member = collection.find(query).first();
            List<Item> borrowList = (List) member.get("borrowedItems");
            String itemType = item.getString("itemType");
            Item checkedout;
            switch (itemType) {
                case "book":
                    checkedout = createBookFromDocument(item);
                    break;
                case "movie":
                    checkedout = createMovieFromDocument(item);
                    break;
                case "audiobook":
                    checkedout = createAudiobookFromDocument(item);
                    break;
                case "game":
                    checkedout = createGameFromDocument(item);
                    break;
                default:
                    // Handle unknown item types or throw an exception
                    throw new IllegalArgumentException("Unknown item type: " + itemType);
            }
            if (borrowList.contains(checkedout)) {return false;}
            if (!item.getBoolean("available")) {
                held.add(username);
                item.replace("holds", held);
                collection2.findOneAndReplace(Filters.eq("title", itemName), item);
                return true;
            }
        }
        return false;
    }

    public static boolean held(String itemName, String username) {
        Document item = collection2.find(Filters.eq("title", itemName)).first();
        ArrayList<String> held = (ArrayList<String>) item.get("holds");
        return held.contains(username);
    }

    public static boolean returnItem(String itemName, String username) {
        Document item = collection2.find(Filters.eq("title", itemName)).first();
        synchronized (collection2) {
            if (item.getInteger("copies") < item.getInteger("total")) {
                Document query = new Document("username", username);
                Document member = collection.find(query).first();
                List<Item> borrowListItem = retrieveUserList(username);
                String itemType = item.getString("itemType");
                Item checkedout;
                switch (itemType) {
                    case "book":
                        checkedout = createBookFromDocument(item);
                        break;
                    case "movie":
                        checkedout = createMovieFromDocument(item);
                        break;
                    case "audiobook":
                        checkedout = createAudiobookFromDocument(item);
                        break;
                    case "game":
                        checkedout = createGameFromDocument(item);
                        break;
                    default:
                        // Handle unknown item types or throw an exception
                        throw new IllegalArgumentException("Unknown item type: " + itemType);
                }
                item.replace("copies", item.getInteger("copies")+1);
                item.replace("available", true);
                collection2.findOneAndReplace(Filters.eq("title", itemName), item);
                borrowListItem.remove(checkedout); //issue here
                member.replace("borrowedItems", borrowListItem);
                collection.findOneAndReplace(Filters.eq("username", username), member);

                ArrayList<String> held = (ArrayList<String>) item.get("holds");
                if (held.isEmpty()) {
                    return true;
                }
                String newuser = held.get(0);
                if (borrow(itemName, newuser)) {
                    held.remove(0);
                    Document item1 = collection2.find(Filters.eq("title", itemName)).first();
                    item1.replace("holds", held);
                    collection2.findOneAndReplace(Filters.eq("title", itemName), item1);
                }
                return true;
            }
        }
        return false;
    }

    public static List retrieveUserList(String user) {
        Document query = new Document("username", user);
        Document member = collection.find(query).first();
        List<Document> itemdocs =  (List) member.get("borrowedItems");
        List<Item> items = new ArrayList<>();
        for (Document doc : itemdocs) {
            // Parse document fields and create Item objects based on item type
            String itemType = doc.getString("itemType");
            //items.add(doc);

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

    public static String retrievePfp(String username) {
        Document query = new Document("username", username);
        Document member = collection.find(query).first();
        return member.getString("profilePic");
    }


    private static Book createBookFromDocument(Document doc) {
        // Extract fields from document and create Book object
        Book book;
        String itemType = doc.getString("itemType");
        String title = doc.getString("title");
        String author = doc.getString("author");
        int pageCount = doc.getInteger("pages");
        String year = doc.getString("year");
        String imageUrl = doc.getString("imageURL");
        boolean a = doc.getBoolean("available");
        int copies = doc.getInteger("copies");
        int total = doc.getInteger("total");
        List<String> holds = (List<String>) doc.get("holds");

        book = new Book(itemType, title, author, pageCount, year, imageUrl);

        book.setAvailable(a);
        book.setTotal(total);
        book.setCopies(copies);
        book.setHolds(holds);
        return book;
    }

    private static Movie createMovieFromDocument(Document doc) {
        // Extract fields from document and create Movie object
        Movie movie;
        String itemType = doc.getString("itemType");
        String title = doc.getString("title");
        String length = doc.getString("length");
        String year = doc.getString("year");
        String imageUrl = doc.getString("imageURL");
        boolean a = doc.getBoolean("available");
        int copies = doc.getInteger("copies");
        int total = doc.getInteger("total");
        List<String> holds = (List<String>) doc.get("holds");

        movie = new Movie(itemType, title, length, year, imageUrl);
        movie.setAvailable(a);
        movie.setTotal(total);
        movie.setCopies(copies);
        movie.setHolds(holds);
        return movie;
    }

    private static Audiobook createAudiobookFromDocument(Document doc) {
        // Extract fields from document and create Audiobook object
        Audiobook abook;

        String itemType = doc.getString("itemType");
        String title = doc.getString("title");
        String author = doc.getString("author");
        String length = doc.getString("length");
        String year = doc.getString("year");
        String imageUrl = doc.getString("imageURL");
        boolean a = doc.getBoolean("available");
        int copies = doc.getInteger("copies");
        int total = doc.getInteger("total");
        List<String> holds = (List<String>) doc.get("holds");

        abook = new Audiobook(itemType, title, author, length, year, imageUrl);
        abook.setAvailable(a);
        abook.setTotal(total);
        abook.setCopies(copies);
        abook.setHolds(holds);
        return abook;
    }


    private static Game createGameFromDocument(Document doc) {
        // Extract fields from document and create Game object
        Game game;
        String itemType = doc.getString("itemType");
        String title = doc.getString("title");
        String year = doc.getString("year");
        String imageUrl = doc.getString("imageURL");
        boolean a = doc.getBoolean("available");
        int copies = doc.getInteger("copies");
        int total = doc.getInteger("total");
        List<String> holds = (List<String>) doc.get("holds");

        game = new Game(itemType, title, year, imageUrl);
        game.setAvailable(a);
        game.setTotal(total);
        game.setCopies(copies);
        game.setHolds(holds);
        return game;
    }

}

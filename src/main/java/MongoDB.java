import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDB {
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection test;

    public static void main(String[] args){
        mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://daulet:aitu2021@cluster0.yl9wgue.mongodb.net/test"));
        database = mongoClient.getDB("masada");
        test = database.getCollection("xdream");

    }
}

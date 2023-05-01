import com.mongodb.*;
import org.jsoup.Jsoup;

import javax.swing.text.Document;
import java.io.IOException;
import java.util.Scanner;

public class MongoDB {
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection test;
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("masada");
        test = database.getCollection("xdream");

        System.out.println("Hello! This is a Masada car trading service. What do u want to do?" +
                "\n1: Offer my own car" +
                "\n2: See all cars" +
                "\n3: Enter filters");
        int n = in.nextInt();

        if(n==1) {
            String manufacturer, name, salon, color;
            int year, price;

            System.out.println("Now enter manufacturer, name, year, salon, color & price \n*with spaces only: ");
            manufacturer = in.next();
            name = in.next();
            year = in.nextInt();
            salon = in.next();
            color = in.next();
            price = in.nextInt();

            BasicDBObject key_details = new BasicDBObject();
            BasicDBObject details = new BasicDBObject();
            key_details.put("manufacturer", manufacturer);
            key_details.put("name", name);
            key_details.put("year", year);

            details.put("salon", salon);
            details.put("color", color);

            BasicDBObject outerDocument = new BasicDBObject();
            outerDocument.put("car_details", key_details);
            outerDocument.put("description", details);
            outerDocument.put("price", price);
            test.insert(outerDocument);

            System.out.println("We've just uploaded your car. Do you want to see list of available cars?");
        }


    }
}

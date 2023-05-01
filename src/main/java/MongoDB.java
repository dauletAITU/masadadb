import com.mongodb.*;
import java.util.Scanner;

public class MongoDB {
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection test, accounts;
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("masada");
        test = database.getCollection("xdream");
        accounts = database.getCollection("x_dream_accs");

        System.out.println("Hello! This is a Masada car trading service. What do u want to do?" +
                "\n1: Offer my own car" +
                "\n2: See all cars" +
                "\n3: Enter filters" +
                "\n4: Sign In");
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
        }
        else if(n==4){

            System.out.println("Enter your login and password: \n*with space between");
            String login =in.next(), password = in.next();

            BasicDBObject query = new BasicDBObject();
            query.put("login", login);
            query.put("password", password);

            DBCursor cursor = accounts.find(query);

            if (cursor.hasNext()) {
                System.out.println("You enter as an admin, now you can conduct crud operations \nWhat do you want to change?");
                System.out.println("1: Create \n2: Read \n3: Update \n4: Delete");
                int n1 = in.nextInt();

                if(n1==1){
                    String manufacturer, name, salon, color;
                    int year, price;

                    System.out.println("Now enter manufacturer, name, year, salon, color & price\n*with spaces only\n9: If you want to go back: ");
                    int exc = in.nextInt();
                    if(exc==9){}
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
                }
                else if(n1==2){
                    System.out.println("You can search by car manufacturer, year and price range \n1: Choose by one \n2: Choose by two \n3;Choose all parameters");
                }
            }
        }
    }
}

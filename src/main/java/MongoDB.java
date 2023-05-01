import com.mongodb.*;
import com.mongodb.client.model.Filters;

import java.util.List;
import java.util.Scanner;

public class MongoDB {
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection test, accounts;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("masada");
        test = database.getCollection("xdream");
        accounts = database.getCollection("x_dream_accs");

        System.out.println("Hello! This is a Masada car trading service. What do you want to do?" +
                "\n1: Offer my own car" +
                "\n2: See all cars" +
                "\n3: Enter filters" +
                "\n4: Sign In");
        int n = in.nextInt();

        if (n == 1) {
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
        } else if (n == 2) {
            DBCursor cursor = test.find();
            while (cursor.hasNext()) {
                DBObject result = cursor.next();
                System.out.println(result);
            }
        } else if (n == 3) {
            BasicDBObject query = new BasicDBObject();
            System.out.println("Enter filters (manufacturer, name, year, salon, color, price). Leave empty if you don't want to filter by a certain field.");
            System.out.print("Manufacturer: ");
            String manufacturer = in.next();
            if (!manufacturer.isEmpty()) {
                query.put("car_details.manufacturer", manufacturer);
            }
            System.out.print("Name: ");
            String name = in.next();
            if (!name.isEmpty()) {
                query.put("car_details.name", name);
            }
            System.out.print("Year: ");
            String year = in.next();
            if (!year.isEmpty()) {
                query.put("car_details.year", Integer.parseInt(year));
            }
            System.out.print("Salon: ");
            String salon = in.next();
            if (!salon.isEmpty()) {
                query.put("description.salon", salon);
            }
            System.out.print("Color: ");
            String color = in.next();
            if (!color.isEmpty()) {
                query.put("description.color", color);
            }
            System.out.print("Price (min): ");
            String minPrice = in.next();
            if (!minPrice.isEmpty()) {
                query.put("price", new BasicDBObject("$gte", Integer.parseInt(minPrice)));
            }
            System.out.print("Price (max): ");
            String maxPrice = in.next();
            if (!maxPrice.isEmpty()) {
                query.put("price", new BasicDBObject("$lte", Integer.parseInt(maxPrice)));
            }

            DBCursor cursor = test.find(query);

            while (cursor.hasNext()) {
                DBObject result = cursor.next();
                System.out.println(result);
            }

        } else if (n == 4) {

            System.out.println("Enter your login and password: \n*with space between");
            String login = in.next(), password = in.next();

            BasicDBObject query = new BasicDBObject();
            query.put("login", login);
            query.put("password", password);

            DBCursor cursor = accounts.find(query);

            if (cursor.hasNext()) {
                System.out.println("You entered as an admin, now you can conduct CRUD operations \nWhat do you want to change?");
                System.out.println("1: Create \n2: Read \n3: Update \n4: Delete");
                int n1 = in.nextInt();

                if (n1 == 1) {
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


                } else if (n1 == 2) {
                    System.out.println("Do you want to see all cars or apply filters? \n1: See all cars \n2: Apply filters");
                    int choice = in.nextInt();
                    if (choice == 1) {
                        DBCursor cursor1 = test.find();
                        while (cursor1.hasNext()) {
                            DBObject result = cursor1.next();
                            System.out.println(result);
                        }
                    } else if (choice == 2) {
                        BasicDBObject query2 = new BasicDBObject();

                        System.out.println("Enter the car manufacturer: ");
                        String manufacturer = in.next();
                        query2.put("car_details.manufacturer", manufacturer);

                        System.out.println("Enter the car year: ");
                        String year = in.next();
                        if (!year.isEmpty()) {
                            query2.put("car_details.year", Integer.parseInt(year));
                        }

                        System.out.println("Enter the car color: ");
                        String color = in.next();
                        if (!color.isEmpty()) {
                            query2.put("description.color", color);
                        }

                        System.out.println("Enter the minimum price: ");
                        String minPrice = in.next();
                        if (!minPrice.isEmpty()) {
                            query2.put("price", new BasicDBObject("$gte", Integer.parseInt(minPrice)));
                        }

                        System.out.println("Enter the maximum price: ");
                        String maxPrice = in.next();
                        if (!maxPrice.isEmpty()) {
                            query2.put("price", new BasicDBObject("$lte", Integer.parseInt(maxPrice)));
                        }

                        DBCursor cursor2 = test.find(query2);

                        while (cursor2.hasNext()) {
                            DBObject result = cursor2.next();
                            System.out.println(result);
                        }
                    }
                } else if (n1 == 3) {
                    System.out.println("Enter the details of the car you want to update:");
                    System.out.print("Manufacturer: ");
                    String manufacturer = in.next();
                    System.out.print("Name: ");
                    String name = in.next();
                    System.out.print("Year: ");
                    int year = in.nextInt();

                    BasicDBObject filter = new BasicDBObject();
                    filter.put("car_details.manufacturer", manufacturer);
                    filter.put("car_details.name", name);
                    filter.put("car_details.year", year);

                    System.out.print("Enter the new price of the car: ");
                    int newPrice = in.nextInt();

                    BasicDBObject update = new BasicDBObject();
                    update.put("$set", new BasicDBObject("price", newPrice));

                    test.update(filter, update);

                    System.out.println("Car price updated successfully!");
                }
                else if (n1 == 4) {
                    System.out.println("Enter the details of the car you want to delete:");
                    System.out.print("Manufacturer: ");
                    String manufacturer = in.next();
                    System.out.print("Name: ");
                    String name = in.next();
                    System.out.print("Year: ");
                    int year = in.nextInt();

                    BasicDBObject filter = new BasicDBObject();
                    filter.put("car_details.manufacturer", manufacturer);
                    filter.put("car_details.name", name);
                    filter.put("car_details.year", year);

                    WriteResult result = test.remove(filter);
                    int count = result.getN();
                    if (count > 0) {
                        System.out.println("Car deleted successfully!");
                    } else {
                        System.out.println("No cars found with the specified details.");
                    }
                }
            }}}}


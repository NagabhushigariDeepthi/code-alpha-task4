import java.util.*;
import java.io.*;

class Room {

    int roomNo;
    String category;
    double price;
    boolean available;

    Room(int roomNo, String category, double price) {
        this.roomNo = roomNo;
        this.category = category;
        this.price = price;
        this.available = true;
    }
}

class Reservation {

    int id;
    String customer;
    Room room;

    Reservation(int id, String customer, Room room) {
        this.id = id;
        this.customer = customer;
        this.room = room;
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();

    static Scanner sc = new Scanner(System.in);

    static int reservationId = 1001;

    public static void initializeRooms() {

        rooms.add(new Room(101,"Standard",1500));
        rooms.add(new Room(102,"Standard",1500));

        rooms.add(new Room(201,"Deluxe",2500));
        rooms.add(new Room(202,"Deluxe",2500));

        rooms.add(new Room(301,"Suite",5000));
    }

    public static void searchRooms() {

        System.out.println("\nAvailable Rooms\n");

        for(Room r : rooms){

            if(r.available){

                System.out.println(
                        r.roomNo+"  "
                        +r.category+"  ₹"
                        +r.price);
            }
        }
    }

    public static void bookRoom() {

        System.out.print("Enter Customer Name: ");
        String name = sc.next();

        System.out.print("Enter Room Number: ");
        int number = sc.nextInt();

        for(Room r : rooms){

            if(r.roomNo==number && r.available){

                System.out.println("Amount : ₹"+r.price);

                System.out.println("Payment Processing...");

                System.out.println("Payment Successful!");

                r.available=false;

                Reservation res =
                        new Reservation(
                                reservationId++,
                                name,
                                r
                        );

                reservations.add(res);

                saveBooking(res);

                System.out.println(
                        "Booking Successful");

                return;
            }
        }

        System.out.println("Room Not Available");
    }

    public static void cancelBooking(){

        System.out.print("Enter Reservation ID: ");

        int id=sc.nextInt();

        Iterator<Reservation> it =
                reservations.iterator();

        while(it.hasNext()){

            Reservation res=it.next();

            if(res.id==id){

                res.room.available=true;

                it.remove();

                rewriteFile();

                System.out.println(
                        "Reservation Cancelled");

                return;
            }
        }

        System.out.println("Reservation Not Found");
    }

    public static void viewBookings(){

        if(reservations.isEmpty()){

            System.out.println("No Bookings");

            return;
        }

        for(Reservation r:reservations){

            System.out.println("-------------------");

            System.out.println(
                    "Reservation ID : "+r.id);

            System.out.println(
                    "Customer : "+r.customer);

            System.out.println(
                    "Room : "+r.room.roomNo);

            System.out.println(
                    "Category : "+r.room.category);

            System.out.println(
                    "Amount : ₹"+r.room.price);
        }
    }

    public static void saveBooking(Reservation r){

        try{

            FileWriter fw =
                    new FileWriter(
                            "bookings.txt",
                            true);

            fw.write(
                    r.id+","
                    +r.customer+","
                    +r.room.roomNo+","
                    +r.room.category+","
                    +r.room.price+"\n"
            );

            fw.close();

        }

        catch(Exception e){

            System.out.println(e);
        }
    }

    public static void rewriteFile(){

        try{

            FileWriter fw =
                    new FileWriter(
                            "bookings.txt");

            for(Reservation r:
                    reservations){

                fw.write(
                        r.id+","
                        +r.customer+","
                        +r.room.roomNo+","
                        +r.room.category+","
                        +r.room.price+"\n"
                );
            }

            fw.close();

        }

        catch(Exception e){

            System.out.println(e);
        }
    }

    public static void main(String[] args) {

        initializeRooms();

        while(true){

            System.out.println("\n===== HOTEL RESERVATION =====");

            System.out.println("1.Search Rooms");

            System.out.println("2.Book Room");

            System.out.println("3.Cancel Reservation");

            System.out.println("4.View Bookings");

            System.out.println("5.Exit");

            int choice=sc.nextInt();

            switch(choice){

                case 1:

                    searchRooms();

                    break;

                case 2:

                    bookRoom();

                    break;

                case 3:

                    cancelBooking();

                    break;

                case 4:

                    viewBookings();

                    break;

                case 5:

                    System.exit(0);

                default:

                    System.out.println("Invalid Choice");
            }
        }
    }
}
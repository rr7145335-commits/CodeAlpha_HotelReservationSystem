import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Room {
    int roomNumber;
    String type;
    double price;
    boolean isBooked;
    String customerName;

    Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isBooked = false;
        this.customerName = "";
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1800));
        rooms.add(new Room(201, "Deluxe", 2500));
        rooms.add(new Room(202, "Deluxe", 2800));
        rooms.add(new Room(301, "Suite", 4000));
        rooms.add(new Room(302, "Suite", 4500));

        int choice;

        do {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View All Rooms");
            System.out.println("2. Search Room by Type");
            System.out.println("3. Book Room");
            System.out.println("4. Cancel Booking");
            System.out.println("5. View Booking Details");
            System.out.println("6. Save Booking Details to File");
            System.out.println("7. Exit");
            System.out.print("Enter Choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewAllRooms();
                    break;
                case 2:
                    searchRoomByType();
                    break;
                case 3:
                    bookRoom();
                    break;
                case 4:
                    cancelBooking();
                    break;
                case 5:
                    viewBookingDetails();
                    break;
                case 6:
                    saveBookingDetailsToFile();
                    break;
                case 7:
                    System.out.println("Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 7);
    }

    static void viewAllRooms() {
        System.out.println("\n--- All Rooms ---");
        for (Room room : rooms) {
            System.out.println("Room No: " + room.roomNumber +
                    " | Type: " + room.type +
                    " | Price: ₹" + room.price +
                    " | Status: " + (room.isBooked ? "Booked" : "Available"));
        }
    }

    static void searchRoomByType() {
        System.out.print("Enter Room Type (Standard/Deluxe/Suite): ");
        String type = sc.nextLine();

        boolean found = false;
        System.out.println("\nAvailable " + type + " Rooms:");

        for (Room room : rooms) {
            if (room.type.equalsIgnoreCase(type) && !room.isBooked) {
                System.out.println("Room No: " + room.roomNumber +
                        " | Price: ₹" + room.price);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No available rooms found.");
        }
    }

    static void bookRoom() {
        System.out.print("Enter Room Type to Book (Standard/Deluxe/Suite): ");
        String type = sc.nextLine();

        ArrayList<Room> availableRooms = new ArrayList<>();

        System.out.println("\nAvailable " + type + " Rooms:");

        int option = 1;
        for (Room room : rooms) {
            if (room.type.equalsIgnoreCase(type) && !room.isBooked) {
                availableRooms.add(room);
                System.out.println(option + ". Room No: " + room.roomNumber +
                        " | Price: ₹" + room.price);
                option++;
            }
        }

        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available in this category.");
            return;
        }

        System.out.print("Choose Room Option: ");
        int selected = sc.nextInt();
        sc.nextLine();

        if (selected < 1 || selected > availableRooms.size()) {
            System.out.println("Invalid option.");
            return;
        }

        Room room = availableRooms.get(selected - 1);

        System.out.print("Enter Customer Name: ");
        room.customerName = sc.nextLine();

        System.out.println("Payment Amount: ₹" + room.price);
        System.out.println("Payment Successful!");

        room.isBooked = true;

        System.out.println("Room Booked Successfully!");
        System.out.println("Booked Room No: " + room.roomNumber);
    }

    static void cancelBooking() {
        System.out.print("Enter Room Number to Cancel Booking: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        boolean found = false;

        for (Room room : rooms) {
            if (room.roomNumber == roomNo) {
                found = true;

                if (room.isBooked) {
                    room.isBooked = false;
                    room.customerName = "";
                    System.out.println("Booking Cancelled Successfully!");
                } else {
                    System.out.println("This room is not booked.");
                }
                break;
            }
        }

        if (!found) {
            System.out.println("Room not found.");
        }
    }

    static void viewBookingDetails() {
        System.out.println("\n--- Booking Details ---");

        boolean found = false;

        for (Room room : rooms) {
            if (room.isBooked) {
                System.out.println("Room No: " + room.roomNumber +
                        " | Customer: " + room.customerName +
                        " | Type: " + room.type +
                        " | Paid Amount: ₹" + room.price);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No bookings available.");
        }
    }

    static void saveBookingDetailsToFile() {
        try {
            FileWriter writer = new FileWriter("bookings.txt");

            writer.write("===== HOTEL BOOKING DETAILS =====\n");

            boolean found = false;

            for (Room room : rooms) {
                if (room.isBooked) {
                    writer.write("Room No: " + room.roomNumber +
                            " | Customer: " + room.customerName +
                            " | Type: " + room.type +
                            " | Paid Amount: ₹" + room.price + "\n");
                    found = true;
                }
            }

            if (!found) {
                writer.write("No bookings available.\n");
            }

            writer.close();

            System.out.println("Booking details saved to bookings.txt successfully!");

        } catch (IOException e) {
            System.out.println("Error while saving file.");
        }
    }
}
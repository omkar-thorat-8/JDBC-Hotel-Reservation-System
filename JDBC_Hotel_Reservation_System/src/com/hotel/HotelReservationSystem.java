package com.hotel;

import java.sql.*;
import java.util.Scanner;

public class HotelReservationSystem {

    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "Ankit2631";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println();
                System.out.println("===== HOTEL MANAGEMENT SYSTEM =====");
                System.out.println("1. Reserve a Room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number by Guest Name");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.print("Choose an Option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        reserveRoom(connection, scanner);
                        break;
                    case 2:
                        viewReservations(connection);
                        break;
                    case 3:
                        getRoomNumber(connection, scanner);
                        break;
                    case 4:
                        updateReservation(connection, scanner);
                        break;
                    case 5:
                        deleteReservation(connection, scanner);
                        break;
                    case 0:
                        System.out.println("Thank you! Exiting system...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid Choice, Please Try Again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void reserveRoom(Connection connection, Scanner scanner) {
        scanner.nextLine(); // consume newline
        System.out.print("Enter Guest Name: ");
        String guestName = scanner.nextLine();

        System.out.print("Enter Room Number: ");
        int roomNumber = scanner.nextInt();

        System.out.print("Enter Contact Number: ");
        scanner.nextLine(); // consume newline
        String contactNumber = scanner.nextLine();

        String query = "INSERT INTO reservations (guest_name, room_number, contact_number) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, guestName);
            pstmt.setInt(2, roomNumber);
            pstmt.setString(3, contactNumber);

            int affectedRows = pstmt.executeUpdate();
            System.out.println(affectedRows > 0 ? "Reservation Successful!" : "Reservation Failed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewReservations(Connection connection) {
        String query = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM reservations";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("---- Current Reservations ----");

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                String guestName = resultSet.getString("guest_name");
                int roomNumber = resultSet.getInt("room_number");
                String contactNumber = resultSet.getString("contact_number");
                String reservationDate = resultSet.getTimestamp("reservation_date").toString();

                System.out.println("Reservation ID : " + reservationId);
                System.out.println("Guest Name     : " + guestName);
                System.out.println("Room Number    : " + roomNumber);
                System.out.println("Contact Number : " + contactNumber);
                System.out.println("Reservation Date: " + reservationDate);
                System.out.println("------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getRoomNumber(Connection connection, Scanner scanner) {
        scanner.nextLine(); // consume newline
        System.out.print("Enter Guest Name: ");
        String guestName = scanner.nextLine();

        String query = "SELECT room_number FROM reservations WHERE guest_name = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, guestName);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                System.out.println("Room Number for " + guestName + ": " + roomNumber);
            } else {
                System.out.println("No reservation found for " + guestName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateReservation(Connection connection, Scanner scanner) {
        System.out.print("Enter Reservation ID to Update: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter New Guest Name: ");
        String guestName = scanner.nextLine();
        System.out.print("Enter New Room Number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter New Contact Number: ");
        String contactNumber = scanner.nextLine();

        String query = "UPDATE reservations SET guest_name = ?, room_number = ?, contact_number = ? WHERE reservation_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, guestName);
            pstmt.setInt(2, roomNumber);
            pstmt.setString(3, contactNumber);
            pstmt.setInt(4, reservationId);

            int updated = pstmt.executeUpdate();
            System.out.println(updated > 0 ? "Reservation Updated Successfully!" : "Update Failed. Reservation ID not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteReservation(Connection connection, Scanner scanner) {
        System.out.print("Enter Reservation ID to Delete: ");
        int reservationId = scanner.nextInt();

        String query = "DELETE FROM reservations WHERE reservation_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, reservationId);

            int deleted = pstmt.executeUpdate();
            System.out.println(deleted > 0 ? "Reservation Deleted Successfully!" : "Delete Failed. Reservation ID not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

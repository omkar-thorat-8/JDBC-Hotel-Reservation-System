# 🏨 Hotel Reservation System using JDBC

This repository contains a **Hotel Reservation System** built using **Java and JDBC**.<br>
It is a console-based application that allows users to manage hotel room bookings with full CRUD functionality through database operations.

## 💡 Project Overview
The system enables interaction with a relational database (like MySQL) to store and manage reservation records.<br>
All database communication is handled using **Java Database Connectivity (JDBC)**.

## 📋 Features
- 🛏️ **Reserve a Room** – Add new booking with guest name, room number, and date<br>
- 📄 **View Reservations** – Display all current reservations<br>
- 🔍 **Get Room Number by Guest Name** – Retrieve booking details using guest name<br>
- ✏️ **Update Reservation** – Modify existing reservation details<br>
- ❌ **Delete Reservation** – Cancel an existing reservation<br>
- 🔚 **Exit** – Close the application

## 🛠 Technologies Used
- Java (Core)<br>
- JDBC API<br>
- MySQL (or any RDBMS)<br>
- IDE: IntelliJ IDEA / Eclipse / NetBeans<br>

## 🧱 Database Structure
- Table: `reservations`<br>
  Columns: `id`, `guest_name`, `room_number`, `check_in_date`, `check_out_date`

## 🚀 How to Run
1. Clone this repository<br>
2. Set up your MySQL database and create the `reservations` table<br>
3. Update your database credentials in the source code<br>
4. Compile and run the `HotelReservationSystem.java` main class<br>

```bash
javac HotelReservationSystem.java
java HotelReservationSystem

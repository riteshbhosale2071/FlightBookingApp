# ✈️ Flight Booking App (Android + Firebase)

A modern **Flight Booking Android Application** built using **Java** and
powered by **Firebase**.\
This app allows users to search for flights, select seats, and manage
bookings with a smooth and user-friendly interface.

------------------------------------------------------------------------

## 👥 Team Project

This application was developed as a **group project** by a team of
Android developers as part of academic/learning collaboration.

**Team Members:** 
- Ritesh Bhosale
- Pratik Kore
- Anju Aurade
- Anushri Kawade

------------------------------------------------------------------------

## 📱 Features

-   🔐 User Authentication (Sign Up / Login)
-   🔎 Search available flights
-   🪑 Seat selection system
-   💳 Booking & payment flow (UI-based)
-   🎟️ View ticket details
-   📊 Firebase Realtime Database integration
-   📈 Firebase Analytics integration
-   🎨 Modern Material UI design

------------------------------------------------------------------------

## 🛠️ Tech Stack

### Frontend (Android)

-   Java
-   XML Layouts
-   ViewBinding
-   Material Design Components

### Backend (BaaS)

-   Firebase Authentication\
-   Firebase Realtime Database\
-   Firebase Analytics

### Libraries Used

-   Glide (Image loading)
-   Google Play Services Auth
-   Chip Navigation Bar
-   Guava

------------------------------------------------------------------------

## 📂 Project Structure

    app/
     ┣ Activity/
     ┃ ┣ IntroActivity
     ┃ ┣ LoginActivity
     ┃ ┣ SignUpActivity
     ┃ ┣ MainActivity
     ┃ ┣ SearchActivity
     ┃ ┣ SeatListActivity
     ┃ ┣ PaymentActivity
     ┃ ┗ TicketDetailActivity
     ┣ Adapter/
     ┃ ┣ FlightAdapter
     ┃ ┗ SeatAdapter
     ┣ Model/
     ┃ ┣ Flight
     ┃ ┣ Seat
     ┃ ┗ Location

------------------------------------------------------------------------

## 🔥 Firebase Setup

To run this project, connect it with your own Firebase project:

1.  Go to the Firebase Console

2.  Create a new project

3.  Add an **Android App** with package name:

        com.example.Project1

4.  Download the `google-services.json` file\

5.  Place it inside:

        app/google-services.json

6.  Enable:

    -   Authentication → Email/Password
    -   Realtime Database

------------------------------------------------------------------------

## ▶️ How to Run the Project

1.  Open the project in Android Studio
2.  Sync Gradle
3.  Connect an Android device or start an emulator
4.  Click **Run ▶️**

------------------------------------------------------------------------

## 🔄 App Flow

1.  **Intro Screen** → App introduction\
2.  **Login / Sign Up** → User authentication with Firebase\
3.  **Home Screen** → Browse & search flights\
4.  **Flight Selection** → Choose a flight\
5.  **Seat Selection** → Pick available seats\
6.  **Payment Screen** → Confirm booking\
7.  **Ticket Details** → View booked ticket

------------------------------------------------------------------------

## 🚀 Future Improvements

-   💳 Real payment gateway integration (Stripe/Razorpay)
-   🧾 Booking history
-   🌍 Flight API integration for real-time data
-   🔔 Push notifications
-   👤 User profile management

------------------------------------------------------------------------

## 📜 License

This project is developed for educational purposes. You are free to use
and modify it for learning and non-commercial use.

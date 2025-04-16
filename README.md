# final_project_aad
final project of aad module


Project Description
TravelEase is a comprehensive hotel and tour management system designed to streamline booking operations for both customers and administrators. The system provides:

User Features:

Hotel room bookings with real-time availability

Tour package reservations

Taxi service bookings

Secure payment processing via Stripe

Booking confirmation emails

User account management

Admin Features:

Dashboard with payment analytics

Booking oversight and management

Resource (rooms/tours/taxis) management

User management

The system is built with a Html CSS Javascript and Spring Boot backend, connected to a MySQL database.



![Screenshot 2025-04-16 190053](https://github.com/user-attachments/assets/5efd47db-a0ad-4861-8486-31c640d3039b)
![Screenshot 2025-04-16 190252](https://github.com/user-attachments/assets/6358a5b3-ceba-4ef2-9aff-66af9bec23dc)
![Screenshot 2025-04-16 190417](https://github.com/user-attachments/assets/11b9b5d5-6e99-440a-b2d9-49d89dcf96cf)
![Screenshot 2025-04-16 190436](https://github.com/user-attachments/assets/3f067097-55ad-4816-bd62-719251b56843)
![Screenshot 2025-04-16 190542](https://github.com/user-attachments/assets/2422c14a-2fbc-4c86-a11f-23d0ed296917)
![Screenshot 2025-04-16 190606](https://github.com/user-attachments/assets/fb28b136-a363-4651-8fa6-d9b7c1983280)
![Screenshot 2025-04-16 190636](https://github.com/user-attachments/assets/9d80d62d-3250-4391-8694-5313c5b5b284)
![Screenshot 2025-04-16 190720](https://github.com/user-attachments/assets/b0a5a478-c30d-461f-b8f1-824a77e4c59e)
![Screenshot 2025-04-16 190748](https://github.com/user-attachments/assets/41d0d533-83f4-4bcf-8310-1d1b7da6c722)
![Screenshot 2025-04-16 190849](https://github.com/user-attachments/assets/2578832b-b50e-4fc5-982d-4590626919c1)
![Screenshot 2025-04-16 190950](https://github.com/user-attachments/assets/010abb3e-4327-41b7-b3d8-dfd7b3d51bae)
![Screenshot 2025-04-16 191006](https://github.com/user-attachments/assets/4ffd38cf-d460-4206-8e8b-110214002f14)
![Screenshot 2025-04-16 191031](https://github.com/user-attachments/assets/40d91ac2-d760-4e7b-a56f-3bfda66824e3)
![Screenshot 2025-04-16 191047](https://github.com/user-attachments/assets/bce555c0-20a1-4966-b2ac-6e86340222fd)
![Screenshot 2025-04-16 191113](https://github.com/user-attachments/assets/7a9141e2-b18b-45f1-8531-a5ae7c03f817)
![Screenshot 2025-04-16 191142](https://github.com/user-attachments/assets/1275b7ca-39ed-45c9-a30f-86860c9c27ba)
![Screenshot 2025-04-16 191237](https://github.com/user-attachments/assets/5875b46e-8af8-40b6-b74f-fa3f76ee1a4d)
![Screenshot 2025-04-16 191255](https://github.com/user-attachments/assets/65541fb2-50b4-4260-a900-f8572b3d7324)
![Screenshot 2025-04-16 191309](https://github.com/user-attachments/assets/16449415-b551-4072-98b6-597572d1f728)
![Screenshot 2025-04-16 191327](https://github.com/user-attachments/assets/243587c9-6109-41a2-b6c9-983b4450cced)
![Screenshot 2025-04-16 191345](https://github.com/user-attachments/assets/8a83672b-23ef-4840-aac1-8312b6ba0007)
![Screenshot 2025-04-16 191402](https://github.com/user-attachments/assets/e086845f-3d31-4780-ab24-991f5bc69e10)


Database Configuration:

bash
# Create database
mysql -u root -p
CREATE DATABASE bookingsystem;
exit
Application Configuration:

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/bookingsystem
spring.datasource.username=your_username
spring.datasource.password=your_password
stripe.api.key=your_stripe_secret_key
Run the Application:

bash
mvn spring-boot:run
Frontend Setup
Prerequisites:

HTML
CSS
JAVASCRIPT

Installation:

bash
cd frontend
npm install
Configuration:

Update .env:

APP_API_BASE_URL=http://localhost:8080/api/v1
STRIPE_PUBLISHABLE_KEY=your_stripe_publishable_key



YOUTUBE LINK : 

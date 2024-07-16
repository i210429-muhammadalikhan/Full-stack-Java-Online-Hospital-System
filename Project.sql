create database proj;
use proj;

CREATE TABLE IF NOT EXISTS register (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    passwordd VARCHAR(255) NOT NULL,
	namee VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
	contactinfo VARCHAR(255) NOT NULL,
	role VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS appointment (
    AppointmentId INT AUTO_INCREMENT PRIMARY KEY,
    AppointmentTime VARCHAR(255) NOT NULL,
    AppointmentDate VARCHAR(255) NOT NULL,
    AppointmentStatus VARCHAR(255) NOT NULL,
    UserId INT,
    labname varchar(255),
	FOREIGN KEY (UserId) REFERENCES register(id)
);
CREATE TABLE IF NOT EXISTS payment (
    PaymentId INT AUTO_INCREMENT PRIMARY KEY,
    AppointmentId INT,
    PaymentDate VARCHAR(255) NOT NULL,
    PaymentMethod VARCHAR(255) NOT NULL,
    PaymentStatus VARCHAR(255) NOT NULL,
    PaymentAmount DECIMAL(10, 2) NOT NULL,
	CreditCardPIN VARCHAR(255),
    CreditCardPassword VARCHAR(255),
    FOREIGN KEY (AppointmentId) REFERENCES appointment(AppointmentId)
);
CREATE TABLE IF NOT EXISTS login (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    role1 VARCHAR(255) NOT NULL
);
create table feedbackk
(
 feedbackkid INT , 
  review varchar(255),
  rating int ,
  username VARCHAR(255),
  appointmentId INT
  );
  
  
select* from register;
select* from appointment;
select* from payment;
select* from login;
select* from feedbackk;



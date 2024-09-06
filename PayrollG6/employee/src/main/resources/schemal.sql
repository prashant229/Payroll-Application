CREATE TABLE IF NOT EXISTS `employee` (
 employee_id INT AUTO_INCREMENT PRIMARY KEY,
 first_name VARCHAR(30) NOT NULL,
 last_name VARCHAR(30),
 role VARCHAR(30) NOT NULL,
 gender VARCHAR(30) NOT NULL,
 email_address VARCHAR(40) NOT NULL,
 password VARCHAR(50) NOT NULL,
 manager_id INT NOT NULL,
 contact_number VARCHAR(15) NOT NULL,
 dob DATE
);
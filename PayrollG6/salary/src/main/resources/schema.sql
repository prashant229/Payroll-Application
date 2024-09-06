CREATE TABLE IF NOT EXISTS `salary_history` (
     `salary_history_id` INT AUTO_INCREMENT PRIMARY KEY,
     `emp_id` INT NOT NULL,
     `hra` INT NOT NULL,
     `basic` INT NOT NULL,
     `benefits` INT,
     `date_of_payment` DATE NOT NULL,
     `payment_amount` INT NOT NULL,
     `unpaid_leave_deduction` INT
 );
CREATE TABLE IF NOT EXISTS `salary` (
     `salary_id` INT AUTO_INCREMENT PRIMARY KEY,
     `emp_id` INT NOT NULL,
     `hra` INT NOT NULL,
     `basic` INT NOT NULL,
     `benefits` INT
 );
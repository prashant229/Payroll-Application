CREATE TABLE IF NOT EXISTS `leave_tracker` (
    `leave_tracker_id` INT AUTO_INCREMENT PRIMARY KEY,
    `emp_id` INT NOT NULL,
    `casual_leaves` INT  NOT NULL,
    `sick_leaves` INT NOT NULL,
    `earned_leaves` INT NOT NULL,
    `unpaid_leaves` INT NOT NULL
);


CREATE TABLE IF NOT EXISTS `leave_history` (
    `leave_id` INT AUTO_INCREMENT PRIMARY KEY,
    `emp_id` INT NOT NULL,
    `leave_type` varchar(10) NOT NULL ,
    `start_date` DATE NOT NULL ,
    `end_date` DATE NOT NULL ,
    `reason` VARCHAR(30) ,
    `status` VARCHAR(30) NOT NULL ,
    `rejection_reason` varchar(30) ,
    `no_of_leaves` INT NOT NULL,
    `leave_token` VARCHAR(50) NOT NULL
);
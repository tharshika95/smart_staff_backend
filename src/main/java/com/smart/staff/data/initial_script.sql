

-- Clear existing data
DELETE FROM role;
DELETE FROM user;
DELETE FROM employee;
DELETE FROM designation;
DELETE FROM department;

-- Check and insert ROLE_USER
INSERT INTO role (id, name)
SELECT 1, 'ROLE_USER'
WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'ROLE_USER');

-- Check and insert ROLE_ADMIN
INSERT INTO role (id, name)
SELECT 2, 'ROLE_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'ROLE_ADMIN');

-- Check and insert useraa
INSERT INTO book_stream.`user` (expiration_time,code,email,full_name,password,phone_number,username) VALUES
	 (NULL,NULL,'tharshika@gmail.com',NULL,'$2a$10$.hl8V6BtmOPGKB7m3jXpOOocV1KdEKmMtHgfIsdSTw4H.N2qBzY52','0764469117','tharshika');

-- Inserting data into the Department table
INSERT INTO department (id, name, description, is_active) VALUES
(1, 'Human Resources', 'Handles recruitment, employee relations, and compliance', true),
(2, 'IT', 'Responsible for technology infrastructure and support', true),
(3, 'Finance', 'Manages financial planning, analysis, and reporting', true),
(4, 'Marketing', 'Handles advertising, branding, and customer engagement', true),
(5, 'Sales', 'Responsible for revenue generation and customer acquisition', true);

-- Inserting data into the Designation table
INSERT INTO designation (id, name, description, is_active) VALUES
(1, 'Software Engineer', 'Develops and maintains software applications', true),
(2, 'Senior Software Engineer', 'Leads development teams and designs solutions', true),
(3, 'HR Manager', 'Oversees HR operations and employee well-being', true),
(4, 'Finance Manager', 'Leads financial planning and strategy', true),
(5, 'Marketing Specialist', 'Plans and executes marketing campaigns', true),
(6, 'Sales Executive', 'Generates leads and sales opportunities', true),
(7, 'Senior Sales Executive', 'Leads sales teams and strategy', true),
(8, 'Product Manager', 'Manages product lifecycle from concept to launch', true),
(9, 'Senior Product Manager', 'Leads product strategy and development', true),
(10, 'Accountant', 'Handles financial reporting and accounting tasks', true),
(11, 'Senior Accountant', 'Oversees accounting department and financial reports', true),
(12, 'Business Analyst', 'Analyzes business needs and provides solutions', true),
(13, 'Senior Business Analyst', 'Leads business analysis projects and teams', true),
(14, 'System Administrator', 'Manages IT infrastructure and systems', true),
(15, 'Network Engineer', 'Handles networking and connectivity within the organization', true),
(16, 'Marketing Manager', 'Leads marketing department and strategy', true),
(17, 'Customer Support Representative', 'Assists customers with queries and issues', true),
(18, 'Data Scientist', 'Analyzes data and builds predictive models', true),
(19, 'Senior Data Scientist', 'Leads data analysis and machine learning projects', true),
(20, 'UX/UI Designer', 'Designs user-friendly interfaces for software products', true);

-- Inserting data into the Employee table
INSERT INTO employee (id, emp_id, name, email, date_of_joining, salary, is_active, temporary_address, permanent_address, image_path, contact_no1, contact_no2, department_id, designation_id) VALUES
(1, 'EMP1001', 'Alice Johnson', 'alice.johnson@company.com', '2022-05-15', 55000.00, true, '123 Temporary St', '456 Permanent Rd', '/employees/images/EMP1001.png', '1234567890', '9876543210', 1, 3),
(2, 'EMP1002', 'Bob Smith', 'bob.smith@company.com', '2021-09-12', 85000.00, true, '789 Temporary Blvd', '321 Permanent Ave', '/employees/images/EMP1002.png', '2345678901', '8765432109', 2, 2),
(3, 'EMP1003', 'Charlie Brown', 'charlie.brown@company.com', '2020-03-30', 48000.00, true, '456 Temporary Ln', '987 Permanent Dr', '/employees/images/EMP1003.png', '3456789012', '7654321098', 3, 4),
(4, 'EMP1004', 'David Williams', 'david.williams@company.com', '2023-07-01', 60000.00, true, '321 Temporary Rd', '654 Permanent Blvd', '/employees/images/EMP1004.png', '4567890123', '6543210987', 4, 5),
(5, 'EMP1005', 'Eva Davis', 'eva.davis@company.com', '2019-11-11', 75000.00, true, '654 Temporary Dr', '321 Permanent St', '/employees/images/EMP1005.png', '5678901234', '5432109876', 1, 6),
(6, 'EMP1006', 'Frank Miller', 'frank.miller@company.com', '2022-01-20', 80000.00, true, '987 Temporary Ave', '123 Permanent Blvd', '/employees/images/EMP1006.png', '6789012345', '4321098765', 5, 7),
(7, 'EMP1007', 'Grace Lee', 'grace.lee@company.com', '2021-06-25', 55000.00, true, '123 Temporary St', '456 Permanent Ave', '/employees/images/EMP1007.png', '7890123456', '3210987654', 3, 10),
(8, 'EMP1008', 'Hank Clark', 'hank.clark@company.com', '2018-02-10', 98000.00, true, '654 Temporary Ln', '789 Permanent Rd', '/employees/images/EMP1008.png', '8901234567', '2109876543', 4, 14),
(9, 'EMP1009', 'Ivy Walker', 'ivy.walker@company.com', '2020-07-15', 60000.00, true, '456 Temporary Blvd', '654 Permanent St', '/employees/images/EMP1009.png', '9012345678', '3214567890', 2, 9),
(10, 'EMP1010', 'Jack Taylor', 'jack.taylor@company.com', '2017-05-02', 120000.00, true, '321 Temporary Rd', '789 Permanent Blvd', '/employees/images/EMP1010.png', '2345678901', '5432109876', 5, 12),
(11, 'EMP1011', 'Kathy Robinson', 'kathy.robinson@company.com', '2021-11-10', 50000.00, true, '987 Temporary Ave', '321 Permanent Rd', '/employees/images/EMP1011.png', '3456789012', '6543210987', 3, 5),
(12, 'EMP1012', 'Liam Walker', 'liam.walker@company.com', '2018-12-25', 95000.00, true, '123 Temporary Blvd', '456 Permanent Ln', '/employees/images/EMP1012.png', '4567890123', '8765432109', 2, 6),
(13, 'EMP1013', 'Megan Scott', 'megan.scott@company.com', '2022-04-17', 67000.00, true, '654 Temporary Ave', '987 Permanent St', '/employees/images/EMP1013.png', '5678901234', '7654321098', 1, 4),
(14, 'EMP1014', 'Nathan Adams', 'nathan.adams@company.com', '2023-09-05', 70000.00, true, '789 Temporary Blvd', '321 Permanent Ave', '/employees/images/nathan.jpg', '6789012345', '5432109876', 3, 8),
(15, 'EMP1015', 'Olivia White', 'olivia.white@company.com', '2021-02-17', 59000.00, true, '321 Temporary Rd', '654 Permanent Blvd', '/employees/images/olivia.jpg', '7890123456', '4321098765', 2, 13),
(16, 'EMP1016', 'Peter Harris', 'peter.harris@company.com', '2023-03-30', 55000.00, true, '987 Temporary Ln', '123 Permanent Rd', '/employees/images/peter.jpg', '8901234567', '2109876543', 4, 16),
(17, 'EMP1017', 'Quincy Young', 'quincy.young@company.com', '2020-09-05', 62000.00, true, '654 Temporary Blvd', '987 Permanent Rd', '/employees/images/quincy.jpg', '9012345678', '3214567890', 5, 10),
(18, 'EMP1018', 'Riley Moore', 'riley.moore@company.com', '2022-05-25', 82000.00, true, '123 Temporary Blvd', '456 Permanent St', '/employees/images/riley.jpg', '2345678901', '8765432109', 1, 17),
(19, 'EMP1019', 'Samantha Garcia', 'samantha.garcia@company.com', '2021-01-28', 75000.00, true, '987 Temporary Ln', '123 Permanent Rd', '/employees/images/samantha.jpg', '3456789012', '7654321098', 3, 15),
(20, 'EMP1020', 'Tom Carter', 'tom.carter@company.com', '2019-04-10', 93000.00, true, '654 Temporary Ave', '789 Permanent Blvd', '/employees/images/tom.jpg', '4567890123', '5432109876', 2, 11);


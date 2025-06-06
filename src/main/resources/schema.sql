-- Drop tables if they exist (in reverse order of dependencies)
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS food_order;
DROP TABLE IF EXISTS combo_order;
DROP TABLE IF EXISTS restaurant_table_order;
DROP TABLE IF EXISTS restaurant_order;
DROP TABLE IF EXISTS restaurant_table_reservation;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS restaurant_table;
DROP TABLE IF EXISTS combo_food;
DROP TABLE IF EXISTS combo;
DROP TABLE IF EXISTS food;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS staff;

-- Create tables
CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    image_url TEXT
);

CREATE TABLE food (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(15, 2) NOT NULL,
    quantity INT NOT NULL,
    image_url TEXT,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE combo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(15, 2) NOT NULL,
    image_url TEXT,
    category_id BIGINT
);

CREATE TABLE combo_food (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    combo_id BIGINT,
    food_id BIGINT,
    quantity INT,
    FOREIGN KEY (combo_id) REFERENCES combo(id),
    FOREIGN KEY (food_id) REFERENCES food(id)
);

-- Tạo bảng restaurant_table
CREATE TABLE restaurant_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    capacity INT NOT NULL,
    status ENUM('AVAILABLE', 'OCCUPIED', 'RESERVED') DEFAULT 'AVAILABLE'
);

CREATE TABLE reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    number_of_guests INT NOT NULL,
    reservation_status VARCHAR(20) NOT NULL, -- Enum: PENDING, CONFIRMED, COMPLETED, CANCELLED
    deposit DECIMAL(15, 2) DEFAULT 0.0,
    customer_name VARCHAR(100) NOT NULL,
    customer_phone VARCHAR(20) NOT NULL
);


CREATE TABLE restaurant_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_price DECIMAL(15, 2) NOT NULL,
    note TEXT,
    order_status ENUM('PENDING', 'PROCESSING', 'PROCESSED', 'COMPLETED', 'CANCELLED'),
    order_time DATETIME,
    reservation_id BIGINT,
    FOREIGN KEY (reservation_id) REFERENCES reservation (id) ON DELETE SET NULL
);

-- Tạo bảng RestaurantTableOrder
CREATE TABLE restaurant_table_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    restaurant_table_id BIGINT,
    order_id BIGINT,
    FOREIGN KEY (restaurant_table_id) REFERENCES restaurant_table(id),
    FOREIGN KEY (order_id) REFERENCES restaurant_order(id)
);

CREATE TABLE restaurant_table_reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    restaurant_table_id BIGINT,
    reservation_id BIGINT,
    FOREIGN KEY (restaurant_table_id) REFERENCES restaurant_table(id),
    FOREIGN KEY (reservation_id) REFERENCES reservation(id)
);


CREATE TABLE food_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    price DECIMAL(15, 2) NOT NULL,
    is_completed BOOLEAN DEFAULT FALSE,
    food_id BIGINT,
    order_id BIGINT,
    FOREIGN KEY (food_id) REFERENCES food(id),
    FOREIGN KEY (order_id) REFERENCES restaurant_order(id)
);

CREATE TABLE combo_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    price DECIMAL(15, 2) NOT NULL,
    combo_id BIGINT,
    order_id BIGINT,
    FOREIGN KEY (combo_id) REFERENCES combo(id),
    FOREIGN KEY (order_id) REFERENCES restaurant_order(id)
);

CREATE TABLE payment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_time DATETIME NOT NULL,
    total_price DECIMAL(15, 2) NOT NULL,
    payment_method VARCHAR(100) NOT NULL,
    order_id BIGINT UNIQUE,
    invoice_pdf_path VARCHAR(255),
    FOREIGN KEY (order_id) REFERENCES restaurant_order(id)
);


CREATE TABLE staff (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255),
    dob DATE,
    phone_number VARCHAR(20),
    address VARCHAR(255),
    salary DECIMAL(15,2),
    position VARCHAR(100),
    image_url TEXT
);

-- Tạo bảng user
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    staff_id BIGINT,
    role VARCHAR(50),
    CONSTRAINT fk_users_staff FOREIGN KEY (staff_id) REFERENCES staff(id)
);
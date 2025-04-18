USE qrest_management;
-- Drop tables if they exist (in reverse order of dependencies)
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS food_order;
DROP TABLE IF EXISTS combo_order;
DROP TABLE IF EXISTS restaurant_order;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS restaurant_table;
DROP TABLE IF EXISTS combo_food;
DROP TABLE IF EXISTS combo;
DROP TABLE IF EXISTS food;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS users;

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
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    image_url TEXT,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE combo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    image_url TEXT
);

CREATE TABLE combo_food (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    combo_id BIGINT,
    food_id BIGINT,
    quantity INT,
    FOREIGN KEY (combo_id) REFERENCES combo(id),
    FOREIGN KEY (food_id) REFERENCES food(id)
);

CREATE TABLE restaurant_table (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL,
    capacity INT NOT NULL,
    status VARCHAR(30) NOT NULL CHECK (
        status IN (
            'AVAILABLE', 'OCCUPIED', 'RESERVED'
        )
    )
);


CREATE TABLE reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    number_of_guests INT NOT NULL,
    is_confirmed BOOLEAN DEFAULT FALSE,
    deposit DECIMAL(10, 2) DEFAULT 0.0,
    customer_name VARCHAR(100) NOT NULL,
    customer_phone VARCHAR(20) NOT NULL,
    restaurant_table_id BIGINT,
    FOREIGN KEY (restaurant_table_id) REFERENCES restaurant_table(id)
);

CREATE TABLE restaurant_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_price DECIMAL(10, 2) NOT NULL,
    note TEXT,
    order_status VARCHAR(20) NOT NULL,
    order_time DATETIME NOT NULL,
    restaurant_table_id BIGINT,
    reservation_id BIGINT,
    FOREIGN KEY (restaurant_table_id) REFERENCES restaurant_table(id),
    FOREIGN KEY (reservation_id) REFERENCES reservation(id)
);

CREATE TABLE food_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    food_id BIGINT,
    order_id BIGINT,
    FOREIGN KEY (food_id) REFERENCES food(id),
    FOREIGN KEY (order_id) REFERENCES restaurant_order(id)
);

CREATE TABLE combo_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    combo_id BIGINT,
    order_id BIGINT,
    FOREIGN KEY (combo_id) REFERENCES combo(id),
    FOREIGN KEY (order_id) REFERENCES restaurant_order(id)
);

CREATE TABLE payment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_time DATETIME NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    order_id BIGINT,
    FOREIGN KEY (order_id) REFERENCES restaurant_order(id)
);

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE
);

-- Insert sample data for categories
INSERT INTO category (name, description) VALUES
('Noodles', 'Various types of noodle dishes'),
('Rice', 'Rice-based dishes from various cuisines'),
('Vege', 'Vegetarian dishes'),
('Drink', 'Refreshing beverages'),
('Dessert', 'Sweet courses to end a meal'),
('Fried Food', 'Crispy fried dishes');

-- Insert sample data for foods
INSERT INTO food (name, description, price, quantity, image_url, category_id) VALUES
('Beef Noodles', 'Noodles stir-fried with tender beef and vegetables', 7.99, 50, 'https://img.freepik.com/free-photo/high-angle-vietnamese-dish-with-mint_23-2148381198.jpg', 1),
('Chicken Fried Rice', 'Fried rice with juicy chicken and mixed vegetables', 6.99, 75, 'https://img.freepik.com/free-photo/high-angle-traditional-asian-meal-with-chopsticks_23-2148694371.jpg', 2),
('Vegetarian Salad', 'A healthy mix of fresh vegetables and herbs', 5.49, 30, 'https://img.freepik.com/free-photo/top-view-tasty-salad-with-vegetables_23-2148515491.jpg', 3),
('Pineapple Fried Rice', 'Fragrant rice stir-fried with pineapple, shrimp, and cashews', 8.49, 100, 'https://img.freepik.com/free-photo/high-view-pineapple-plate-with-cutlery_23-2148494708.jpg', 2),
('Spaghetti Bolognese', 'Classic Italian spaghetti with a rich meat sauce', 9.99, 50, 'https://img.taste.com.au/5qlr1PkR/taste/2016/11/spaghetti-bolognese-106560-1.jpeg', 1),
('Vegetable Soup', 'A warming soup made from seasonal vegetables', 4.99, 40, 'https://example.com/vegetable_soup.jpg', 3),
('Fried Chicken', 'Crispy and juicy fried chicken pieces', 6.49, 150, 'https://example.com/fried_chicken.jpg', 6),
('Chicken Wings', 'Spicy grilled chicken wings served with dipping sauce', 5.99, 120, 'https://example.com/chicken_wings.jpg', 6),
('Beef Burger', 'Juicy beef patty with fresh lettuce, tomato, and cheese', 7.49, 80, 'https://img.freepik.com/free-photo/side-view-double-cheeseburger-with-grilled-beef-patties-cheese-lettuce-leaf-burger-buns_141793-4883.jpg', 6),
('Veggie Burger', 'Delicious veggie patty with fresh vegetables', 6.49, 60, 'https://example.com/veggie_burger.jpg', 3),
('Grilled Fish', 'Grilled fish served with a lemon butter sauce', 10.49, 40, 'https://example.com/grilled_fish.jpg', 1),
('Tacos', 'Mexican tacos filled with beef, lettuce, and salsa', 5.49, 90, 'https://example.com/tacos.jpg', 6),
('Fish and Chips', 'Crispy fried fish served with golden fries', 8.99, 110, 'https://example.com/fish_and_chips.jpg', 6),
('Lamb Kebabs', 'Juicy grilled lamb served with rice and vegetables', 11.99, 60, 'https://example.com/lamb_kebabs.jpg', 6),
('Pasta Primavera', 'Pasta tossed with fresh vegetables in a light sauce', 7.99, 70, 'https://example.com/pasta_primavera.jpg', 1),
('Cheeseburger', 'Classic cheeseburger with pickles, onions, and ketchup', 6.99, 130, 'https://example.com/cheeseburger.jpg', 6),
('Mushroom Risotto', 'Creamy risotto with fresh mushrooms and parmesan cheese', 8.49, 50, 'https://example.com/mushroom_risotto.jpg', 3),
('Peking Duck', 'Crispy duck served with pancakes and hoisin sauce', 14.99, 30, 'https://example.com/peking_duck.jpg', 6),
('Pizza Margherita', 'Simple pizza with fresh mozzarella, basil, and tomato sauce', 9.49, 80, 'https://example.com/pizza_margherita.jpg', 1),
('Chocolate Cake', 'Rich chocolate cake with creamy frosting', 5.99, 60, 'https://example.com/chocolate_cake.jpg', 5),
('Pho Bo', 'Traditional Vietnamese beef noodle soup with herbs', 7.49, 60, 'https://img.freepik.com/free-photo/vietnamese-noodle-soup-table_23-2149251213.jpg', 1),
('Bun Cha', 'Grilled pork served with rice vermicelli noodles and dipping sauce', 6.99, 40, 'https://img.freepik.com/free-photo/traditional-vietnamese-bun-cha-meal_23-2149261021.jpg', 1),
('Broken Rice with Grilled Pork', 'Broken rice with grilled pork chop, egg, and pickled vegetables', 7.99, 80, 'https://img.freepik.com/free-photo/vietnamese-broken-rice-dish-with-egg-meat_23-2149261018.jpg', 2),
('Yangzhou Fried Rice', 'Colorful fried rice with sausage, shrimp, and vegetables', 6.49, 90, 'https://img.freepik.com/free-photo/fried-rice-with-vegetables-white-plate_123827-21332.jpg', 2),
('Fresh Spring Rolls', 'Fresh rolls with shrimp, herbs, and rice vermicelli, served with peanut sauce', 4.99, 70, 'https://img.freepik.com/free-photo/vietnamese-spring-rolls-served-with-peanut-sauce_114579-2257.jpg', 3),
('Bubble Milk Tea', 'Sweet milk tea with chewy tapioca pearls', 3.99, 120, 'https://img.freepik.com/free-photo/bubble-tea-drink-glass_1150-35609.jpg', 4),
('Three-Color Dessert', 'Vietnamese dessert with mung beans, jelly, and coconut milk', 4.49, 65, 'https://img.freepik.com/free-photo/vietnamese-dessert-che-ba-mau_1150-34687.jpg', 5),
('Fried Spring Rolls', 'Crispy fried rolls stuffed with minced pork and vegetables', 5.49, 85, 'https://img.freepik.com/free-photo/top-view-fried-spring-rolls-with-chili-sauce_23-2148822692.jpg', 6);

-- Insert sample data for combos
INSERT INTO combo (name, description, price, image_url) VALUES
('Vietnamese Classic', 'A traditional combo featuring Pho Bo, Fried Spring Rolls, and Bubble Milk Tea.', 14.99, 'https://hd1.hotdeal.vn/images/uploads/2015/06/27/153681/153681-pho-bo-uc-pho-bo-gia-truyen-solex-body-1.jpg'),
('Tropical Rice Set', 'Fried rice lovers'' dream: Pineapple Fried Rice, Yangzhou Fried Rice, and Chicken Wings.', 15.99, 'https://img.freepik.com/free-photo/high-view-pineapple-plate-with-cutlery_23-2148494708.jpg'),
('Vegetarian Delight', 'A healthy and colorful mix of Vegetarian Salad, Vegetable Soup, and Mushroom Risotto.', 13.49, 'https://img.freepik.com/free-photo/top-view-tasty-salad-with-vegetables_23-2148515491.jpg'),
('Grill and Chill', 'Perfect for meat lovers – Grilled Fish, Chicken Wings, and Chocolate Cake for dessert.', 16.99, 'https://example.com/grilled_fish.jpg'),
('Fried Feast', 'A crispy combo of Fried Chicken, Fried Spring Rolls, and Fish and Chips.', 16.49, 'https://img.freepik.com/free-photo/top-view-fried-spring-rolls-with-chili-sauce_23-2148822692.jpg');

-- Insert sample data for combo_food relationships
INSERT INTO combo_food (combo_id, food_id) VALUES
(1, 21), -- Vietnamese Classic: Pho Bo
(1, 28), -- Vietnamese Classic: Fried Spring Rolls
(1, 26), -- Vietnamese Classic: Bubble Milk Tea
(2, 3),  -- Tropical Rice Set: Pineapple Fried Rice
(2, 23), -- Tropical Rice Set: Yangzhou Fried Rice
(2, 7),  -- Tropical Rice Set: Chicken Wings
(3, 2),  -- Vegetarian Delight: Vegetarian Salad
(3, 6),  -- Vegetarian Delight: Vegetable Soup
(3, 17), -- Vegetarian Delight: Mushroom Risotto
(4, 11), -- Grill and Chill: Grilled Fish
(4, 7),  -- Grill and Chill: Chicken Wings
(4, 20), -- Grill and Chill: Chocolate Cake
(5, 7),  -- Fried Feast: Fried Chicken
(5, 28), -- Fried Feast: Fried Spring Rolls
(5, 13); -- Fried Feast: Fish and Chips

-- Insert sample data for restaurant tables
INSERT INTO restaurant_table (name, capacity, status) VALUES
('A1', 4, "AVAILABLE"),
('A2', 4, "AVAILABLE"),
('A3', 4, "AVAILABLE"),
('A4', 4, "AVAILABLE"),
('A5', 4, "AVAILABLE"),
('A6', 4, "AVAILABLE"),
('A7', 4, "AVAILABLE"),
('A8', 4, "AVAILABLE"),
('A9', 4, "AVAILABLE"),
('A10', 4, "AVAILABLE"),
('A11', 4, "AVAILABLE"),
('A12', 4, "AVAILABLE"),
('B1', 6, "AVAILABLE"),
('B2', 6, "AVAILABLE"),
('B3', 6, "AVAILABLE"),
('B4', 6, "AVAILABLE"),
('B5', 6, "AVAILABLE"),
('B6', 6, "AVAILABLE"),
('B7', 8, "AVAILABLE"),
('B8', 8, "AVAILABLE"),
('B9', 8, "AVAILABLE"),
('B10', 8, "AVAILABLE"),
('C1', 12, "AVAILABLE"),
('C2', 12, "AVAILABLE"),
('C3', 12, "AVAILABLE"),
('C4', 12, "AVAILABLE"),
('C5', 12, "AVAILABLE"),
('VIP1', 4, "AVAILABLE");


-- Insert sample data for reservations
INSERT INTO reservation (booking_time, arrival_time, number_of_guests, is_confirmed, deposit, customer_name, customer_phone, restaurant_table_id) VALUES
('2024-04-11 18:00:00', '2024-04-11 19:00:00', 4, TRUE, 20.00, 'John Smith', '123-456-7890', 1),
('2024-04-11 19:30:00', '2024-04-11 20:30:00', 6, TRUE, 30.00, 'Jane Doe', '098-765-4321', 13),
('2024-04-12 12:00:00', '2024-04-12 13:00:00', 8, TRUE, 40.00, 'Robert Johnson', '555-123-4567', 20),
('2024-04-12 18:30:00', '2024-04-12 19:30:00', 12, TRUE, 50.00, 'Emily Davis', '777-888-9999', 23),
('2024-04-13 19:00:00', '2024-04-13 20:00:00', 4, FALSE, 0.00, 'Michael Wilson', '222-333-4444', 5),
('2024-04-14 20:00:00', '2024-04-14 21:00:00', 6, FALSE, 0.00, 'Sarah Brown', '111-222-3333', 14),
('2024-04-15 18:00:00', '2024-04-15 19:00:00', 8, FALSE, 0.00, 'David Miller', '444-555-6666', 21),
('2024-04-16 19:30:00', '2024-04-16 20:30:00', 12, FALSE, 0.00, 'Lisa Anderson', '999-888-7777', 24);

-- Insert sample data for orders
INSERT INTO restaurant_order (total_price, note, order_status, order_time, restaurant_table_id, reservation_id) VALUES
(45.97, 'No onions please', 'COMPLETED', '2024-04-11 19:15:00', 1, 1),
(62.97, 'Extra spicy', 'COMPLETED', '2024-04-11 20:45:00', 13, 2),
(89.95, 'Birthday celebration', 'COMPLETED', '2024-04-12 13:15:00', 20, 3),
(120.90, 'Anniversary dinner', 'COMPLETED', '2024-04-12 19:45:00', 23, 4),
(35.98, NULL, 'PENDING', '2024-04-13 19:15:00', 5, 5),
(42.97, 'Allergic to peanuts', 'PROCESSING', '2024-04-14 20:15:00', 14, 6),
(78.96, NULL, 'PENDING', '2024-04-15 18:30:00', 21, 7),
(95.94, 'Special occasion', 'PROCESSING', '2024-04-16 20:00:00', 24, 8),
(55.96, 'Window seat preferred', 'COMPLETED', '2024-04-10 18:30:00', 3, NULL),
(68.95, 'Quiet table please', 'COMPLETED', '2024-04-10 19:45:00', 15, NULL),
(82.94, NULL, 'COMPLETED', '2024-04-09 20:00:00', 22, NULL),
(75.93, 'Extra napkins', 'COMPLETED', '2024-04-09 18:15:00', 8, NULL),
(65.92, 'High chair needed', 'COMPLETED', '2024-04-08 19:30:00', 12, NULL),
(58.91, NULL, 'COMPLETED', '2024-04-08 20:45:00', 17, NULL),
(72.90, 'Celebration cake', 'COMPLETED', '2024-04-07 18:00:00', 25, NULL);

-- Insert sample data for food orders
INSERT INTO food_order (quantity, price, food_id, order_id) VALUES
(2, 7.99, 1, 1),  -- Beef Noodles
(1, 6.99, 2, 1),  -- Chicken Fried Rice
(2, 5.49, 3, 1),  -- Vegetarian Salad
(1, 8.49, 4, 2),  -- Pineapple Fried Rice
(2, 9.99, 5, 2),  -- Spaghetti Bolognese
(1, 4.99, 6, 2),  -- Vegetable Soup
(3, 6.49, 7, 3),  -- Fried Chicken
(2, 5.99, 8, 3),  -- Chicken Wings
(1, 7.49, 9, 3),  -- Beef Burger
(2, 6.49, 10, 4), -- Veggie Burger
(1, 10.49, 11, 4), -- Grilled Fish
(2, 5.49, 12, 4), -- Tacos
(1, 8.99, 13, 5), -- Fish and Chips
(2, 11.99, 14, 5), -- Lamb Kebabs
(1, 7.99, 15, 5), -- Pasta Primavera
(2, 6.99, 16, 6), -- Cheeseburger
(1, 8.49, 17, 6), -- Mushroom Risotto
(2, 14.99, 18, 6), -- Peking Duck
(1, 9.49, 19, 7), -- Pizza Margherita
(2, 5.99, 20, 7), -- Chocolate Cake
(1, 7.49, 21, 7), -- Pho Bo
(2, 6.99, 22, 8), -- Bun Cha
(1, 7.99, 23, 8), -- Broken Rice with Grilled Pork
(2, 6.49, 24, 8), -- Yangzhou Fried Rice
(1, 4.99, 25, 9), -- Fresh Spring Rolls
(2, 3.99, 26, 9), -- Bubble Milk Tea
(1, 4.49, 27, 9), -- Three-Color Dessert
(2, 5.49, 28, 10), -- Fried Spring Rolls
(1, 7.99, 1, 10), -- Beef Noodles
(2, 6.99, 2, 10), -- Chicken Fried Rice
(1, 5.49, 3, 11), -- Vegetarian Salad
(2, 8.49, 4, 11), -- Pineapple Fried Rice
(1, 9.99, 5, 11), -- Spaghetti Bolognese
(2, 4.99, 6, 12), -- Vegetable Soup
(1, 6.49, 7, 12), -- Fried Chicken
(2, 5.99, 8, 12), -- Chicken Wings
(1, 7.49, 9, 13), -- Beef Burger
(2, 6.49, 10, 13), -- Veggie Burger
(1, 10.49, 11, 13), -- Grilled Fish
(2, 5.49, 12, 14), -- Tacos
(1, 8.99, 13, 14), -- Fish and Chips
(2, 11.99, 14, 14), -- Lamb Kebabs
(1, 7.99, 15, 15), -- Pasta Primavera
(2, 6.99, 16, 15), -- Cheeseburger
(1, 8.49, 17, 15); -- Mushroom Risotto

-- Insert sample data for combo orders
INSERT INTO combo_order (quantity, price, combo_id, order_id) VALUES
(1, 14.99, 1, 1), -- Vietnamese Classic
(1, 15.99, 2, 2), -- Tropical Rice Set
(1, 13.49, 3, 3), -- Vegetarian Delight
(1, 16.99, 4, 4), -- Grill and Chill
(1, 16.49, 5, 5), -- Fried Feast
(1, 14.99, 1, 6), -- Vietnamese Classic
(1, 15.99, 2, 7), -- Tropical Rice Set
(1, 13.49, 3, 8), -- Vegetarian Delight
(1, 16.99, 4, 9), -- Grill and Chill
(1, 16.49, 5, 10), -- Fried Feast
(1, 14.99, 1, 11), -- Vietnamese Classic
(1, 15.99, 2, 12), -- Tropical Rice Set
(1, 13.49, 3, 13), -- Vegetarian Delight
(1, 16.99, 4, 14), -- Grill and Chill
(1, 16.49, 5, 15); -- Fried Feast

-- Insert sample data for payments
INSERT INTO payment (payment_time, total_price, payment_method, order_id) VALUES
('2024-04-11 20:30:00', 45.97, 'IN_CASH', 1),
('2024-04-11 22:00:00', 62.97, 'BANK_TRANSFER', 2),
('2024-04-12 14:30:00', 89.95, 'IN_CASH', 3),
('2024-04-12 21:30:00', 120.90, 'BANK_TRANSFER', 4),
('2024-04-13 20:30:00', 35.98, 'IN_CASH', 5),
('2024-04-14 21:30:00', 42.97, 'BANK_TRANSFER', 6),
('2024-04-15 19:30:00', 78.96, 'IN_CASH', 7),
('2024-04-16 21:30:00', 95.94, 'BANK_TRANSFER', 8),
('2024-04-10 19:30:00', 55.96, 'IN_CASH', 9),
('2024-04-10 21:00:00', 68.95, 'BANK_TRANSFER', 10),
('2024-04-09 21:30:00', 82.94, 'IN_CASH', 11),
('2024-04-09 19:30:00', 75.93, 'BANK_TRANSFER', 12),
('2024-04-08 20:30:00', 65.92, 'IN_CASH', 13),
('2024-04-08 22:00:00', 58.91, 'BANK_TRANSFER', 14),
('2024-04-07 19:30:00', 72.90, 'IN_CASH', 15);

-- Insert sample data for users
INSERT INTO users (username, password, full_name, email, phone, role, is_active) VALUES
('admin', 'admin123', 'Admin User', 'admin@restaurant.com', '123-456-7890', 'admin', TRUE),
('waiter', 'waiter123', 'Jane Waiter', 'waiter2@restaurant.com', '777-888-9999', 'waiter', TRUE),
('cashier', 'cashier123', 'Alice Cashier', 'cashier2@restaurant.com', '111-222-3333', 'cashier', TRUE),
('chef', 'chef123', 'Sarah Kitchen', 'kitchen2@restaurant.com', '999-888-7777', 'chef', TRUE); 
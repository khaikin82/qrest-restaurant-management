-- Insert sample data for categories
INSERT INTO category (name, description) VALUES
('Combo', 'Combination meals with multiple items'),
('Noodles', 'Various types of noodle dishes'),
('Rice', 'Rice-based dishes from various cuisines'),
('Vege', 'Vegetarian dishes'),
('Drink', 'Refreshing beverages'),
('Dessert', 'Sweet courses to end a meal'),
('Fried Food', 'Crispy fried dishes');

-- Insert sample data for foods
INSERT INTO food (name, description, price, quantity, image_url, category_id) VALUES
('Beef Noodles', 'Noodles stir-fried with tender beef and vegetables', 7.99, 50, 'https://img.freepik.com/free-photo/high-angle-vietnamese-dish-with-mint_23-2148381198.jpg', 2),
('Chicken Fried Rice', 'Fried rice with juicy chicken and mixed vegetables', 6.99, 75, 'https://img.freepik.com/free-photo/high-angle-traditional-asian-meal-with-chopsticks_23-2148694371.jpg', 3),
('Vegetarian Salad', 'A healthy mix of fresh vegetables and herbs', 5.49, 30, 'https://img.freepik.com/free-photo/top-view-tasty-salad-with-vegetables_23-2148515491.jpg', 4),
('Pineapple Fried Rice', 'Fragrant rice stir-fried with pineapple, shrimp, and cashews', 8.49, 100, 'https://img.freepik.com/free-photo/high-view-pineapple-plate-with-cutlery_23-2148494708.jpg', 3),
('Spaghetti Bolognese', 'Classic Italian spaghetti with a rich meat sauce', 9.99, 50, 'https://img.taste.com.au/5qlr1PkR/taste/2016/11/spaghetti-bolognese-106560-1.jpeg', 2),
('Vegetable Soup', 'A warming soup made from seasonal vegetables', 4.99, 40, 'https://example.com/vegetable_soup.jpg', 4),
('Fried Chicken', 'Crispy and juicy fried chicken pieces', 6.49, 150, 'https://example.com/fried_chicken.jpg', 7),
('Chicken Wings', 'Spicy grilled chicken wings served with dipping sauce', 5.99, 120, 'https://example.com/chicken_wings.jpg', 7),
('Beef Burger', 'Juicy beef patty with fresh lettuce, tomato, and cheese', 7.49, 80, 'https://img.freepik.com/free-photo/side-view-double-cheeseburger-with-grilled-beef-patties-cheese-lettuce-leaf-burger-buns_141793-4883.jpg', 7),
('Veggie Burger', 'Delicious veggie patty with fresh vegetables', 6.49, 60, 'https://example.com/veggie_burger.jpg', 4),
('Grilled Fish', 'Grilled fish served with a lemon butter sauce', 10.49, 40, 'https://example.com/grilled_fish.jpg', 2),
('Tacos', 'Mexican tacos filled with beef, lettuce, and salsa', 5.49, 90, 'https://example.com/tacos.jpg', 7),
('Fish and Chips', 'Crispy fried fish served with golden fries', 8.99, 110, 'https://example.com/fish_and_chips.jpg', 7),
('Lamb Kebabs', 'Juicy grilled lamb served with rice and vegetables', 11.99, 60, 'https://example.com/lamb_kebabs.jpg', 7),
('Pasta Primavera', 'Pasta tossed with fresh vegetables in a light sauce', 7.99, 70, 'https://example.com/pasta_primavera.jpg', 2),
('Cheeseburger', 'Classic cheeseburger with pickles, onions, and ketchup', 6.99, 130, 'https://example.com/cheeseburger.jpg', 7),
('Mushroom Risotto', 'Creamy risotto with fresh mushrooms and parmesan cheese', 8.49, 50, 'https://example.com/mushroom_risotto.jpg', 4),
('Peking Duck', 'Crispy duck served with pancakes and hoisin sauce', 14.99, 30, 'https://example.com/peking_duck.jpg', 7),
('Pizza Margherita', 'Simple pizza with fresh mozzarella, basil, and tomato sauce', 9.49, 80, 'https://example.com/pizza_margherita.jpg', 2),
('Chocolate Cake', 'Rich chocolate cake with creamy frosting', 5.99, 60, 'https://example.com/chocolate_cake.jpg', 6),
('Pho Bo', 'Traditional Vietnamese beef noodle soup with herbs', 7.49, 60, 'https://img.freepik.com/free-photo/vietnamese-noodle-soup-table_23-2149251213.jpg', 2),
('Bun Cha', 'Grilled pork served with rice vermicelli noodles and dipping sauce', 6.99, 40, 'https://img.freepik.com/free-photo/traditional-vietnamese-bun-cha-meal_23-2149261021.jpg', 2),
('Broken Rice with Grilled Pork', 'Broken rice with grilled pork chop, egg, and pickled vegetables', 7.99, 80, 'https://img.freepik.com/free-photo/vietnamese-broken-rice-dish-with-egg-meat_23-2149261018.jpg', 3),
('Yangzhou Fried Rice', 'Colorful fried rice with sausage, shrimp, and vegetables', 6.49, 90, 'https://img.freepik.com/free-photo/fried-rice-with-vegetables-white-plate_123827-21332.jpg', 3),
('Fresh Spring Rolls', 'Fresh rolls with shrimp, herbs, and rice vermicelli, served with peanut sauce', 4.99, 70, 'https://img.freepik.com/free-photo/vietnamese-spring-rolls-served-with-peanut-sauce_114579-2257.jpg', 4),
('Bubble Milk Tea', 'Sweet milk tea with chewy tapioca pearls', 3.99, 120, 'https://img.freepik.com/free-photo/bubble-tea-drink-glass_1150-35609.jpg', 5),
('Three-Color Dessert', 'Vietnamese dessert with mung beans, jelly, and coconut milk', 4.49, 65, 'https://img.freepik.com/free-photo/vietnamese-dessert-che-ba-mau_1150-34687.jpg', 6),
('Fried Spring Rolls', 'Crispy fried rolls stuffed with minced pork and vegetables', 5.49, 85, 'https://img.freepik.com/free-photo/top-view-fried-spring-rolls-with-chili-sauce_23-2148822692.jpg', 7);

-- Insert sample data for combos
INSERT INTO combo (name, description, price, image_url, category_id) VALUES
('Vietnamese Classic', 'A traditional combo featuring Pho Bo, Fried Spring Rolls, and Bubble Milk Tea.', 14.99,
'https://hd1.hotdeal.vn/images/uploads/2015/06/27/153681/153681-pho-bo-uc-pho-bo-gia-truyen-solex-body-1.jpg', 1),
('Tropical Rice Set', 'Fried rice lovers'' dream: Pineapple Fried Rice, Yangzhou Fried Rice, and Chicken Wings.', 15.99,
 'https://img.freepik.com/free-photo/high-view-pineapple-plate-with-cutlery_23-2148494708.jpg', 1),
('Vegetarian Delight', 'A healthy and colorful mix of Vegetarian Salad, Vegetable Soup, and Mushroom Risotto.', 13.49,
'https://img.freepik.com/free-photo/top-view-tasty-salad-with-vegetables_23-2148515491.jpg', 1),
('Grill and Chill', 'Perfect for meat lovers – Grilled Fish, Chicken Wings, and Chocolate Cake for dessert.', 16.99,
'https://example.com/grilled_fish.jpg', 1),
('Fried Feast', 'A crispy combo of Fried Chicken, Fried Spring Rolls, and Fish and Chips.', 16.49,
'https://img.freepik.com/free-photo/top-view-fried-spring-rolls-with-chili-sauce_23-2148822692.jpg', 1);

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
('A3', 4, "OCCUPIED"),
('A4', 4, "AVAILABLE"),
('A5', 4, "OCCUPIED"),
('A6', 4, "OCCUPIED"),
('A7', 4, "RESERVED"),
('A8', 4, "RESERVED"),
('A9', 4, "AVAILABLE"),
('A10', 4, "AVAILABLE"),
('A11', 4, "AVAILABLE"),
('A12', 4, "AVAILABLE"),
('B1', 6, "AVAILABLE"),
('B2', 6, "AVAILABLE"),
('B3', 6, "OCCUPIED"),
('B4', 6, "AVAILABLE"),
('B5', 6, "OCCUPIED"),
('B6', 6, "AVAILABLE"),
('B7', 8, "AVAILABLE"),
('B8', 8, "AVAILABLE"),
('B9', 8, "AVAILABLE"),
('B10', 8, "AVAILABLE"),
('C1', 2, "AVAILABLE"),
('C2', 2, "RESERVED"),
('C3', 2, "AVAILABLE"),
('C4', 2, "AVAILABLE"),
('C5', 2, "AVAILABLE"),
('VIP1', 4, "AVAILABLE");


-- Insert sample data for reservations (full name, Vietnamese phone format, USD deposit)
INSERT INTO reservation (
    booking_time, arrival_time, number_of_guests,
    reservation_status, deposit, customer_name, customer_phone
) VALUES
('2024-04-11 18:00:00', '2024-04-11 19:00:00', 4, 'CONFIRMED', 20.00, 'Johnathan Smith', '0901234567'),
('2024-04-11 19:30:00', '2024-04-11 20:30:00', 6, 'CONFIRMED', 30.00, 'Isabella Nguyen', '0912345678'),
('2024-04-12 12:00:00', '2024-04-12 13:00:00', 8, 'CONFIRMED', 40.00, 'Robert Johnson', '0923456789'),
('2024-04-12 18:30:00', '2024-04-12 19:30:00', 12, 'CONFIRMED', 60.00, 'Emily Davis', '0934567890'),
('2024-04-13 19:00:00', '2024-04-13 20:00:00', 4, 'PENDING', 0.00, 'Michael Wilson', '0945678901'),
('2024-04-14 20:00:00', '2024-04-14 21:00:00', 6, 'PENDING', 0.00, 'Sarah Brown', '0956789012'),
('2024-04-15 18:00:00', '2024-04-15 19:00:00', 8, 'PENDING', 0.00, 'David Miller', '0967890123'),
('2024-04-16 19:30:00', '2024-04-16 20:30:00', 12, 'PENDING', 0.00, 'Lisa Anderson', '0978901234'),

-- COMPLETED
('2024-04-05 18:00:00', '2024-04-05 19:00:00', 3, 'COMPLETED', 15.00, 'Alex Turner', '0900000001'),
('2024-04-06 19:00:00', '2024-04-06 20:00:00', 5, 'COMPLETED', 25.00, 'Nina Dobrev', '0900000002'),

-- CANCELLED
('2024-04-07 18:30:00', '2024-04-07 19:30:00', 4, 'CANCELLED', 0.00, 'Chris Evans', '0900000003'),
('2024-04-08 20:00:00', '2024-04-08 21:00:00', 6, 'CANCELLED', 0.00, 'Emma Watson', '0900000004'),

-- CONFIRMED (mới)
('2024-04-17 17:00:00', '2024-04-17 18:00:00', 2, 'CONFIRMED', 10.00, 'Mark Ruffalo', '0900000005'),

-- PENDING (mới)
('2024-04-18 19:30:00', '2024-04-18 20:30:00', 7, 'PENDING', 0.00, 'Zendaya Coleman', '0900000006');


INSERT INTO restaurant_table_reservation (restaurant_table_id, reservation_id) VALUES
(1, 1),     -- Johnathan Smith
(13, 2),    -- Isabella Nguyen
(2, 3),     -- Robert Johnson
(3, 3),
(1, 4),     -- Emily Davis
(15, 4),
(4, 5),     -- Michael Wilson
(14, 6),    -- Sarah Brown
(20, 7),    -- David Miller
(15, 8),    -- Lisa Anderson
(16, 8),
(5, 9),     -- Alex Turner (COMPLETED)
(6, 10),    -- Nina Dobrev
(7, 11),    -- Chris Evans (CANCELLED)
(8, 12),    -- Emma Watson
(9, 13),    -- Mark Ruffalo
(10, 14);   -- Zendaya Coleman


-- Insert sample data for restaurant_order
INSERT INTO restaurant_order (total_price, note, order_status, order_time, reservation_id) VALUES
(45.97, 'No onions please', 'COMPLETED', '2024-04-11 19:15:00', 1),
(62.97, 'Extra spicy', 'COMPLETED', '2024-04-11 20:45:00', 2),
(89.95, 'Birthday celebration', 'COMPLETED', '2024-04-12 13:15:00', 3),
(120.90, 'Anniversary dinner', 'COMPLETED', '2024-04-12 19:45:00', 4),
(35.98, NULL, 'PENDING', '2024-04-13 19:15:00', NULL),
(42.97, 'Allergic to peanuts', 'PROCESSING', '2024-04-14 20:15:00', NULL),
(78.96, NULL, 'PENDING', '2024-04-15 18:30:00', NULL),
(95.94, 'Special occasion', 'PROCESSING', '2024-04-16 20:00:00', NULL),
(55.96, 'Window seat preferred', 'COMPLETED', '2024-04-10 18:30:00', 9),
(68.95, 'Quiet table please', 'COMPLETED', '2024-04-10 19:45:00', 10),
(82.94, NULL, 'COMPLETED', '2024-04-09 20:00:00', NULL),
(75.93, 'Extra napkins', 'COMPLETED', '2024-04-09 18:15:00', NULL),
(65.92, 'High chair needed', 'COMPLETED', '2024-04-08 19:30:00', 13),
(58.91, NULL, 'COMPLETED', '2024-04-08 20:45:00', 14),
(72.90, 'Celebration cake', 'COMPLETED', '2024-04-07 18:00:00', NULL);


INSERT INTO restaurant_table_order (restaurant_table_id, order_id) VALUES
(1, 1),
(2, 1),   -- order 1 có thêm bàn 2
(3, 1),   -- order 1 có thêm bàn 3
(13, 2),
(20, 3),
(6, 3),   -- order 3 có thêm bàn 6
(23, 4),
(5, 5),
(9, 5),   -- order 5 có thêm bàn 9
(14, 6),
(21, 7),
(24, 8),
(3, 9),
(15, 10),
(16, 10), -- order 10 có thêm bàn 16
(22, 11),
(8, 12),
(12, 13),
(17, 14),
(25, 15);

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
('2024-04-14 21:30:00', 42.97, 'BANK_TRANSFER', 6);


INSERT INTO staff (full_name, dob, phone_number, address, salary, position, image_url) VALUES
('Nguyễn Minh Quân', '2001-03-15', '0901000001', 'Hà Nội', 400.00, 'WAITER', 'https://cdn.eva.vn/upload/1-2021/images/2021-03-28/images2796494_1dep-1616935824-313-width660height661.jpg'),
('Trần Thị Thuỳ Dung', '2000-07-22', '0901000002', 'TP.HCM', 500.00, 'CASHIER', 'https://kenh14cdn.com/2019/3/7/5301629225053921961409043040056750658027520n-1551924968498331276673.jpg'),
('Lê Hoàng Nam', '1998-11-30', '0901000003', 'Đà Nẵng', 800.00, 'CHEF', 'https://cdn11.dienmaycholon.vn/filewebdmclnew/public/userupload/files/Image%20FP_2024/hinh-anime-4.jpg'),
('Phạm Anh Tuấn', '2002-05-09', '0901000004', 'Cần Thơ', 450.00, 'WAITER', 'https://sieuthihanguc.net/wp-content/uploads/2024/09/anime-girl-13tXs9iN.webp'),
('Võ Thị Kim Ngân', '2000-09-01', '0901000005', 'Huế', 550.00, 'CASHIER', NULL),
('Đỗ Văn Hậu', '1999-06-18', '0901000006', 'Hải Phòng', 1300.00, 'CHEF', 'https://scontent.fhan14-4.fna.fbcdn.net/v/t39.30808-6/468719770_1245762753206282_4515101368064360920_n.jpg?_nc_cat=102&ccb=1-7&_nc_sid=a5f93a&_nc_ohc=NubtYD-8YrYQ7kNvwEXBYee&_nc_oc=AdlrvXOax5JzOhVNO0__rkDeih0ZMUCy1v-dTz_PZMa3R17EkerUKl8Vb793tiyLueo&_nc_zt=23&_nc_ht=scontent.fhan14-4.fna&_nc_gid=gKsDNnlqLlgiGSySrel25Q&oh=00_AfEaoz08irqT04pHOIEZeJBx-spcVkP0kQku_tn1ZlsjUA&oe=680A78FA'),
('Nguyễn Thị Lan', '2003-12-25', '0901000007', 'Hà Nội', 350.00, 'WAITER', 'https://scontent.fhan14-2.fna.fbcdn.net/v/t1.6435-9/118313275_970083550134039_6814075035813550769_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=94e2a3&_nc_ohc=PMCch095PU0Q7kNvwFEA3CX&_nc_oc=Adm5igUWGykrXk_PRmRq-GcMn3zFcc3LpzOmI0SpZ4TTt8dj1fh1vVz4zWoZGDDyvd4&_nc_zt=23&_nc_ht=scontent.fhan14-2.fna&_nc_gid=DhrnEnwzky8dISh2CgM94w&oh=00_AfE2LHIqrucybCCTSzzOA2dRC4G4tNntJfiRM4BexmCv0w&oe=682C24DF'),
('Trần Quốc Bảo', '2001-01-20', '0901000008', 'TP.HCM', 600.00, 'CASHIER', NULL),
('Lê Thị Bích Phượng', '2000-08-12', '0901000009', 'Đà Nẵng', 600.00, 'CHEF', 'https://scontent.fhan14-5.fna.fbcdn.net/v/t39.30808-6/476378837_2000516083766848_7211361303318404140_n.jpg?_nc_cat=104&ccb=1-7&_nc_sid=a5f93a&_nc_ohc=mOatBrf6TzAQ7kNvwF51dWj&_nc_oc=Adn95kkg-ohrk1yWL9Zcsp219rqdZsYSEapnkoJ6KkSwXYv0fnYucnp8iER58_x9IaY&_nc_zt=23&_nc_ht=scontent.fhan14-5.fna&_nc_gid=-iqn7b1VQHWbgoh6yQcGXA&oh=00_AfECRGFimYnVb5_SBDpfZ2IR79jJI9cxpcYFO7PwY06x4w&oe=680A769E'),
('Phạm Trung Kiên', '2002-04-14', '0901000010', 'Cần Thơ', 500.00, 'WAITER', NULL),
('Hoàng Ngọc Hà', '2003-10-05', '0901000011', 'Huế', 300.00, 'CASHIER', NULL),
('Đinh Văn Khải', '1997-07-07', '0901000012', 'Hà Nội', 1000.00, 'CHEF', NULL),
('Nguyễn Thanh Tùng', '2001-11-01', '0901000013', 'TP.HCM', 550.00, 'WAITER', NULL),
('Lê Thị Mai', '2000-02-28', '0901000014', 'Hải Dương', 600.00, 'CASHIER', NULL),
('Trần Minh Đức', '1996-03-03', '0901000015', 'Đà Nẵng', 1400.00, 'CHEF', NULL),
('Vũ Thị Hằng', '2002-06-16', '0901000016', 'Hà Nội', 400.00, 'WAITER', NULL),
('Phan Nhật Huy', '2000-09-19', '0901000017', 'TP.HCM', 550.00, 'CASHIER', NULL),
('Đoàn Thị Như Quỳnh', '2001-01-11', '0901000018', 'Huế', 650.00, 'CHEF', NULL),
('Ngô Hoàng Long', '2002-08-08', '0901000019', 'Đồng Nai', 500.00, 'WAITER', NULL),
('Nguyễn Thị Diễm My', '2003-05-23', '0901000020', 'Cần Thơ', 400.00, 'CASHIER', NULL),
('Ngô Văn Hậu', '1995-12-10', '0901000006', 'Bình Dương', 350.00, 'DISHWASHER', NULL),
('Đặng Thị Mai', '1999-04-25', '0901000007', 'Nghệ An', 360.00, 'DISHWASHER', NULL),
('Trần Văn Phú', '1997-02-14', '0901000008', 'Quảng Nam', 370.00, 'DISHWASHER', NULL),
('Lý Thành Trung', '1994-08-19', '0901000009', 'Gia Lai', 420.00, 'SECURITY', NULL),
('Nguyễn Thị Thảo', '1996-06-05', '0901000010', 'Khánh Hòa', 410.00, 'SECURITY', NULL),
('Phạm Văn Khánh', '1992-03-27', '0901000011', 'Bắc Ninh', 430.00, 'SECURITY', NULL),
('Bùi Thị Hồng', '2001-10-03', '0901000012', 'Hòa Bình', 300.00, 'CLEANER', NULL),
('Trần Văn Đông', '2000-01-17', '0901000013', 'Vĩnh Long', 310.00, 'CLEANER', NULL);

INSERT INTO users (username, password, role, staff_id) VALUES
('admin', '$2a$10$MQKxiH6JvMlc6jjP5Vhi4OnjfmSdHkMfWrbhai5Oex2VJOBJGuIDe', 'ADMIN', NULL),
('khai', '$2a$10$0I8M.7sXKDY0kac0y7cLoOuJ1jtePPc.HQhsyVZD9Z.vpliUW37GK', 'ADMIN', NULL),
('waiter', '$2a$10$MSn.cDJqsuXqyD7nJiCO4.zg60x7XbdPzfXAJX/VAfLaGRsO3HDkO', 'WAITER', NULL),
('waiter001', '$2a$10$c248l2Hr7fRG5TkFQbHqjeXE98ovaE/E8dhweedYKi6Ikw4tHXRe.', 'WAITER', NULL),
('cashier',  '$2a$10$RLB7Zu2Q6Hg1yU80C6vjfeqCIs2WywI9ZfakovHD9kmmjd4oZEf6W', 'CASHIER', NULL),
('chef', '$2a$10$YSzUQ9aNCMXHI7xMrNkbjeIv2JHgUC51pNzz4GGFDbvInDl4nZW0i', 'CHEF', NULL),
('user', '$2a$10$ulydMg8pXIm/X2UBSfEhnuHN.jhJwnuspHPRWbcIec3jiN0hy.UEO', 'USER', NULL);


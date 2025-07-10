-- 👤 Insert ONE OWNER user
INSERT INTO Users (name, phone_number, email, password, role, is_active)
VALUES
    ('Test Owner', '9999999999', 'owner@test.com', 'owner_encrypted_pwd', 'OWNER', TRUE);

-- 🍽️ Insert restaurant owned by this user
INSERT INTO Restaurants (name, owner_id, type, address, is_active, is_open)
VALUES
    ('Testaurant',
     (SELECT id FROM Users WHERE phone_number = '9999999999'),
     'BOTH',
     '456 Test Street, Kochi',
     TRUE,
     TRUE);

-- 🗂️ Insert 3 default categories (created_by = NULL)
INSERT INTO Categories (name, created_by, is_default, display_order, is_active)
VALUES
    ('Starters', NULL, TRUE, 1, TRUE),
    ('Main Course', NULL, TRUE, 2, TRUE),
    ('Desserts', NULL, TRUE, 3, TRUE);

-- 🗂️ Insert 2 custom categories by the user
INSERT INTO Categories (name, created_by, is_default, display_order, is_active)
VALUES
    ('Owner Specials',
     (SELECT id FROM Users WHERE phone_number = '9999999999'),
     FALSE, 4, TRUE),
    ('Fusion Bites',
     (SELECT id FROM Users WHERE phone_number = '9999999999'),
     FALSE, 5, TRUE);

-- 🍜 Insert 3 default items
INSERT INTO Items (name, created_by, is_default, is_veg, is_active)
VALUES
    ('Veg Spring Roll', NULL, TRUE, TRUE, TRUE),
    ('Butter Chicken', NULL, TRUE, FALSE, TRUE),
    ('Brownie with Ice Cream', NULL, TRUE, TRUE, TRUE);

-- 🍜 Insert 2 custom items by the user
INSERT INTO Items (name, created_by, is_default, is_veg, is_active)
VALUES
    ('Owner’s Spicy Paneer',
     (SELECT id FROM Users WHERE phone_number = '9999999999'),
     FALSE, TRUE, TRUE),
    ('Tandoori Momos',
     (SELECT id FROM Users WHERE phone_number = '9999999999'),
     FALSE, FALSE, TRUE);

-- 📋 Link all 5 items to appropriate categories and the restaurant
-- Default Items
INSERT INTO RestaurantItems (restaurant_id, item_id, category_id, price, is_available, display_order)
VALUES
    (
        (SELECT id FROM Restaurants WHERE name = 'Testaurant'),
        (SELECT id FROM Items WHERE name = 'Veg Spring Roll'),
        (SELECT id FROM Categories WHERE name = 'Starters'),
        120.00, TRUE, 1
    ),
    (
        (SELECT id FROM Restaurants WHERE name = 'Testaurant'),
        (SELECT id FROM Items WHERE name = 'Butter Chicken'),
        (SELECT id FROM Categories WHERE name = 'Main Course'),
        220.00, TRUE, 2
    ),
    (
        (SELECT id FROM Restaurants WHERE name = 'Testaurant'),
        (SELECT id FROM Items WHERE name = 'Brownie with Ice Cream'),
        (SELECT id FROM Categories WHERE name = 'Desserts'),
        90.00, TRUE, 3
    );

-- Custom Items
INSERT INTO RestaurantItems (restaurant_id, item_id, category_id, price, is_available, display_order)
VALUES
    (
        (SELECT id FROM Restaurants WHERE name = 'Testaurant'),
        (SELECT id FROM Items WHERE name = 'Owner’s Spicy Paneer'),
        (SELECT id FROM Categories WHERE name = 'Owner Specials'),
        180.00, TRUE, 4
    ),
    (
        (SELECT id FROM Restaurants WHERE name = 'Testaurant'),
        (SELECT id FROM Items WHERE name = 'Tandoori Momos'),
        (SELECT id FROM Categories WHERE name = 'Fusion Bites'),
        150.00, TRUE, 5
    );

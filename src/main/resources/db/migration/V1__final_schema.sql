CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(100) NOT NULL,
                       phone_number VARCHAR(15) UNIQUE NOT NULL,
                       email VARCHAR(100),
                       password VARCHAR(255) NOT NULL,
                       role ENUM('OWNER', 'ADMIN') NOT NULL,
                       is_active BOOLEAN DEFAULT TRUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       CONSTRAINT unique_email UNIQUE (email)
);

CREATE TABLE restaurants (
                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             name VARCHAR(150) NOT NULL,
                             owner_id BIGINT,
                             type ENUM('VEG', 'NON_VEG', 'BOTH') NOT NULL,
                             address TEXT NOT NULL,
                             is_active BOOLEAN DEFAULT TRUE,
                             is_open BOOLEAN DEFAULT FALSE,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             CONSTRAINT fk_restaurant_owner FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE categories (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(100) NOT NULL,
                            created_by BIGINT,
                            is_default BOOLEAN DEFAULT TRUE,
                            display_order INT DEFAULT 0,
                            is_active BOOLEAN DEFAULT TRUE,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            CONSTRAINT fk_category_creator FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE items (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(150) NOT NULL,
                       created_by BIGINT,
                       is_default BOOLEAN DEFAULT TRUE,
                       is_veg BOOLEAN DEFAULT TRUE,
                       is_active BOOLEAN DEFAULT TRUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       CONSTRAINT fk_item_creator FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE restaurantitems (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                 restaurant_id BIGINT NOT NULL,
                                 item_id BIGINT NOT NULL,
                                 category_id BIGINT NOT NULL,
                                 price DECIMAL(10,2) NOT NULL,
                                 is_available BOOLEAN DEFAULT TRUE,
                                 custom_name VARCHAR(150),
                                 display_order INT DEFAULT 0,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 CONSTRAINT fk_ri_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE,
                                 CONSTRAINT fk_ri_item FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE,
                                 CONSTRAINT fk_ri_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
                                 CONSTRAINT uq_restaurant_item_category UNIQUE (restaurant_id, item_id, category_id)
);

CREATE TABLE otp_verifications (
                                   phone_number VARCHAR(15) PRIMARY KEY,
                                   otp VARCHAR(10) NOT NULL,
                                   generated_at TIMESTAMP NOT NULL
);

CREATE TABLE restaurants (
                             id UUID PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             street VARCHAR(255) NOT NULL,
                             city VARCHAR(100) NOT NULL,
                             province VARCHAR(100) NOT NULL,
                             postal_code VARCHAR(20) NOT NULL,
                             open BOOLEAN NOT NULL
);

CREATE TABLE restaurant_menu_items (
                                       id UUID PRIMARY KEY,
                                       restaurant_id UUID NOT NULL,
                                       name VARCHAR(255) NOT NULL,
                                       price NUMERIC(10, 2) NOT NULL,
                                       available BOOLEAN NOT NULL,
                                       CONSTRAINT fk_restaurant_menu_items_restaurant
                                           FOREIGN KEY (restaurant_id) REFERENCES restaurants (id)
);

INSERT INTO restaurants (id, name, street, city, province, postal_code, open) VALUES
                                                                                  ('33333333-3333-3333-3333-333333333333', 'Pizza Palace', '789 King St', 'Montreal', 'Quebec', 'H2X3Y7', TRUE),
                                                                                  ('44444444-4444-4444-4444-444444444444', 'Burger Town', '99 Queen Rd', 'Brossard', 'Quebec', 'J4Y1A2', TRUE);

INSERT INTO restaurant_menu_items (id, restaurant_id, name, price, available) VALUES
                                                                                  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '33333333-3333-3333-3333-333333333333', 'Pepperoni Pizza', 18.99, TRUE),
                                                                                  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '33333333-3333-3333-3333-333333333333', 'Veggie Pizza', 17.49, TRUE),
                                                                                  ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb1', '44444444-4444-4444-4444-444444444444', 'Classic Burger', 13.99, TRUE),
                                                                                  ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb2', '44444444-4444-4444-4444-444444444444', 'Fries', 4.99, TRUE);
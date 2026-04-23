CREATE TABLE customers (
                           id UUID PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           email VARCHAR(255) NOT NULL UNIQUE,
                           phone VARCHAR(50) NOT NULL,
                           street VARCHAR(255) NOT NULL,
                           city VARCHAR(100) NOT NULL,
                           province VARCHAR(100) NOT NULL,
                           postal_code VARCHAR(20) NOT NULL
);

INSERT INTO customers (id, name, email, phone, street, city, province, postal_code) VALUES
                                                                                        ('11111111-1111-1111-1111-111111111111', 'Alice Johnson', 'alice@example.com', '514-555-1001', '123 Maple St', 'Montreal', 'Quebec', 'H1A1A1'),
                                                                                        ('22222222-2222-2222-2222-222222222222', 'Brian Smith', 'brian@example.com', '514-555-1002', '456 Cedar Ave', 'Longueuil', 'Quebec', 'J4K2T3');
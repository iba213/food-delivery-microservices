CREATE TABLE orders (
                        id UUID PRIMARY KEY,
                        customer_id UUID NOT NULL,
                        restaurant_id UUID NOT NULL,
                        driver_id UUID,
                        status VARCHAR(20) NOT NULL,
                        total_price NUMERIC(10, 2) NOT NULL,
                        street VARCHAR(255) NOT NULL,
                        city VARCHAR(100) NOT NULL,
                        province VARCHAR(100) NOT NULL,
                        postal_code VARCHAR(20) NOT NULL,
                        payment_method VARCHAR(100) NOT NULL,
                        payment_last_four_digits VARCHAR(4) NOT NULL
);

CREATE TABLE order_items (
                             id UUID PRIMARY KEY,
                             order_id UUID NOT NULL,
                             menu_item_id UUID NOT NULL,
                             quantity INT NOT NULL,
                             price NUMERIC(10, 2) NOT NULL,
                             CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders (id)
);

INSERT INTO orders (
    id,
    customer_id,
    restaurant_id,
    driver_id,
    status,
    total_price,
    street,
    city,
    province,
    postal_code,
    payment_method,
    payment_last_four_digits
) VALUES (
             '66666666-6666-6666-6666-666666666666',
             '11111111-1111-1111-1111-111111111111',
             '33333333-3333-3333-3333-333333333333',
             '55555555-5555-5555-5555-555555555551',
             'ASSIGNED',
             36.48,
             '123 Maple St',
             'Montreal',
             'Quebec',
             'H1A1A1',
             'VISA',
             '4242'
         );

INSERT INTO order_items (id, order_id, menu_item_id, quantity, price) VALUES
                                                                          ('77777777-7777-7777-7777-777777777771', '66666666-6666-6666-6666-666666666666', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 1, 18.99),
                                                                          ('77777777-7777-7777-7777-777777777772', '66666666-6666-6666-6666-666666666666', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 1, 17.49);
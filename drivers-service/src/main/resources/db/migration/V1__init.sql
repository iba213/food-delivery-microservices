CREATE TABLE drivers (
                         id UUID PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         phone VARCHAR(50) NOT NULL,
                         status VARCHAR(20) NOT NULL
);

INSERT INTO drivers (id, name, phone, status) VALUES
                                                  ('55555555-5555-5555-5555-555555555551', 'Diane Courier', '514-555-2001', 'AVAILABLE'),
                                                  ('55555555-5555-5555-5555-555555555552', 'Ethan Rider', '514-555-2002', 'AVAILABLE'),
                                                  ('55555555-5555-5555-5555-555555555553', 'Farah Express', '514-555-2003', 'OFFLINE');
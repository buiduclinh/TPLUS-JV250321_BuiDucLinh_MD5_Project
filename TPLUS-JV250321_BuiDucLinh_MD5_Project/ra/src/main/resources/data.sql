INSERT INTO product (name, brand, price, stock, image, is_deleted) VALUES
                                                                       ('iPhone 14 Pro Max 128GB', 'Apple', 29990000, 20, 'iphone14promax.jpg', false),
                                                                       ('Samsung Galaxy S23 Ultra 256GB', 'Samsung', 26990000, 15, 's23ultra.jpg', false),
                                                                       ('Xiaomi 13T Pro 512GB', 'Xiaomi', 15990000, 25, 'xiaomi13tpro.jpg', false),
                                                                       ('Oppo Reno10 Pro+ 5G', 'Oppo', 16990000, 18, 'reno10pro.jpg', false),
                                                                       ('Vivo V27e 256GB', 'Vivo', 8990000, 30, 'vivov27e.jpg', false),
                                                                       ('iPhone 13 128GB', 'Apple', 17990000, 22, 'iphone13.jpg', false),
                                                                       ('Samsung Galaxy Z Flip4 128GB', 'Samsung', 19990000, 12, 'zflip4.jpg', false),
                                                                       ('Realme GT Neo 5', 'Realme', 10990000, 28, 'realmegtneo5.jpg', false),
                                                                       ('Asus ROG Phone 7', 'Asus', 23990000, 10, 'rogphone7.jpg', false),
                                                                       ('Nokia G50 128GB', 'Nokia', 4990000, 35, 'nokiag50.jpg', false);

INSERT INTO customer (name, phone, email, address, is_deleted) VALUES
                                                                  ('Nguyễn Văn A', '0901111111', 'vana@example.com', 'Hà Nội', false),
                                                                  ('Trần Thị B', '0902222222', 'thib@example.com', 'TP. HCM', false),
                                                                  ('Lê Văn C', '0903333333', 'vanc@example.com', 'Đà Nẵng', false),
                                                                  ('Phạm Thị D', '0904444444', 'thid@example.com', 'Cần Thơ', false),
                                                                  ('Hoàng Văn E', '0905555555', 'vane@example.com', 'Hải Phòng', false),
                                                                  ('Đỗ Thị F', '0906666666', 'thif@example.com', 'Nha Trang', false),
                                                                  ('Bùi Văn G', '0907777777', 'vang@example.com', 'Huế', false),
                                                                  ('Vũ Thị H', '0908888888', 'thih@example.com', 'Quảng Ninh', false),
                                                                  ('Ngô Văn I', '0909999999', 'vani@example.com', 'Bình Dương', false),
                                                                  ('Phan Thị J', '0910000000', 'thij@example.com', 'Đà Lạt', false);
INSERT INTO admin (username, password)
VALUES ('admin1', '123456');
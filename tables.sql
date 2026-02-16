USE shoppingdb;
CREATE TABLE products (
id INT PRIMARY KEY,
name VARCHAR(100),
price DOUBLE,
quantity INT);

CREATE TABLE orders (
order_id INT AUTO_INCREMENT PRIMARY KEY,
product_id INT,
quantity DOUBLE,
order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (product_id) REFERENCES products(id));
show tables;

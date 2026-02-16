CREATE TABLE products (
product_id INT AUTO_INCREMENT PRIMARY KEY,
product_name VARCHAR(100) NOT NULL,
price DECIMAL(10, 2) NOT NULL,
quantity INT NOT NULL
);

CREATE TABLE orders(
order_id INT AUTO_INCREMENT PRIMARY KEY,
product_id INT NOT NULL,
quantity INT NOT NULL,
FOREIGN KEY (product_id) REFERENCES products(product_id)
);

 
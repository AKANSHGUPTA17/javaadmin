package shopping_cart;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.security.sasl.Sasl;

public class ShoppingCart {
	private Connection conn;
	
	public ShoppingCart() {
		try {
			
			this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoppingcart", "root", "password");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addProduct(Product product) {
		String query = "INSERT INTOproducts (product_name, price, quantity) VALUES (?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, product.getProductName());
			stmt.setDouble(2, product.getPrice());
			stmt.setInt(3, product.getQuantity());
			stmt.executeUpdate();
			System.out.println("Product added successfully.");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Product> getProducts() {
		List<Product> products = new ArrayList<>();
		String query = "SELECT * FROM products";
		try (Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				int productId = rs.getInt("product_id");
				String productName = rs.getString("product_name");
				double price = rs.getDouble("price");
				int quantity = rs.getInt("quantity");
				products.add(new Product(productId, productName, price, quantity));
						}
			
		}catch (Sasl Exception e) {
			e.printStackTrace();
		}
		return products;
				
	}
	
	public void buyProduct(int productId, int quantity, Object stmt) {
		try {
			
			String checkStockQuery = "SELECT quantity FROM products WHERE product_id = ?";
			try (PreparedStatement smtm = conn.prepareStatement(checkStockQuery)) {
				((CallableStatement) stmt).setInt(1, productId);
				ResultSet rs = ((PreparedStatement) stmt).executeQuery();
				if (rs.next()) {
					int availableQuantity = rs.getInt("quantity");
					if (availableQuantity >= quantity) {
						
						String updateStockQuery = "UPDATE products SET quantity = quantity - ? WHARE product_id = ?";
						try (PreparedStatement updateStmt = conn.prepareStatement(updateStockQuery)) {
							updateStmt.setInt(1, quantity);
							updateStmt.setInt(2, productId);
							updateStmt.executeUpdate();						}
					}
					
					String insertOrderQuery = "INSERT INTO orders (product_id, quantity) VALUES (?, ?)";
					try (CallableStatement insertStmt = conn.prepareCall(insertOrderQuery)) {
						insertStmt.setInt(1, productId);
						insertStmt.setInt(2, quantity);
						insertStmt.executeUpdate();
					}
					
					System.out.println("Order placed successfully.");
				}else {
					System.out.println("not enough stock available.");
				}
			}
		}	
	} catch (Sasl Exception e) {
		e.printStackTrace();
	}
}

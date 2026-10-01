import java.sql.*;

public class LibraryApp {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/library";

        try (Connection con = DriverManager.getConnection(url, "root", "MyNewPass@2026")) {
            System.out.println("Connected to database");

            String sql = "SELECT * FROM books";
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {

                while (rs.next()) {
                    System.out.println(rs.getInt("book_id") + " | " + rs.getString("title")
                            + " | " + rs.getString("author") + " | " + rs.getDouble("price"));
                }
            }

            String sql2 = "INSERT INTO books (title, author, price) VALUES (?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql2)) {
                ps.setString(1, "Wings of Fire");
                ps.setString(2, "A. P. J. Abdul Kalam");
                ps.setDouble(3, 250.0);
                int rows = ps.executeUpdate();
                System.out.println(rows + " row inserted");
            }

            String sql3 = "UPDATE books SET price = ? WHERE book_id = ?";
            try (PreparedStatement ps = con.prepareStatement(sql3)) {
                ps.setDouble(1, 300.0);
                ps.setInt(2, 2);
                int rows = ps.executeUpdate();
                System.out.println(rows + " row updated");
            }

            String sql4 = "DELETE FROM books WHERE book_id = ?";
            try (PreparedStatement ps = con.prepareStatement(sql4)) {
                ps.setInt(1, 3);
                int rows = ps.executeUpdate();

                if (rows == 0) {
                    System.out.println("No book found.");
                } else {
                    System.out.println("Book deleted.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
package TestHelpers;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCheckout {

    public static boolean assertAuctionAddedToDatabase(Connection connection, String title, String description, BigDecimal price) {
        try {
            String checkTitle = null, descript = null;
            BigDecimal priceToCheck = new BigDecimal(0);
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("SELECT * FROM auctions WHERE title = '" + title + "'");
            while (resultSet.next()) {
                checkTitle = resultSet.getString("title");
                descript = resultSet.getString("description");
                priceToCheck = resultSet.getBigDecimal("price");
            }

            if (checkTitle.equals(title) && descript.equals(description) && priceToCheck.equals(price))
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

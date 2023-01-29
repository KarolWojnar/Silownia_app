import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseTest {
    @Test
    void sprawdzNazwisko(){
        Connection connection;
        try {
            connection = DriverManager.getConnection(ConnectorDbase.DB_URL, "root", "");
            String query = "Select * from silownia_dane where nazwisko = 'Król'";
            Statement stat = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet rs = stat.executeQuery(query);
            assertTrue(rs.next());

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Test
    void sprawdzEmail() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(ConnectorDbase.DB_URL, "root", "");
            String query = "Select * from silownia_dane where email = 'wojtek@wp.pl'";
            Statement stat = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet rs = stat.executeQuery(query);
            assertTrue(rs.next());

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
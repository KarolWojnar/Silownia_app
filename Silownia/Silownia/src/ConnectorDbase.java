import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConnectorDbase {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/silka";
    public static Connection connection;
    public static List<DataBase> listOfUsers = new ArrayList<DataBase>();
    public static String[] emailBase = new String[500];
    public static String[] passwordBase = new String[500];
    public static DataBase user;

    private static Statement statement;
    public void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL, "root", "");
            String query = "select * from silownia_dane";
            Statement stat = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet rs = stat.executeQuery(query);
            int i = 0, j = 0;
            while(rs.next())
            {
                Integer id = rs.getInt("ID");
                String imie = rs.getString("imie");
                String nazwisko = rs.getString("nazwisko");
                String numer = rs.getString("nr_telefonu");
                String email = rs.getString("email");
                String haslo = rs.getString("haslo");
                Date data = rs.getDate("data_karnet");
                user = new DataBase(id, imie, nazwisko, numer, email, haslo, data);
                emailBase[i++] = email;
                passwordBase[j++] = haslo;
                listOfUsers.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(DB_URL,"root", "");
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.println("Error while connect to database");
            e.printStackTrace();
        }
    }
}



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Piotr Lipski
 */
public class manipulatedatabase {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("Proba polaczenia z baza..");
        Class.forName("com.mysql.jdbc.Driver");
        Scanner in = new Scanner(System.in);
        Connection conn = connectionToDatabase();
        int returnedCodeWhatToDo = 1;
        while (returnedCodeWhatToDo != 5) {
            returnedCodeWhatToDo = chooseWhatToDo();
            switch (returnedCodeWhatToDo) {
                case 1:
                    System.out.println("\n Add To Database \n");
                    System.out.println("Podaj imie:");
                    String imie = in.nextLine();
                    System.out.println("Podaj nazwisko:");
                    String nazwisko = in.nextLine();
                    System.out.println("Podaj email:");
                    String email = in.nextLine();
                    addToDatabase(conn, imie, nazwisko, email);
                    break;
                case 2:
                    System.out.println("\n deleteFromDatabase() \n");
                    System.out.println("Podaj id usera do usuniecia");
                    String iduser = in.nextLine();
                    deleteFromDatabase(conn, iduser);
                    break;
                case 3:
                    System.out.println("\n Edycja  \n");
                    System.out.println("Podaj id usera do edycji");
                    String iduserDelete = in.nextLine();
                    System.out.println("Podaj imie:");
                    String imieNew = in.nextLine();
                    System.out.println("Podaj nazwisko:");
                    String nazwiskoNew = in.nextLine();
                    System.out.println("Podaj email:");
                    String emailNew = in.nextLine();
                    editDatabase(conn,iduserDelete,imieNew,nazwiskoNew,emailNew);
                    
                    break;
                case 4:
                    System.out.println("\n Show Data from table \n");
                    showDatabase(conn);
                    break;
                case 5:
                    System.out.println("Wyjscie");
                    conn.close();
                    break;
            }
        }

    }

    public static Connection connectionToDatabase() {
        String dbURL = "jdbc:mysql://10.0.10.3:3306/plipski";
        String username = "plipski";
        String password = "plipski";

        try {

            System.out.println("Proba polaczenia z baza..");
            Connection conn = DriverManager.getConnection(dbURL, username, password);

            if (conn != null) {
                System.out.println("Connected");

            }
            return conn;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public static int chooseWhatToDo() {
        System.out.println("\n Dziala twoj manipulator bazy danych!");
        System.out.println("1. Dodaj do bazy \n 2. Usun z bazy \n 3. edytuj \n 4. pokaz \n 5. WYJSCIE");
        System.out.println("Wpisz liczbe 1,2,3,4 lub 5:");
        Scanner load = new Scanner(System.in);
        int whatToDo = load.nextInt();
        return whatToDo;
    }

    public static void showDatabase(Connection conn) throws SQLException {

        String sql = "SELECT * FROM users";

        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);

        int count = 0;
        String outputString = "";
        while (result.next()) {
            int id = result.getInt(1);
            String firstname = result.getString(2);
            String lastname = result.getString(3);
            String email = result.getString("email");

            String output = "User #%d: %s - %s - %s - %s";
            System.out.println(String.format(output, ++count, id, firstname, lastname, email));

        }

    }

    public static String addToDatabase(Connection conn, String imie, String nazwisko, String email) throws SQLException {
        String sql = "INSERT INTO users (firstname, lastname, email) VALUES (?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, imie);
        statement.setString(2, nazwisko);
        statement.setString(3, email);

        int rowsInserted = statement.executeUpdate();

        if (rowsInserted > 0) {
            System.out.println("A new user was inserted successfully!");
        }
        return "Dodano do bazy";
    }

    public static String deleteFromDatabase(Connection conn, String iduser) throws SQLException {
        String sql = "DELETE FROM users WHERE id=?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, iduser);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A user was deleted successfully!");
        }
        return "Usuwanie z bazy";
    }

    public static String editDatabase(Connection conn, String id, String imie, String nazwisko, String email) throws SQLException {
        String sql = "UPDATE users SET firstname=?, lastname=?, email=? WHERE id=?";
 
PreparedStatement statement = conn.prepareStatement(sql);
statement.setString(1, imie);
statement.setString(2, nazwisko);
statement.setString(3, email);
statement.setString(4, id);
 
int rowsUpdated = statement.executeUpdate();
if (rowsUpdated > 0) {
    System.out.println("An existing user was updated successfully!");
}
        return "Edytowanie bazy";
    }
}

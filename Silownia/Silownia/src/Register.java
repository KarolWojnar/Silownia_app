import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Register extends JDialog {
    private JPanel p1 = new JPanel();
    private JPanel mainPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private final JPanel p2 = new JPanel();
    private final JPanel p3 = new JPanel();
    private final JPanel p4 = new JPanel();
    private final JPanel p5 = new JPanel();
    private final JPanel p6 = new JPanel();
    private final JPanel p7 = new JPanel();
    private final JPanel p8 = new JPanel();
    private final JPanel p9 = new JPanel();
    private final JPanel p10 = new JPanel();
    private final JPanel p11 = new JPanel();
    private final JPanel p12 = new JPanel();
    private JTextField nameField = new JTextField(20);
    private JTextField secondNameField = new JTextField(20);
    private JTextField numberField = new JTextField(20);
    private JTextField emailField = new JTextField(20);
    private JTextField passwdField = new JTextField(20);
    private JTextField repPasswdField = new JTextField(20);
    private JLabel name = new JLabel("Podaj Imie");
    private JLabel secodnName = new JLabel("Podaj Nazwisko");
    private JLabel number = new JLabel("Podaj numer");
    private JLabel email = new JLabel("Podaj e-mail");
    private JLabel paswd = new JLabel("Podaj hasło");
    private JLabel repPaswd = new JLabel("Powtórz hasło");
    private JButton register = new JButton("Zarejestruj się");
    private boolean isNotExisting = true;
    Register(JFrame parent)
    {
        super(parent, true);
        this.setTitle("Rejestracja");
        int height = parent.getHeight();
        int width = parent.getWidth();
        int x = parent.getX();
        int y = parent.getY();
        this.setBounds(x + (width / 4), y, width / 2, height);
        p1.add(name);
        p2.add(nameField);
        p3.add(secodnName);
        p4.add(secondNameField);
        p5.add(email);
        p6.add(emailField);
        p7.add(number);
        p8.add(numberField);
        p9.add(paswd);
        p10.add(passwdField);
        p11.add(repPaswd);
        p12.add(repPasswdField);
        mainPanel.add(p1);
        mainPanel.add(p2);
        mainPanel.add(p3);
        mainPanel.add(p4);
        mainPanel.add(p5);
        mainPanel.add(p6);
        mainPanel.add(p7);
        mainPanel.add(p8);
        mainPanel.add(p9);
        mainPanel.add(p10);
        mainPanel.add(p11);
        mainPanel.add(p12);
        buttonPanel.add(register);
        mainPanel.setLayout(new GridLayout(6,2));
        this.setDefaultCloseOperation(2);
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newUser();
                if(isNotExisting)
                {
                    dispose();
                }
            }
        });
    }
    void newUser()
    {
        if(!(nameField.getText().isEmpty() || secondNameField.getText().isEmpty() || numberField.getText().isEmpty() || emailField.getText().isEmpty()
                || passwdField.getText().isEmpty() || repPasswdField.getText().isEmpty()))
        {
            String name = nameField.getText();
            String secondName = secondNameField.getText();
            String number = numberField.getText();
            String mail = emailField.getText();
            String password = passwdField.getText();
            String passwordConfirm = repPasswdField.getText();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            try {
                Statement stat = ConnectorDbase.connection.createStatement(
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE
                );
                String lastId = "SELECT MAX(id) FROM silownia_dane";
                ResultSet resultLastId = stat.executeQuery(lastId);
                int nextInt = 0;
                while (resultLastId.next()) {
                    Integer id = resultLastId.getInt("MAX(id)");
                    nextInt = id + 1;
                }
                String query = "INSERT INTO `silownia_dane` (`id`,`imie`, `nazwisko`, `nr_telefonu`, `email`, `haslo`, `data_karnet`) VALUES" +
                        " ( '" + nextInt + "', '" + name + "', '" + secondName + "', '" + number + "', '" + mail + "', '" + password + "', '" + dateFormat.format(date) + "');";
                if(password.equals(passwordConfirm))
                {
                    try
                    {
                        if(EditingUsers.isValidEmailAddress(mail)) {
                            stat.executeUpdate(query);
                            isNotExisting = true;
                        }
                        else {
                            JOptionPane.showMessageDialog(this, "Podaj parawdziwy email!", "Nie można wykonać polecenia", 2);
                            isNotExisting = false;
                        }
                    }catch(MysqlDataTruncation e)
                    {
                        JOptionPane.showMessageDialog(this, "Podaj parawdziwy numer!", "Nie można wykonać polecenia", 2);
                        isNotExisting = false;
                    }catch (SQLIntegrityConstraintViolationException e)
                    {
                        JOptionPane.showMessageDialog(this, "Podany e-mail lub numer już istnieje w bazie", "Nie można wykonać polecenia", 2);
                        isNotExisting = false;
                    }
                }else {
                    JOptionPane.showMessageDialog(this, "Hasła się różnią", "Nie można wykonać polecenia", 2);
                    isNotExisting = false;
                }
                String query2 = "select * from silownia_dane";
                ResultSet rs = stat.executeQuery(query2);
                for (int x = 0; x < ConnectorDbase.emailBase.length; x++) {
                    ConnectorDbase.emailBase[x] = null;
                    ConnectorDbase.passwordBase[x] = null;
                }
                int i = 0, j = 0, z = 0;
                ConnectorDbase.listOfUsers.clear();
                while (rs.next()) {
                    Integer id = rs.getInt("ID");
                    String imie = rs.getString("imie");
                    String nazwisko = rs.getString("nazwisko");
                    String numer = rs.getString("nr_telefonu");
                    String email = rs.getString("email");
                    String haslo = rs.getString("haslo");
                    Date data = rs.getDate("data_karnet");
                    ConnectorDbase.user = new DataBase(id, imie, nazwisko, numer, email, haslo, data);
                    ConnectorDbase.emailBase[i++] = email;
                    ConnectorDbase.passwordBase[j++] = haslo;
                    ConnectorDbase.listOfUsers.add(z++, ConnectorDbase.user);
                }
                Main.users.clear();
                Main.lista.clearSelection();
                Main.users.addAll(ConnectorDbase.listOfUsers);
                Main.lista.setModel(Main.users);
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Wypełnij Wszytkie pola!", "Nie można wykonać polecenia", 2);
            isNotExisting = false;
        }

    }
}

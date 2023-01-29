import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Date;

class EditingUsers extends JDialog{
    public JPanel p1 = new JPanel();
    public JPanel mainPanel = new JPanel();
    public JPanel buttonPanel = new JPanel();
    public JPanel p2 = new JPanel();
    public JPanel p3 = new JPanel();
    public JPanel p4 = new JPanel();
    public JPanel p5 = new JPanel();
    public JPanel p6 = new JPanel();
    public JPanel p7 = new JPanel();
    public JPanel p8 = new JPanel();
    public JPanel p9 = new JPanel();
    public JPanel p10 = new JPanel();
    public static JTextField nameField = new JTextField(20);
    public static JTextField secondNameField = new JTextField(20);
    public static JTextField numberField = new JTextField(20);
    public static JTextField emailField = new JTextField(20);
    public static JTextField passwdField = new JTextField(20);
    private JLabel name = new JLabel("Zmien Imie");
    private JLabel secodnName = new JLabel("Zmien Nazwisko");
    private JLabel number = new JLabel("Zmien numer");
    private JLabel email = new JLabel("Zmien e-mail");
    private JLabel paswd = new JLabel("Zmien hasło");
    private JLabel date = new JLabel("Zmien datę ważności");
    private JButton change = new JButton("Zmien");
    private JButton back = new JButton("Anuluj");
    private int index;
    private boolean canUpdate = false;
    EditingUsers(JFrame parent, int ind)
    {
        super(parent, true);
        this.index = ind;
        this.setTitle("Edycja");
        int height = parent.getHeight();
        int width = parent.getWidth();
        int x = parent.getX();
        int y = parent.getY();
        this.setBounds(x + (width / 4), y, width / 2, height);
        this.setDefaultCloseOperation(2);
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
        buttonPanel.add(change);
        buttonPanel.add(back);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        mainPanel.setLayout(new GridLayout(6,2));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        editExisted();
    }
    void editExisted()
    {
        try{
            ConnectorDbase connectorDbase = new ConnectorDbase();
            connectorDbase.connect();
            String qr = "Select * from silownia_dane where id = '" + (index + 1) + "'";
            Statement stat = ConnectorDbase.connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet rs = stat.executeQuery(qr);
            if(rs.next())
            {
                String imie = rs.getString("imie");
                String nazwisko = rs.getString("nazwisko");
                String numer = rs.getString("nr_telefonu");
                String email = rs.getString("email");
                String haslo = rs.getString("haslo");
                EditingUsers.nameField.setText(imie);
                EditingUsers.secondNameField.setText(nazwisko);
                EditingUsers.numberField.setText(numer);
                EditingUsers.emailField.setText(email);
                EditingUsers.passwdField.setText(haslo);
            }
        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveNewData();
                if(canUpdate == true) {
                    dispose();
                    UserPanel.takeUser();
                }
            }
        });
    }
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    void saveNewData()
    {
        String newName = nameField.getText();
        String newSecondName = secondNameField.getText();
        String newNumer = numberField.getText();
        String newEmail = emailField.getText();
        String newpassword = passwdField.getText();

        try {
            String editedUser = "UPDATE `silownia_dane` SET `imie` = '" + newName +
                    "', `nazwisko` = '" + newSecondName + "', `nr_telefonu` = '" + newNumer + "', " +
                    "`email` = '" + newEmail + "', `haslo` = '" + newpassword + "' WHERE `silownia_dane`.`id` =" + (index + 1);
            ConnectorDbase connectorDbase = new ConnectorDbase();
            connectorDbase.connect();
            Statement stat = ConnectorDbase.connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );
            try
            {
                if(isValidEmailAddress(newEmail)) {
                    stat.executeUpdate(editedUser);
                    canUpdate = true;
                }
                else {
                    JOptionPane.showMessageDialog(this, "Podaj parawdziwy email!", "Nie można wykonać polecenia", 2);
                    canUpdate = false;
                }
            }catch(MysqlDataTruncation e)
            {
                JOptionPane.showMessageDialog(this, "Podaj parawdziwy numer!", "Nie można wykonać polecenia", 2);
                canUpdate = false;
            }catch (SQLIntegrityConstraintViolationException e)
            {
                JOptionPane.showMessageDialog(this, "Podany e-mail lub numer już istnieje w bazie", "Nie można wykonać polecenia", 2);
                canUpdate = false;
            }
            String query = "select * from silownia_dane";
            ResultSet rs = stat.executeQuery(query);
            for(int x = 0; x < ConnectorDbase.emailBase.length; x++)
            {
                ConnectorDbase.emailBase[x] = null;
                ConnectorDbase.passwordBase[x] = null;
            }
            int i = 0, j = 0, z = 0;
            ConnectorDbase.listOfUsers.clear();
            while(rs.next())
            {
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
            JOptionPane.showMessageDialog(this, "Podany numer jest niepoprawny", "Nie można wykonać polecenia", 2);
            canUpdate = false;
        }
    }
}

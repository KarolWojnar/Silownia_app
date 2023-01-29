import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import static javax.swing.JOptionPane.YES_NO_OPTION;

public class Main extends JFrame {
    private final int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;
    public static JTabbedPane pannes = new JTabbedPane();
    private final int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;
    private final JButton login = new JButton("Zaloguj");
    public static final JButton logout = new JButton("Wyloguj");
    static final JButton logoutUser = new JButton("Wyloguj");
    private final JButton register = new JButton("Zarejestruj się");
    private final JLabel bilboardGym = new JLabel("Siłownia GymPower");
    static JPanel mainPanel = new JPanel();
    private final JPanel prizePanel = new JPanel();
    public static JPanel adminPanel = new JPanel();
    public static JPanel userPanel = new JPanel();
    private final JPanel panelBilboard = new JPanel();
    public static final JList lista = new JList();
    private final JScrollPane scroll = new JScrollPane(lista);
    public final JFrame mainFrame = this;
    private final float promoStudent = 0.5f;
    private final float prizeMonthly = 80;
    private JLabel mLabel;
    private JLabel kLabel;
    private JLabel pLabel;
    private JLabel rLabel;
    private final ButtonGroup group = new ButtonGroup();
    public static int ind;
    private final JPanel info_Prize = new JPanel();
    public static final DefaultListModel users = new DefaultListModel();
    final JPopupMenu menuAdmin = new JPopupMenu();

    public static void main(String[] args) {
        new Main().setVisible(true);

    }
    Main()
    {
        ConnectorDbase connectorDbase = new ConnectorDbase();
        connectorDbase.connect();
        initComps();
        buttonInit();
        setPrizePanel();
    }

    void buttonInit()
    {
        login.addActionListener(e -> new Login(mainFrame).setVisible(true));
        register.addActionListener(e -> new Register(mainFrame).setVisible(true));
        logout.addActionListener(e -> {
            Main.pannes.setEnabledAt(1, true);
            Main.pannes.removeTabAt(2);
            Main.pannes.setSelectedIndex(1);
        });
        logoutUser.addActionListener(e -> {
            UserPanel.dateAdd.setEnabled(true);
            Main.pannes.setEnabledAt(1, true);
            Main.pannes.removeTabAt(2);
            Main.pannes.setSelectedIndex(1);
        });
    }
    void setPrizePanel()
    {
        JLabel allInfo = new JLabel("Ceny karnetów:");
        JPanel leftPrize = new JPanel();
        JPanel rightPrize = new JPanel();
        allInfo.setFont(new Font("Verdana", Font.BOLD, 50));
        info_Prize.add(new JLabel("Czy jesteś studentem/uczniem?"));
        setPrize("Tak", promoStudent);
        setPrize("Nie", 1);
        info_Prize.setLayout(new GridLayout(3,1));
        prizePanel.setLayout(new GridLayout(1,2));
        prizePanel.add(leftPrize);
        prizePanel.add(rightPrize);
        leftPrize.add(allInfo);
        leftPrize.add(info_Prize);
        rightPrize.setLayout(new GridLayout(5,1));
        mLabel = (JLabel) rightPrize.add(new JLabel());
        mLabel.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 20));
        kLabel = (JLabel) rightPrize.add(new JLabel());
        kLabel.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 20));
        pLabel = (JLabel) rightPrize.add(new JLabel());
        pLabel.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 20));
        rLabel = (JLabel) rightPrize.add(new JLabel());
        rLabel.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 20));
    }
    void setPrize(String name, final float x)
    {
        JRadioButton b = new JRadioButton(name);
        b.addActionListener(e -> {
            mLabel.setText("Miesięczny - " + prizeMonthly * x + "zł");
            kLabel.setText("Kwartalny - " + prizeMonthly * 2.9 * x + "zł");
            pLabel.setText("Półroczny - " + prizeMonthly * 5.5 * x + "zł");
            rLabel.setText("Roczny - " + prizeMonthly * 10 * x + "zł");
        });
        group.add(b);
        info_Prize.add(b);
        userPanel.setLayout(new GridLayout(2,1));
    }
    void initComps()
    {
        this.setTitle("Siłownia");
        this.setIconImage(new ImageIcon("imageGym.png").getImage());
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setBounds(width / 2, height / 2, width + 200, height + 200);
        panelBilboard.add(bilboardGym);
        bilboardGym.setFont(new Font("Verdana", Font.BOLD, 50));
        bilboardGym.setForeground(Color.orange.darker());
        panelBilboard.setBackground(Color.orange);
        mainPanel.add(login);
        mainPanel.add(register);
        pannes.addTab("Cennik", prizePanel);
        pannes.addTab("Zaloguj!", mainPanel);
        UserPanel.setUserPanel();
        setAdminPanel();
        setSwap();
        this.getContentPane().add(panelBilboard, BorderLayout.NORTH);
        this.getContentPane().add(pannes);
    }
    public void createList()
    {
        users.clear();
        lista.clearSelection();
        users.addAll(ConnectorDbase.listOfUsers);
        lista.setModel(users);
    }
    void setAdminPanel()
    {
        createList();
        adminPanel.add(scroll);
        scroll.setPreferredSize(new Dimension(this.width -50,this.height -200));
        adminPanel.add(logout);
        lista.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    menuAdmin.show(e.getComponent(), e.getX(), e.getY());
                    ind = lista.getSelectedIndex();
                }
            }
        });
        menuAdmin.add(new DeleteUsers("Usuń z listy"));
        menuAdmin.addSeparator();
        menuAdmin.add(new EditUser("Edytuj"));
    }
    private class DeleteUsers extends AbstractAction
    {
        DeleteUsers(String name)
        {
            this.putValue(Action.NAME, name);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(rootPane, "Czy na pewno chcesz usunąć tego użytkownika?", "Usuwanie", YES_NO_OPTION);
            if(confirm == 0)
            {
                deleteUserFromList();
            }
        }
    }
    void deleteUserFromList()
    {
        ConnectorDbase.listOfUsers.remove(sortInt());
        createList();
    }
    public static int sortInt()
    {
        try {
            String polecenie = "DELETE FROM silownia_dane WHERE `silownia_dane`.`ID` = '" + (ind + 1) + "'";
            String deleteId = "ALTER TABLE silownia_dane DROP id";
            String addId = "ALTER TABLE silownia_dane ADD  id INT( 20 ) NOT NULL AUTO_INCREMENT FIRST ,ADD PRIMARY KEY (id)";
            Statement stat = ConnectorDbase.connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );
            stat.executeUpdate(polecenie);
            stat.executeUpdate(deleteId);
            stat.executeUpdate(addId);
            String query = "select * from silownia_dane";
            ResultSet rs = stat.executeQuery(query);
            for(int x = 0; x < ConnectorDbase.emailBase.length; x++)
            {
                ConnectorDbase.emailBase[x] = null;
                ConnectorDbase.passwordBase[x] = null;
            }
            int i = 0, j = 0, z = 0;
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
                ConnectorDbase.listOfUsers.set(z++, ConnectorDbase.user);
            }
            return z;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void setSwap()
    {
        UserPanel.swap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditingUsers(mainFrame, Login.index).setVisible(true);
            }
        });
    }
    private class EditUser extends AbstractAction
    {
        EditUser(String name)
        {
            this.putValue(Action.NAME, name);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            new EditingUsers(mainFrame, ind).setVisible(true);
        }
    }
}
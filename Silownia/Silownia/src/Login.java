import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JDialog {
    private JPasswordField passwField = new JPasswordField(20);
    private JPanel p1 = new JPanel();
    private JPanel mainPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel p2 = new JPanel();
    private JPanel p3 = new JPanel();
    private JPanel p4 = new JPanel();
    private JTextField loginField = new JTextField(20);
    private JLabel loginLab = new JLabel("Podaj e-mail");
    private JLabel passwdLab = new JLabel("Hasło:");
    private JButton login = new JButton("Zaloguj!");
    public static int index;

    Login(JFrame parent)
    {
        super(parent, true);
        this.setTitle("Logowanie");
        int height = parent.getHeight();
        int width = parent.getWidth();
        int x = parent.getX();
        int y = parent.getY();
        this.setBounds(x + (width / 4), y + (height / 4), width / 2, height / 2);
        p1.add(loginLab);
        p2.add(loginField);
        p3.add(passwdLab);
        p4.add(passwField);
        mainPanel.add(p1);
        mainPanel.add(p2);
        mainPanel.add(p3);
        mainPanel.add(p4);
        buttonPanel.add(login);
        mainPanel.setLayout(new GridLayout(5,1));
        this.setDefaultCloseOperation(2);
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        buttonActions();
    }
    void buttonActions()
    {
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nazwa = loginField.getText();
                String haslo = new String(passwField.getPassword());
                int succes = 0;
                if(nazwa.length() > 0 && haslo.length() > 0)
                {
                    for(int i = 0; i < ConnectorDbase.listOfUsers.size(); i++)
                    {
                        if(nazwa.equals("admin"))
                        {
                            if(haslo.equals("admin"))
                            {
                                Main.pannes.addTab("Panel admina", Main.adminPanel);
                                Main.pannes.setEnabledAt(1, false);
                                Main.pannes.setSelectedIndex(2);
                                succes = 1;
                            }
                        }
                        else if(nazwa.equals(ConnectorDbase.emailBase[i]))
                        {
                            if(haslo.equals(ConnectorDbase.passwordBase[i]))
                            {
                                Main.pannes.setEnabledAt(1, false);
                                Main.pannes.addTab("Panel użytkownika", Main.userPanel);
                                index = i;
                                Main.pannes.setSelectedIndex(2);
                                UserPanel.takeUser();
                                succes = 1;
                            }
                        }
                    }
                    if(succes == 1)
                    {
                        dispose();
                    }
                    else JOptionPane.showMessageDialog(rootPane, "Błędne dane");
                }
                else JOptionPane.showMessageDialog(rootPane, "Podaj dane!!!!!");
            }

        });
    }
}

import com.mysql.cj.xdevapi.JsonNumber;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class UserPanel extends Main{
    private static JLabel hello = new JLabel("Witaj!");
    private static JLabel nameJ = new JLabel();
    private static JLabel secondNameJ = new JLabel();
    private static JLabel numberJ = new JLabel();
    private static JLabel emailJ = new JLabel();
    private static JLabel passwordJ = new JLabel();
    private static JLabel dateJ = new JLabel();
    private static JLabel howManyMonths = new JLabel("O ile chcesz przedłużyć karnet?");
    public static int index;
    public  static JButton swap = new JButton("Zmien Dane");
    private static JSlider suwak = new JSlider(SwingConstants.HORIZONTAL, 12,0);
    private static boolean once = true;
    private static int plusHowMany = 0;
    public static LocalDate finalNewData;
    public  static JButton dateAdd = new JButton("Kup/Przedluż karnet");
    static void setUserPanel()
    {
        JPanel panelUser = new JPanel();
        JPanel panelUser2 = new JPanel();
        JPanel addButtons = new JPanel();
        JPanel panelUserAllPanels = new JPanel();
        JPanel textMonth = new JPanel();
        JPanel addMonth = new JPanel();
        panelUser.setLayout(new GridLayout(7,1));
        panelUser.add(hello);
        hello.setFont(new Font("Verdana", 2, 20));
        panelUser.add(nameJ);
        nameJ.setFont(new Font("Verdana", 2, 20));
        panelUser.add(secondNameJ);
        secondNameJ.setFont(new Font("Verdana", 2, 20));
        panelUser.add(numberJ);
        numberJ.setFont(new Font("Verdana", 2, 20));
        panelUser.add(emailJ);
        emailJ.setFont(new Font("Verdana", 2, 20));
        panelUser.add(passwordJ);
        passwordJ.setFont(new Font("Verdana", 2, 20));
        panelUser.add(dateJ);
        dateJ.setFont(new Font("Verdana", 2, 20));
        suwak.setPaintTicks(true);
        suwak.setPaintLabels(true);
        suwak.setPaintTrack(true);
        suwak.setMajorTickSpacing(1);
        textMonth.setLayout(new GridLayout(1,2));
        addMonth.setLayout(new GridLayout(2,1));
        addButtons.setLayout(new GridLayout(1,2));
        panelUserAllPanels.setLayout(new GridLayout(2,1));
        textMonth.add(howManyMonths);
        textMonth.add(suwak);
        addMonth.add(textMonth);
        addMonth.add(dateAdd);
        addButtons.add(swap);
        addButtons.add(logoutUser);
        panelUserAllPanels.add(addMonth);
        panelUserAllPanels.add(addButtons);
        panelUser2.add(panelUserAllPanels);
        dajWartoscSuwaka();
        refresh();
        Main.userPanel.add(panelUser, BorderLayout.CENTER);
        Main.userPanel.add(panelUser2, BorderLayout.SOUTH);
    }
    private static void dajWartoscSuwaka()
    {
        suwak.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                plusHowMany = ((JSlider) e.getSource()).getValue();
            }
        });
    }
    static void takeUser()
    {
        LocalDate newData = LocalDate.now();
        index = Login.index + 1;
        try {
            String query = "SELECT * FROM `silownia_dane` WHERE id = " + index;
            Statement stat = ConnectorDbase.connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet rs = stat.executeQuery(query);
            while(rs.next())
            {
                LocalDate dataUser = rs.getDate("data_karnet").toLocalDate();
                if(dataUser.compareTo(newData) > 0) {
                    dateJ.setText("Ważność Karnetu: \t" + rs.getDate("data_karnet").toString());
                    newData = rs.getDate("data_karnet").toLocalDate();
                    dateJ.setForeground(Color.black);
                }
                else {
                    dateJ.setText("Karnet Wygasł");
                    dateJ.setForeground(Color.red);
                }
                nameJ.setText("Imie: \t" + rs.getString("imie"));
                secondNameJ.setText("Nazwisko: \t" + rs.getString("nazwisko"));
                numberJ.setText("Numer telefonu: \t" + rs.getString("nr_telefonu"));
                emailJ.setText("E-mail: \t" + rs.getString("email"));
                passwordJ.setText("Twoje Hasło: \t" + rs.getString("haslo"));
            }
            finalNewData= newData;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    static void refresh()
    {
        dateAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LocalDate newData2 = finalNewData.plusMonths(plusHowMany);
                    String addCarnet = "UPDATE `silownia_dane` SET `data_karnet` = '" + newData2 + "' WHERE `silownia_dane`.`id` =" + index;
                    System.out.println(addCarnet);
                    Statement stat = ConnectorDbase.connection.createStatement(
                            ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE
                    );
                    stat.executeUpdate(addCarnet);
                    dateAdd.setEnabled(false);
                    String query2 = "SELECT * FROM `silownia_dane` WHERE id = " + index;
                    Statement stat2 = ConnectorDbase.connection.createStatement(
                            ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE
                    );
                    ResultSet rs2 = stat2.executeQuery(query2);
                    while(rs2.next())
                    {
                        nameJ.setText("Imie: \t" + rs2.getString("imie"));
                        secondNameJ.setText("Nazwisko: \t" + rs2.getString("nazwisko"));
                        numberJ.setText("Numer telefonu: \t" + rs2.getString("nr_telefonu"));
                        emailJ.setText("E-mail: \t" + rs2.getString("email"));
                        passwordJ.setText("Twoje Hasło: \t" + rs2.getString("haslo"));
                        dateJ.setText("Ważność Karnetu: \t" + rs2.getDate("data_karnet").toString());
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}

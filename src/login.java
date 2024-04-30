import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public login() {
        setTitle("Login Page");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 5, 5);

        ImageIcon imageIcon = new ImageIcon(getImageFromUrl("https://img.freepik.com/free-psd/view-3d-supply-chain-representation-illustration_23-2150766713.jpg?t=st=1710959794~exp=1710963394~hmac=b69f335744f665a53db85ae67fcea2dad2ba265799d228e4a285503794ead98f&w=740").getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(imageIcon);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(imageLabel, gbc);

        JLabel titleLabel = new JLabel("Airplane Agency");
        titleLabel.setForeground(Color.RED);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(4, 8, 5, 5);
        add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(Color.RED);
        loginButton.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(loginButton, gbc);

        JLabel registerLabel = new JLabel("Don't have an account? Register here");
        registerLabel.setForeground(Color.BLUE); // Set text color to blue
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new Register().setVisible(true);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        add(registerLabel, gbc);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());


            try {
                Connection connection = MyConnection.getConnection("jdbc:mysql://127.0.0.1/AirPlanProject", "root", "");
                String query = "SELECT * FROM Auth WHERE username = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Login Successful");

                    new ReservationPage(username).setVisible(true);
                    dispose();
                } else if (username.equals("admin") && password.equals("admin")) {

                    dispose();
                    new Admin().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Login Failed");
            }
        }
    }

    private Image getImageFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            return new ImageIcon(url).getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(login::new);
    }
}

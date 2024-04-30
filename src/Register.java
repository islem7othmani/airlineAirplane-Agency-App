import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField countryField;
    private JComboBox<String> genderComboBox;
    private JButton registerButton;

    public Register() {
        setTitle("Registration Page");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 5, 5);

        // Load image from URL and resize
        ImageIcon imageIcon = new ImageIcon(getImageFromUrl("https://img.freepik.com/free-psd/view-3d-supply-chain-representation-illustration_23-2150766713.jpg?t=st=1710959794~exp=1710963394~hmac=b69f335744f665a53db85ae67fcea2dad2ba265799d228e4a285503794ead98f&w=740").getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(imageIcon);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(imageLabel, gbc);

        JLabel titleLabel = new JLabel("Airplane Agency");
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
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

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(nameLabel, gbc);

        nameField = new JTextField(15);
        gbc.gridx = 1;
        add(nameField, gbc);

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(phoneLabel, gbc);

        phoneField = new JTextField(15);
        gbc.gridx = 1;
        add(phoneField, gbc);

        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(countryLabel, gbc);

        countryField = new JTextField(15);
        gbc.gridx = 1;
        add(countryField, gbc);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(genderLabel, gbc);

        String[] genders = {"Male", "Female"};
        genderComboBox = new JComboBox<>(genders);
        gbc.gridx = 1;
        add(genderComboBox, gbc);


        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(Color.RED);
        registerButton.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(registerButton, gbc);

        JLabel loginLabel = new JLabel("Already have an account? Login here");
        loginLabel.setForeground(Color.RED);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new login().setVisible(true);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        add(loginLabel, gbc);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String name = nameField.getText();
            String phone = phoneField.getText();
            String country = countryField.getText();
            String gender = (String) genderComboBox.getSelectedItem();

            try {

                Connection connection = MyConnection.getConnection("jdbc:mysql://127.0.0.1/AirPlanProject", "root", "");

                String query = "INSERT INTO Auth (username, password, name, phone, country, gender) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, name);
                preparedStatement.setString(4, phone);
                preparedStatement.setString(5, country);
                preparedStatement.setString(6, gender);


                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {

                    dispose();
                    new ReservationPage(username).setVisible(true);
                    registrationPageWelcome(name);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Registration Failed");
            }
        }
    }

    private void registrationPageWelcome(String name) {
        JOptionPane.showMessageDialog(this, "Welcome " + name, "Registration Successful", JOptionPane.INFORMATION_MESSAGE);

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
        SwingUtilities.invokeLater(Register::new);
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ReservationPage extends JFrame implements ActionListener {
    private JPanel reservationFormPanel;
    private JPanel tablePanel, tableofflights;
    private JRadioButton firstClassRadio, economyRadio;
    private JTextField idField, nameField, timeField, destinationField, countryField, phoneField, numOfPlacesField;
    private JButton reserveButton;
    private String username;

    public String getUsername() {
        return username;
    }




    public ReservationPage(String username) {


        this.username = username;
        setTitle("Airplane Reservation");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(Color.RED);
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));

        JButton newReservationButton = new JButton("New Reservation");
        JButton myReservationsButton = new JButton("My Reservations");
        JButton flightsButton = new JButton("Flights");

        newReservationButton.setForeground(Color.WHITE);
        newReservationButton.setBackground(Color.RED);
        newReservationButton.setBorderPainted(false);
        myReservationsButton.setForeground(Color.WHITE);
        myReservationsButton.setBackground(Color.RED);
        myReservationsButton.setBorderPainted(false);
        flightsButton.setForeground(Color.WHITE);
        flightsButton.setBackground(Color.RED);
        flightsButton.setBorderPainted(false);

        newReservationButton.addActionListener(this);
        myReservationsButton.addActionListener(this);
        flightsButton.addActionListener(this);

        sidebarPanel.add(newReservationButton);
        sidebarPanel.add(myReservationsButton);
        sidebarPanel.add(flightsButton);

        add(sidebarPanel, BorderLayout.WEST);

        reservationFormPanel = createReservationFormPanel();

        tablePanel = createTablePanel(username);

        tableofflights = createTableFlights();

        add(reservationFormPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createReservationFormPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel reservationFormPanel = new JPanel(new BorderLayout());

        JPanel formFieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formFieldsPanel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formFieldsPanel.add(nameField, gbc);

        JLabel idLabel = new JLabel("Id:");
        idLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formFieldsPanel.add(idLabel, gbc);

        idField = new JTextField(20);
        gbc.gridx = 1;
        formFieldsPanel.add(idField, gbc);

        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        formFieldsPanel.add(timeLabel, gbc);

        timeField = new JTextField(10);
        gbc.gridx = 1;
        formFieldsPanel.add(timeField, gbc);

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 3;
        formFieldsPanel.add(phoneLabel, gbc);

        phoneField = new JTextField(15);
        gbc.gridx = 1;
        formFieldsPanel.add(phoneField, gbc);

        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 4;
        formFieldsPanel.add(countryLabel, gbc);

        countryField = new JTextField(15);
        gbc.gridx = 1;
        formFieldsPanel.add(countryField, gbc);

        JLabel destinationLabel = new JLabel("Destination:");
        destinationLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 5;
        formFieldsPanel.add(destinationLabel, gbc);

        destinationField = new JTextField(15);
        gbc.gridx = 1;
        formFieldsPanel.add(destinationField, gbc);

        JLabel placesLabel = new JLabel("Places:");
        placesLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 6;
        formFieldsPanel.add(placesLabel, gbc);

        numOfPlacesField = new JTextField(15);
        gbc.gridx = 1;
        formFieldsPanel.add(numOfPlacesField, gbc);

        JLabel classLabel = new JLabel("Class:");
        classLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 7;
        formFieldsPanel.add(classLabel, gbc);

        ButtonGroup classGroup = new ButtonGroup();
        firstClassRadio = new JRadioButton("First Class");
        economyRadio = new JRadioButton("Economy");

        classGroup.add(firstClassRadio);
        classGroup.add(economyRadio);

        JPanel classPanel = new JPanel();
        classPanel.add(firstClassRadio);
        classPanel.add(economyRadio);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formFieldsPanel.add(classPanel, gbc);

        reserveButton = new JButton("Reserve");
        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action listener for the reserve button
                if (e.getSource() == reserveButton) {

                    String id = idField.getText();
                    String name = nameField.getText();
                    String time = timeField.getText();
                    String destination = destinationField.getText();
                    String country = countryField.getText();
                    String phone = phoneField.getText();
                    int numOfPlaces = Integer.parseInt(numOfPlacesField.getText());
                    String className = firstClassRadio.isSelected() ? "First Class" : "Economy";

                    String message = "Reservation Details:\n" +
                            "ID: " + id + "\n" +
                            "Name: " + name + "\n" +
                            "Time: " + time + "\n" +
                            "Destination: " + destination + "\n" +
                            "Country: " + country + "\n" +
                            "Phone Number: " + phone + "\n" +
                            "Number of Places: " + numOfPlaces + "\n" +
                            "Class: " + className;




                    try {
                        Connection connection = MyConnection.getConnection("jdbc:mysql://127.0.0.1/AirPlanProject", "root", "");

                        String sql = "INSERT INTO reservations1 (id, name, time, destination, country, phone, places, class) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1, id);
                        statement.setString(2, name);
                        statement.setString(3, time);
                        statement.setString(4, destination);
                        statement.setString(5, country);
                        statement.setString(6, phone);
                        statement.setInt(7, numOfPlaces);
                        statement.setString(8, className);

                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            JOptionPane.showMessageDialog(panel, "Reservation created successfully.");


                        } else {
                            JOptionPane.showMessageDialog(panel, "Failed to create reservation.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
                    }



                }
            }


        });


        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        formFieldsPanel.add(reserveButton, gbc);

        reservationFormPanel.add(formFieldsPanel, BorderLayout.CENTER);

        panel.add(reservationFormPanel, BorderLayout.CENTER);

        ImageIcon imageIcon = new ImageIcon(getImageFromUrl("https://www.kindpng.com/picc/m/150-1509057_messages-pressed-message-icon-black-png-transparent-png.png").getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(imageIcon);
        gbc.gridx = 6;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        formFieldsPanel.add(imageLabel,gbc);

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                Chat chat = new Chat();
                chat.setVisible(true);
            }
        });


        return panel;
    }



    private JPanel createTablePanel(String username) {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"ID", "Name", "Time", "Destination", "Class", "Country", "Phone Number", "Number of Places"};

        String sql = "SELECT * FROM reservations1 WHERE name = ?";
        try (Connection connection = MyConnection.getConnection("jdbc:mysql://127.0.0.1/AirPlanProject", "root", "");
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {

                List<Object[]> rows = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String time = resultSet.getString("time");
                    String destination = resultSet.getString("destination");
                    String className = resultSet.getString("class");
                    String country = resultSet.getString("country");
                    String phone = resultSet.getString("phone");
                    int numOfPlaces = resultSet.getInt("places");


                    Object[] rowData = {id, name, time, destination, className, country, phone, numOfPlaces};
                    rows.add(rowData);
                }

                Object[][] rowDataArray = new Object[rows.size()][];
                rowDataArray = rows.toArray(rowDataArray);

                DefaultTableModel model = new DefaultTableModel(rowDataArray, columnNames);

                JTable reservationTable = new JTable(model);

                JScrollPane scrollPane = new JScrollPane(reservationTable);

                panel.add(scrollPane, BorderLayout.CENTER);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Error fetching data from the database: " + ex.getMessage());
        }

        return panel;
    }


    private JPanel createTableFlights() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel tablePanel = new JPanel(new BorderLayout());

        String[] columnNames = {"name", "Time", "Destination","numOfPlaces"};

        try (Connection connection = MyConnection.getConnection("jdbc:mysql://127.0.0.1/AirPlanProject", "root", "");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM flights");
             ResultSet resultSet = statement.executeQuery()) {

            List<Object[]> rows = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String time = resultSet.getString("time");
                String destination = resultSet.getString("destination");
                int numOfPlaces = resultSet.getInt("numOfPlaces");

                Object[] rowData = {id, name, time, destination, numOfPlaces};
                rows.add(rowData);
            }

            Object[][] rowDataArray = new Object[rows.size()][];
            rowDataArray = rows.toArray(rowDataArray);

            DefaultTableModel model = new DefaultTableModel(rowDataArray, columnNames);

            JTable flightsTable = new JTable(model);

            JScrollPane scrollPane = new JScrollPane(flightsTable);

            tablePanel.add(scrollPane, BorderLayout.CENTER);

            panel.add(tablePanel, BorderLayout.CENTER);




        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Error fetching data from the database: " + ex.getMessage());
        }

        return panel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("New Reservation")) {

            remove(tablePanel);
            remove(tableofflights);
            revalidate();
            repaint();
            add(reservationFormPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        } else if (e.getActionCommand().equals("My Reservations")) {

            remove(reservationFormPanel);
            remove(tableofflights);
            revalidate();
            repaint();

            tablePanel = createTablePanel(username);
            add(tablePanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        } else if (e.getActionCommand().equals("Flights")) {

            remove(reservationFormPanel);
            remove(tablePanel);
            revalidate();
            repaint();
            tableofflights = createTableFlights();
            add(tableofflights, BorderLayout.CENTER);
            revalidate();
            repaint();
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
        String username = "example_username";
        SwingUtilities.invokeLater(() -> new ReservationPage(username));



    }
}

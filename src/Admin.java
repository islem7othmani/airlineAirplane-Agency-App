import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

public class Admin extends JFrame implements ActionListener {
    private JPanel reservationFormPanel;
    private JPanel tablePanel, tableofflights, flightFormPanel;
    private JRadioButton firstClassRadio, economyRadio;
    private JTextField idField, nameField, timeField, destinationField, countryField, phoneField, numOfPlacesField, nbpField2,idField2,nameField2,timeField2,destinationField2;
    private JButton reserveButton;
    private String username;

    public String getUsername() {
        return username;
    }

    public Admin() {
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
        JButton newflightsButton = new JButton("new Flights");

        newReservationButton.setForeground(Color.WHITE);
        newReservationButton.setBackground(Color.RED);
        newReservationButton.setBorderPainted(false);
        myReservationsButton.setForeground(Color.WHITE);
        myReservationsButton.setBackground(Color.RED);
        myReservationsButton.setBorderPainted(false);
        flightsButton.setForeground(Color.WHITE);
        flightsButton.setBackground(Color.RED);
        flightsButton.setBorderPainted(false);
        newflightsButton.setForeground(Color.WHITE);
        newflightsButton.setBackground(Color.RED);
        newflightsButton.setBorderPainted(false);

        newReservationButton.addActionListener(this);
        myReservationsButton.addActionListener(this);
        flightsButton.addActionListener(this);
        newflightsButton.addActionListener(this);

        sidebarPanel.add(newReservationButton);
        sidebarPanel.add(myReservationsButton);
        sidebarPanel.add(flightsButton);
        sidebarPanel.add(newflightsButton);

        add(sidebarPanel, BorderLayout.WEST);

        reservationFormPanel = createReservationFormPanel();

        flightFormPanel =createFlightsFormPanel();

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

                if (e.getSource() == reserveButton) {
                    String id = idField.getText();
                    String name = nameField.getText();
                    String time = timeField.getText();
                    String destination = destinationField.getText();
                    String country = countryField.getText();
                    String phone = phoneField.getText();
                    int numOfPlaces = Integer.parseInt(numOfPlacesField.getText());
                    String className = firstClassRadio.isSelected() ? "First Class" : "Economy";

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

        return panel;
    }


    private JPanel createFlightsFormPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel flightsFormPanel = new JPanel(new BorderLayout());
        JPanel formFlightsFieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel idLabel2 = new JLabel("ID:");
        idLabel2.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formFlightsFieldsPanel.add(idLabel2, gbc);

        JLabel nameLabel2= new JLabel("Name:");
        nameLabel2.setForeground(Color.RED);
        gbc.gridy = 1;
        formFlightsFieldsPanel.add(nameLabel2, gbc);

        JLabel timeLabel2 = new JLabel("Time:");
        timeLabel2.setForeground(Color.RED);
        gbc.gridy = 2;
        formFlightsFieldsPanel.add(timeLabel2, gbc);

        JLabel destinationLabel2 = new JLabel("Destination:");
        destinationLabel2.setForeground(Color.RED);
        gbc.gridy = 3;
        formFlightsFieldsPanel.add(destinationLabel2, gbc);

        JLabel nbpLabel2 = new JLabel("Number of Places:");
        nbpLabel2.setForeground(Color.RED);
        gbc.gridy = 4;
        formFlightsFieldsPanel.add(nbpLabel2, gbc);


        idField2 = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        formFlightsFieldsPanel.add(idField2, gbc);

        nameField2 = new JTextField(20);
        gbc.gridy = 1;
        formFlightsFieldsPanel.add(nameField2, gbc);

        timeField2 = new JTextField(20);
        gbc.gridy = 2;
        formFlightsFieldsPanel.add(timeField2, gbc);

        destinationField2 = new JTextField(20);
        gbc.gridy = 3;
        formFlightsFieldsPanel.add(destinationField2, gbc);

        nbpField2 = new JTextField(20);
        gbc.gridy = 4;
        formFlightsFieldsPanel.add(nbpField2, gbc);


        JButton createFlightButton = new JButton("Create Flight");
        createFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == createFlightButton) {
                    String id = idField2.getText();
                    String name = nameField2.getText();
                    String time = timeField2.getText();
                    String destination = destinationField2.getText();
                    String numOfPlacesStr = nbpField2.getText();

                    if (!numOfPlacesStr.isEmpty()) {
                        try {
                            int numOfPlaces = Integer.parseInt(numOfPlacesStr);
                            Connection connection = MyConnection.getConnection("jdbc:mysql://127.0.0.1/AirPlanProject", "root", "");

                            String sql = "INSERT INTO flights (id, name, time, destination, numOfPlaces) VALUES (?, ?, ?, ?, ?)";
                            PreparedStatement statement = connection.prepareStatement(sql);
                            statement.setString(1, id);
                            statement.setString(2, name);
                            statement.setString(3, time);
                            statement.setString(4, destination);
                            statement.setInt(5, numOfPlaces);


                            int rowsInserted = statement.executeUpdate();
                            if (rowsInserted > 0) {
                                JOptionPane.showMessageDialog(panel, "Flight created successfully.");

                            } else {
                                JOptionPane.showMessageDialog(panel, "Failed to create flight.");
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(panel, "Invalid input for number of places.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(panel, "Number of places cannot be empty.");
                    }
                }
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formFlightsFieldsPanel.add(createFlightButton, gbc);

        flightsFormPanel.add(formFlightsFieldsPanel, BorderLayout.CENTER);

        panel.add(flightsFormPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTablePanel(String username) {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"ID", "Name", "Time", "Destination", "Class", "Country", "Phone Number", "Number of Places"};

        try (Connection connection = MyConnection.getConnection("jdbc:mysql://127.0.0.1/AirPlanProject", "root", "");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservations1");
             ResultSet resultSet = statement.executeQuery()) {

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

            reservationTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        int row = reservationTable.rowAtPoint(e.getPoint());
                        reservationTable.setRowSelectionInterval(row, row);
                        showPopupMenu(e.getX(), e.getY(), row, reservationTable);
                    }
                }
            });

            panel.add(scrollPane, BorderLayout.CENTER);


        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Error fetching data from the database: " + ex.getMessage());
        }

        return panel;
    }

    private void showPopupMenu(int x, int y, int row, JTable table) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete");
        JMenuItem updateItem = new JMenuItem("Update");

        deleteItem.addActionListener(e -> deleteReservation(row, table));

        updateItem.addActionListener(e -> updateReservation(row, table));

        popupMenu.add(deleteItem);
        popupMenu.add(updateItem);

        popupMenu.show(table, x, y);
    }

    private void deleteReservation(int row, JTable table) {
        int idToDelete = (int) table.getValueAt(row, 0);
        try (Connection connection = MyConnection.getConnection("jdbc:mysql://127.0.0.1/AirPlanProject", "root", "");
             PreparedStatement statement = connection.prepareStatement("DELETE FROM reservations1 WHERE id = ?")) {
            statement.setInt(1, idToDelete);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ((DefaultTableModel) table.getModel()).removeRow(row);
                JOptionPane.showMessageDialog(null, "Reservation deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete reservation.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting reservation: " + ex.getMessage());
        }
    }

    private void updateReservation(int row, JTable table) {
        int idToUpdate = (int) table.getValueAt(row, 0);
        String[] options = {"Name", "Time", "Destination", "Class", "Country", "Phone Number", "Number of Places"};
        String selectedOption = (String) JOptionPane.showInputDialog(null, "Select field to update:", "Update Reservation", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (selectedOption != null) {
            String newValue = JOptionPane.showInputDialog(null, "Enter new " + selectedOption.toLowerCase() + ":");
            if (newValue != null) {
                //in db
                try (Connection connection = MyConnection.getConnection("jdbc:mysql://127.0.0.1/AirPlanProject", "root", "")) {
                    String sql = "UPDATE reservations1 SET " + selectedOption.toLowerCase() + " = ? WHERE id = ?";
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, newValue);
                        statement.setInt(2, idToUpdate);
                        int rowsAffected = statement.executeUpdate();
                  //in ui
                        if (rowsAffected > 0) {
                            switch (selectedOption) {
                                case "Name":
                                    table.setValueAt(newValue, row, 1);
                                    break;
                                case "Time":
                                    table.setValueAt(newValue, row, 2);
                                    break;
                                case "Destination":
                                    table.setValueAt(newValue, row, 3);
                                    break;
                                case "Class":
                                    table.setValueAt(newValue, row, 4);
                                    break;
                                case "Country":
                                    table.setValueAt(newValue, row, 5);
                                    break;
                                case "Phone Number":
                                    table.setValueAt(newValue, row, 6);
                                    break;
                                case "Number of Places":
                                    table.setValueAt(newValue, row, 7);
                                    break;
                                default:
                                    break;
                            }
                            JOptionPane.showMessageDialog(null, "Reservation updated successfully.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to update reservation.");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error updating reservation: " + ex.getMessage());
                }
            }
        }
    }


    private JPanel createTableFlights() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel tablePanel = new JPanel(new BorderLayout());

        String[] columnNames = {"id","name", "Time", "Destination","numOfPlaces"};

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

            flightsTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        int row = flightsTable.rowAtPoint(e.getPoint());
                        flightsTable.setRowSelectionInterval(row, row);
                        showPopupMenu2(e.getX(), e.getY(), row, flightsTable);
                    }
                }
            });

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Error fetching data from the database: " + ex.getMessage());
        }

        return panel;
    }

    private void showPopupMenu2(int x, int y, int row, JTable table) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete");
        JMenuItem updateItem = new JMenuItem("Update");

        deleteItem.addActionListener(e -> deleteFlights(row, table));

        updateItem.addActionListener(e -> updateflight(row, table));

        popupMenu.add(deleteItem);
        popupMenu.add(updateItem);

        popupMenu.show(table, x, y);
    }
    private void deleteFlights(int row, JTable table) {
        int idToDelete = (int) table.getValueAt(row, 0);
        try (Connection connection = MyConnection.getConnection("jdbc:mysql://127.0.0.1/AirPlanProject", "root", "");
             PreparedStatement statement = connection.prepareStatement("DELETE FROM flights WHERE id = ?")) {
            statement.setInt(1, idToDelete);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ((DefaultTableModel) table.getModel()).removeRow(row);
                JOptionPane.showMessageDialog(null, "flight deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete flight.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting flight: " + ex.getMessage());
        }
    }

    private void updateflight(int row, JTable table) {
        int idToUpdate = (int) table.getValueAt(row, 0);
        String[] options = {"name", "Time", "Destination","numOfPlaces"};
        String selectedOption = (String) JOptionPane.showInputDialog(null, "Select field to update:", "Update flight", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (selectedOption != null) {
            String newValue = JOptionPane.showInputDialog(null, "Enter new " + selectedOption.toLowerCase() + ":");
            if (newValue != null) {
                try (Connection connection = MyConnection.getConnection("jdbc:mysql://127.0.0.1/AirPlanProject", "root", "")) {
                    String sql = "UPDATE flights SET " + selectedOption.toLowerCase() + " = ? WHERE id = ?";
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, newValue);
                        statement.setInt(2, idToUpdate);
                        int rowsAffected = statement.executeUpdate();

                        if (rowsAffected > 0) {
                            switch (selectedOption) {

                                case "name":
                                    table.setValueAt(newValue, row, 1);
                                    break;
                                case "Time":
                                    table.setValueAt(newValue, row, 2);
                                    break;
                                case "Destination":
                                    table.setValueAt(newValue, row, 3);
                                    break;

                                case "numOfPlaces":
                                    table.setValueAt(newValue, row, 4);
                                    break;
                                default:
                                    break;
                            }
                            JOptionPane.showMessageDialog(null, "flight updated successfully.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to update flight.");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error updating flight: " + ex.getMessage());
                }
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Action listener for sidebar buttons
        if (e.getActionCommand().equals("New Reservation")) {

            remove(tablePanel);
            remove(tableofflights);
            remove(flightFormPanel);
            revalidate();
            repaint();
            add(reservationFormPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        } else if (e.getActionCommand().equals("My Reservations")) {

            remove(reservationFormPanel);
            remove(tableofflights);
            remove(flightFormPanel);
            revalidate();
            repaint();
            tablePanel = createTablePanel(username);
            add(tablePanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        } else if (e.getActionCommand().equals("Flights")) {

            remove(reservationFormPanel);
            remove(tablePanel);
            remove(flightFormPanel);
            revalidate();
            repaint();
            tableofflights = createTableFlights();
            add(tableofflights, BorderLayout.CENTER);
            revalidate();
            repaint();
        } else if (e.getActionCommand().equals("new Flights")) {

            remove(reservationFormPanel);
            remove(tablePanel);
            remove(tableofflights);
            revalidate();
            repaint();

            add(flightFormPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }

    public static void main(String[] args) {
        String username = "example_username";
        SwingUtilities.invokeLater(() -> new ReservationPage(username));
    }
}

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerEnd {

    private JFrame frame;
    private JTextField textField;
    private static JTextArea textArea;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static DataInputStream input;
    private static DataOutputStream output;
    private JScrollPane scrollPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ServerEnd window = new ServerEnd();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            serverSocket = new ServerSocket(5000);
            while (true) {
                clientSocket = serverSocket.accept();
                input = new DataInputStream(clientSocket.getInputStream());
                String string = input.readUTF();
                textArea.setText(textArea.getText() + "\n" + "Client: " + string);
            }
        } catch (IOException e) {
            textArea.setText(textArea.getText() + "\n" + "Network issues ");
            try {
                Thread.sleep(2000);
                System.exit(0);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    public ServerEnd() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(UIManager.getColor("MenuBar.highlight"));
        frame.setBounds(100, 100, 605, 378);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("Soumyadip Networking Project : Server Chat");

        textField = new JTextField();
        textField.setFont(new Font("Lato Medium", Font.PLAIN, 22));
        textField.setForeground(Color.ORANGE);
        textField.setBackground(Color.DARK_GRAY);
        textField.setBounds(12, 45, 344, 38);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("Send");
        btnNewButton.addActionListener(e -> {
            if (textField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please write some text !");
            } else {
                textArea.setText(textArea.getText() + "\n" + "Server: " + textField.getText());
                try {
                    output = new DataOutputStream(clientSocket.getOutputStream());
                    output.writeUTF(textField.getText());
                } catch (IOException e1) {
                    textArea.setText(textArea.getText() + "\n " + " Network issues");
                    try {
                        Thread.sleep(2000);
                        System.exit(0);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
                textField.setText("");
            }
        });
        btnNewButton.setFont(new Font("Georgia", Font.BOLD, 22));
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.setBackground(Color.RED);
        btnNewButton.setBounds(398, 45, 164, 38);
        frame.getContentPane().add(btnNewButton);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 149, 557, 157);
        frame.getContentPane().add(scrollPane);

        textArea = new JTextArea();
        scrollPane.setViewportView(textArea);
        textArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        textArea.setForeground(new Color(255, 255, 255));
        textArea.setBackground(new Color(0, 128, 128));
    }
}

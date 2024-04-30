import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Chat extends JFrame {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private JTextField messageField;
    private JTextArea chatArea;

    public Chat() {
        setTitle("Chat Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        messageField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        inputPanel.add(messageField, BorderLayout.CENTER);

        JButton sendButton = new JButton("Send");
        sendButton.setBackground(Color.RED);
        sendButton.setForeground(Color.WHITE);
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        inputPanel.add(sendButton, BorderLayout.EAST);

        panel.add(inputPanel, BorderLayout.SOUTH);

        add(panel);

        try {
            socket = new Socket("127.0.0.1",9000);

            System.out.println("Connected...");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            Thread receiveThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        String serverMessage;
                        while ((serverMessage = in.readLine()) != null) {
                            chatArea.append("Server: " + serverMessage + "\n");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            receiveThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        try {
            String clientMessage = messageField.getText();
            out.println(clientMessage);
            chatArea.append("Client: " + clientMessage + "\n");
            messageField.setText("");
            if (clientMessage.equalsIgnoreCase("exit")) {
                socket.close();
                in.close();
                out.close();
                dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Chat().setVisible(true);
            }
        });
    }
}

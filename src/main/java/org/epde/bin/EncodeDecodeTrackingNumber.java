package org.epde.bin;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncodeDecodeTrackingNumber extends JFrame {

    private JTextField inputField;
    private JTextArea outputArea;

    public EncodeDecodeTrackingNumber() {
        setTitle("Tracking Number Encoder/Decoder");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setLocationRelativeTo(null);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel label = new JLabel("Enter Tracking Number:");
        label.setBounds(10, 20, 200, 25);
        panel.add(label);

        inputField = new JTextField(20);
        inputField.setBounds(220, 20, 250, 25);
        panel.add(inputField);

        JButton encodeButton = new JButton("Encode");
        encodeButton.setBounds(50, 70, 150, 30);
        panel.add(encodeButton);

        JButton decodeButton = new JButton("Decode");
        decodeButton.setBounds(300, 70, 150, 30);
        panel.add(decodeButton);

        outputArea = new JTextArea();
        outputArea.setBounds(10, 120, 480, 100);
        panel.add(outputArea);

        encodeButton.addActionListener(e -> {
            String inputText = inputField.getText();
            byte[] encodeBytes = Base64.getEncoder().encode(inputText.getBytes());
            String encodedText = new String(encodeBytes, StandardCharsets.UTF_8);
            outputArea.setText("Encoded Tracking Number: " + encodedText);
        });

        decodeButton.addActionListener(e -> {
            String inputText = inputField.getText();
            try {
                byte[] decodedBytes = Base64.getDecoder().decode(inputText);
                String decodedText = new String(decodedBytes, StandardCharsets.UTF_8);
                outputArea.setText("Decoded Tracking Number: " + decodedText);
            } catch (IllegalArgumentException ex) {
                outputArea.setText("Invalid Base64 decoding: " + ex.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EncodeDecodeTrackingNumber EncodeDecodeTrackingNumber = new EncodeDecodeTrackingNumber();
            EncodeDecodeTrackingNumber.setVisible(true);
        });
    }
}

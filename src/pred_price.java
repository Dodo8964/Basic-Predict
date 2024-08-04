import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class pred_price extends JFrame {
    private JTextField openField, highField, lowField, peField, pbField;
    private JLabel outputLabel;

    public pred_price() {
        setTitle("Price Recommender");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        openField = new JTextField(10);
        highField = new JTextField(10);
        lowField = new JTextField(10);
        peField = new JTextField(10);
        pbField = new JTextField(10);

        panel.add(new JLabel("Open:"));
        panel.add(openField);
        panel.add(new JLabel("High:"));
        panel.add(highField);
        panel.add(new JLabel("Low:"));
        panel.add(lowField);
        panel.add(new JLabel("P/E:"));
        panel.add(peField);
        panel.add(new JLabel("P/B:"));
        panel.add(pbField);

        JButton submitButton = new JButton("Submit");
        outputLabel = new JLabel("");
        panel.add(submitButton);
        panel.add(outputLabel);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendDataToPython();
            }
        });

        add(panel);
        setVisible(true);
    }

    private void sendDataToPython() {
        try {
            // Get integer values from the input fields
            int open = Integer.parseInt(openField.getText());
            int high = Integer.parseInt(highField.getText());
            int low = Integer.parseInt(lowField.getText());
            int pe = Integer.parseInt(peField.getText());
            int pb = Integer.parseInt(pbField.getText());

            // Execute Python script
            Process process = Runtime.getRuntime().exec("python D:\\Harshith\\Mini\\get.py");

            // Send integer values to Python script
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            writer.write(open + "\n");
            writer.write(high + "\n");
            writer.write(low + "\n");
            writer.write(pe + "\n");
            writer.write(pb + "\n");
            writer.flush();

            // Read output from Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String outputData = reader.readLine();

            // Display output from Python script
            System.out.println("Output received from Python: " + outputData); // Debug print
            outputLabel.setText("Price to Buy at: " + outputData);

            // Close resources
            writer.close();
            reader.close();
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new pred_price();
            }
        });
    }
}

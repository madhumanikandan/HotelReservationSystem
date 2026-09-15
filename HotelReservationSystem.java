
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HotelReservationSystem extends JFrame implements ActionListener {

    JTextField guestNameField, nightsField;
    JComboBox<String> roomTypeBox;
    JButton bookButton, clearButton;
    JTextArea outputArea;

    // Room rates per night
    final double SINGLE_RATE = 1500.0;
    final double DELUXE_RATE = 2500.0;
    final double SUITE_RATE = 4000.0;

    public HotelReservationSystem() {
        setTitle("Hotel Reservation System");
        setSize(430, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel nameLabel = new JLabel("Guest Name:");
        nameLabel.setBounds(20, 20, 100, 25);
        add(nameLabel);

        guestNameField = new JTextField();
        guestNameField.setBounds(150, 20, 220, 25);
        add(guestNameField);

        JLabel roomLabel = new JLabel("Room Type:");
        roomLabel.setBounds(20, 55, 100, 25);
        add(roomLabel);

        String[] roomTypes = {"Single", "Deluxe", "Suite"};
        roomTypeBox = new JComboBox<>(roomTypes);
        roomTypeBox.setBounds(150, 55, 220, 25);
        add(roomTypeBox);

        JLabel nightsLabel = new JLabel("No. of Nights:");
        nightsLabel.setBounds(20, 90, 110, 25);
        add(nightsLabel);

        nightsField = new JTextField();
        nightsField.setBounds(150, 90, 220, 25);
        add(nightsField);

        bookButton = new JButton("Book Room");
        bookButton.setBounds(80, 130, 110, 30);
        add(bookButton);
        bookButton.addActionListener(this);

        clearButton = new JButton("Clear");
        clearButton.setBounds(220, 130, 110, 30);
        add(clearButton);
        clearButton.addActionListener(this);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBounds(20, 180, 370, 240);
        add(scrollPane);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookButton) {
            bookRoom();
        } else if (e.getSource() == clearButton) {
            clearFields();
        }
    }

    private void bookRoom() {
        String name = guestNameField.getText().trim();
        String room = (String) roomTypeBox.getSelectedItem();
        String nightsText = nightsField.getText().trim();

        if (name.isEmpty() || nightsText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int nights;
        try {
            nights = Integer.parseInt(nightsText);
            if (nights <= 0) {
                JOptionPane.showMessageDialog(this, "Nights must be a positive number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of nights.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double rate;
        switch (room) {
            case "Single":
                rate = SINGLE_RATE;
                break;
            case "Deluxe":
                rate = DELUXE_RATE;
                break;
            case "Suite":
                rate = SUITE_RATE;
                break;
            default:
                rate = 0;
        }

        double totalCost = rate * nights;
        double tax = totalCost * 0.12;
        double grandTotal = totalCost + tax;

        String receipt = "----- Booking Confirmation -----\n"
                + "Guest Name   : " + name + "\n"
                + "Room Type    : " + room + "\n"
                + "Rate/Night   : Rs. " + rate + "\n"
                + "No. of Nights: " + nights + "\n"
                + "Subtotal     : Rs. " + totalCost + "\n"
                + "Tax (12%)    : Rs. " + String.format("%.2f", tax) + "\n"
                + "Grand Total  : Rs. " + String.format("%.2f", grandTotal) + "\n"
                + "---------------------------------\n"
                + "Status       : Room Booked Successfully!\n";

        outputArea.setText(receipt);
    }

    private void clearFields() {
        guestNameField.setText("");
        nightsField.setText("");
        roomTypeBox.setSelectedIndex(0);
        outputArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HotelReservationSystem());
    }
}

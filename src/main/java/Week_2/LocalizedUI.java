package Week_2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class LocalizedUI extends JFrame implements ActionListener {
    private JButton englishButton, persianButton, japaneseButton;
    private JLabel nameLabel;
    private ResourceBundle messages;

    public LocalizedUI() {
        setTitle("Localized UI Application");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        englishButton = new JButton();
        persianButton = new JButton();
        japaneseButton = new JButton();

        nameLabel = new JLabel();

        englishButton.addActionListener(this);
        persianButton.addActionListener(this);
        japaneseButton.addActionListener(this);

        add(englishButton);
        add(persianButton);
        add(japaneseButton);
        add(nameLabel);

        // Default to English messages
        messages = ResourceBundle.getBundle("messages_messages", Locale.ENGLISH);
        updateLabels();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == englishButton) {
            messages = ResourceBundle.getBundle("messages_messages", Locale.ENGLISH);
        } else if (e.getSource() == persianButton) {
            messages = ResourceBundle.getBundle("messages_fa_IR", new Locale("fa", "IR"));
        } else if (e.getSource() == japaneseButton) {
            messages = ResourceBundle.getBundle("messages_ja_JA", new Locale("ja", "JA"));
        }
        updateLabels();
    }

    private void updateLabels() {
        nameLabel.setText(messages.getString("nameLabel"));
        englishButton.setText(messages.getString("englishButton"));
        persianButton.setText(messages.getString("persianButton"));
        japaneseButton.setText(messages.getString("japaneseButton"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LocalizedUI ui = new LocalizedUI();
            ui.setVisible(true);
        });
    }
}

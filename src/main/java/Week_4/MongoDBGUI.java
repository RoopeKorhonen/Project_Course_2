import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.bson.Document;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBGUI extends JFrame {

    private JTextField idField;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField cityField;

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoDBGUI() {
        super("MongoDB GUI Example");
        initializeMongoDB();

        // Create input fields
        idField = new JTextField(20);
        nameField = new JTextField(20);
        ageField = new JTextField(20);
        cityField = new JTextField(20);

        // Create buttons
        JButton addButton = new JButton("Add");
        JButton readButton = new JButton("Read");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        // Layout using GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components to the frame
        add(new JLabel("ID:"), gbc);
        gbc.gridx++;
        add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Name:"), gbc);
        gbc.gridx++;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Age:"), gbc);
        gbc.gridx++;
        add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("City:"), gbc);
        gbc.gridx++;
        add(cityField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(addButton, gbc);
        gbc.gridx++;
        add(readButton, gbc);
        gbc.gridx++;
        add(updateButton, gbc);
        gbc.gridx++;
        add(deleteButton, gbc);

        // Add button action listeners
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRecord();
            }
        });

        readButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                readRecord();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRecord();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteRecord();
            }
        });

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeMongoDB() {
        // Connect to MongoDB server running on localhost
        mongoClient = MongoClients.create("mongodb://localhost:secret");
        database = mongoClient.getDatabase("roopemongodb");
        collection = database.getCollection("roopecollection");
    }

    private void addRecord() {
        String id = idField.getText();
        String name = nameField.getText();
        String age = ageField.getText();
        String city = cityField.getText();

        Document document = new Document("id", id)
                .append("name", name)
                .append("age", age)
                .append("city", city);
        collection.insertOne(document);
        JOptionPane.showMessageDialog(this, "Record added successfully!");
    }

    private void readRecord() {
        String name = nameField.getText();
        Document document = collection.find(new Document("name", name)).first();
        if (document != null) {
            idField.setText(document.getString("id"));
            ageField.setText(document.getString("age"));
            cityField.setText(document.getString("city"));
        } else {
            JOptionPane.showMessageDialog(this, "Record not found!");
        }
    }

    private void updateRecord() {
        String name = nameField.getText();
        String age = ageField.getText();
        String city = cityField.getText();

        collection.updateOne(new Document("name", name),
                new Document("$set", new Document("age", age).append("city", city)));
        JOptionPane.showMessageDialog(this, "Record updated successfully!");
    }

    private void deleteRecord() {
        String name = nameField.getText();
        collection.deleteOne(new Document("name", name));
        JOptionPane.showMessageDialog(this, "Record deleted successfully!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MongoDBGUI());
    }

    @Override
    public void dispose() {
        // Close MongoDB connection
        mongoClient.close();
        super.dispose();
    }
}
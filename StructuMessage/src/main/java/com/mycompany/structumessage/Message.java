package com.mycompany.structumessage;

import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Message {

    // Private fields to store message details
    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;
    private boolean isSent;

    // Static field to keep track of total messages sent
    private static int totalMessages = 0;

    // Constructor to initialize a new Message object with provided details
    public Message(String recipient, String message, int messageNumber) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash(this.messageID, messageNumber, message);
        this.isSent = false;
    }

    // Method to generate a unique 10-digit message ID
    // Title: Generating 10 digits unique random number in Java
    // Author: Stack Overflow
    // Date: 2025
    // Available: https://stackoverflow.com/questions/5328822/generating-10-digits-unique-random-number-in-java
    public static String generateMessageID() {
        Random rand = new Random();
        String id = "";
        for (int i = 0; i < 10; i++) {
            id += rand.nextInt(10); // generates a single digit and appends to ID
        }
        return id;
    }

    // Method to check if the message ID is valid (not null and up to 10 characters)
    public static boolean checkMessageID(String id) {
        if (id != null && id.length() <= 10) {
            return true;
        } else {
            return false;
        }
    }

    // Method to validate the recipient's cellphone number
    // Criteria: Starts with '+' and length between 11 and 13 characters
    public static boolean checkRecipientCell(String number) {
        if (number.startsWith("+") && number.length() <= 13 && number.length() >= 11) {
            return true;
        } else {
            return false;
        }
    }

    // Method to validate the length of the message
    // Criteria: Maximum of 250 characters
    public static String validateMessageLength(String msg) {
        if (msg.length() <= 250) {
            return "Message ready to send.";
        } else {
            int extra = msg.length() - 250;
            return "Message exceeds 250 characters by " + extra + ", please reduce size.";
        }
    }

    // Method to create a unique message hash based on message ID, message number, and content
    // Title: Java String substring() Method
    // Author: W3Schools
    // Date: 2025
    // Available: https://www.w3schools.com/java/ref_string_substring.asp
    public static String createMessageHash(String id, int msgNum, String msg) {
        String[] words = msg.trim().split("\\s+"); // Handles multiple spaces safely

        String first = "NA";
        String last = "NA";

        if (words.length >= 1 && !words[0].isEmpty()) {
            first = words[0].replaceAll("[^a-zA-Z0-9]", "");
        }

        if (words.length >= 2 && !words[words.length - 1].isEmpty()) {
            last = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");
        }

        String partID = id.substring(0, 2);
        return (partID + ":" + msgNum + ":" + first + last).toUpperCase();
    }

    // Method to handle different message actions based on user choice
    public String sendOptions(String choice) {
        if (choice.equals("send")) {
            isSent = true;
            totalMessages++;
            return "Message successfully sent.";
        } else if (choice.equals("discard")) {
            return "Press 0 to delete message.";
        } else if (choice.equals("store")) {
            storeMessageToJson();
            return "Message successfully stored.";
        } else {
            return "Invalid option.";
        }
    }

    // Method to return message details as a formatted string
    public String printDetails() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + message;
    }

    // Static method to return the total number of messages sent
    public static int returnTotalMessages() {
        return totalMessages;
    }

    // Method to store the message content into a JSON file
    // Title: How to write/create a JSON file using Java
    // Author: TutorialsPoint
    // Date: 2025
    // Available: https://www.tutorialspoint.com/how-to-write-create-a-json-file-using-java
    public void storeMessageToJson() {
        try {
            FileWriter file = new FileWriter("message.json", true); // Append mode

            // Manually create JSON string (like the example in your image)
            String jsonString = "{\n"
                    + "  \"messageHash\": \"" + messageHash.replace("\"", "\\\"") + "\",\n"
                    + "  \"recipient\": \"" + recipient.replace("\"", "\\\"") + "\",\n"
                    + "  \"message\": \"" + message.replace("\"", "\\\"") + "\"\n"
                    + "}\n";

            file.write(jsonString);
            file.close();
            System.out.println("Message saved to message.json");
        } catch (IOException e) {
            System.out.println("Error saving message.");
        }
    }

    // Getter methods to access private fields
    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public boolean isSent() {
        return isSent;
    }
}

// Title: StructuMessage Application – Main Class  
// Author: Oracle, Stack Overflow, TheServerSide, W3Schools, GeeksforGeeks, Baeldung, TutorialsPoint, JavaCodeGeeks, Mozilla MDN, The IIE / Rochelle Moodley  
// Date: 26 May 2025  
// Version: 1.0  
// Available: https://docs.oracle.com/javase/8/docs/api/javax/swing/JOptionPane.html  
// Additional Reference: 
// Title: JOptionPane Input Validation Example  
// Author: Stack Overflow  
// Date: 2025  
// Available: https://stackoverflow.com/questions/3544521/user-input-validation-for-joptionpane-showinputdialog  
// Additional Reference: 
// Title: Java Array Size Explained by Example  
// Author: TheServerSide  
// Date: 2025  
// Available: https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/Java-array-size-explained-by-example  
// Additional Reference: 
// Title: Java Conditions (if, else, switch)  
// Author: W3Schools  
// Date: 2025  
// Available: https://www.w3schools.com/java/java_conditions.asp  
// Additional Reference: 
// Title: Arrays in Java  
// Author: GeeksforGeeks  
// Date: 2025  
// Available: https://www.geeksforgeeks.org/arrays-in-java/  
// Additional Reference: 
// Title: Introduction to Java Swing  
// Author: Baeldung  
// Date: 2025  
// Available: https://www.baeldung.com/java-swing  
// Additional Reference: 
// Title: Java Strings Tutorial  
// Author: TutorialsPoint  
// Date: 2025  
// Available: https://www.tutorialspoint.com/java/java_strings.htm  
// Additional Reference: 
// Title: Input Validation in Java  
// Author: JavaCodeGeeks  
// Date: 2025  
// Available: https://www.javacodegeeks.com/2019/01/input-validation-in-java.html  
// Additional Reference: 
// Title: JavaScript String.substring() (used for understanding string logic)  
// Author: Mozilla Developer Network (MDN)  
// Date: 2025  
// Available: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/substring  
// Additional Reference: 
// Title: PROG5121 Lecture Slides  
// Author: The Independent Institute of Education / Rochelle Moodley  
// Date: 2025  
// Available: Internal Slide Deck (Unpublished Material)

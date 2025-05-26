package com.mycompany.structumessage;

import javax.swing.*;

// Used to create dialog boxes for input/output
// Title: Introduction to Java Swing  
// Author: Baeldung  
// Date: 2025  
// Available: https://www.baeldung.com/java-swing  
public class StructuMessage {

    // Array to store sent messages
    // Title: Java Array Size Explained by Example  
    // Author: TheServerSide  
    // Date: 2025  
    // Available: https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/Java-array-size-explained-by-example  
    static Message[] sentMessages = new Message[10];
    static int sentCount = 0;

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat Registration");

        // === Registration ===
        String firstName = JOptionPane.showInputDialog("Enter your first name:");
        String lastName = JOptionPane.showInputDialog("Enter your last name:");

        String username = getValidUsername();
        String password = getValidPassword();
        String cellphone = getValidCellphone();

        QuickChatUser user = new QuickChatUser(username, password, cellphone, firstName, lastName);
        JOptionPane.showMessageDialog(null, user.register());

        // === Login ===
        JOptionPane.showMessageDialog(null, "Please log in");

        String inputUsername = JOptionPane.showInputDialog("Username:");
        String inputPassword = JOptionPane.showInputDialog("Password:");

        if (user.login(inputUsername, inputPassword)) {
            JOptionPane.showMessageDialog(null, user.loginStatusMessage(true));
            runApp();  // launch menu
        } else {
            JOptionPane.showMessageDialog(null, user.loginStatusMessage(false));
        }
    }

    // Main app menu using while loop (rubric requirement)
    public static void runApp() {
        boolean running = true;

        while (running) {
            String option = JOptionPane.showInputDialog("""
                Welcome to QuickChat!
                
                Choose an option:
                1) Send Message
                2) Show Recently Sent Messages
                3) Disregard Message/Quit
                """);

            if (option.equals("1")) {
                int total = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to send?"));

                // Send message using a for loop (rubric requirement)
                for (int i = 0; i < total; i++) {
                    boolean success = sendMessage(i);
                    if (!success) {
                        JOptionPane.showMessageDialog(null, "Message not sent. Skipping...");
                    }
                }

                JOptionPane.showMessageDialog(null, "Total messages processed: " + sentCount);
            } else if (option.equals("2")) {
                if (sentCount == 0) {
                    JOptionPane.showMessageDialog(null, "No messages sent yet.");
                } else {
                    StringBuilder report = new StringBuilder("📄 Sent Messages Report:\n\n");
                    for (int i = 0; i < sentCount; i++) {
                        Message msg = sentMessages[i];
                        report.append("Message #").append(i + 1)
                                .append("\nHash: ").append(msg.getMessageHash())
                                .append("\nRecipient: ").append(msg.getRecipient())
                                .append("\nMessage: ").append(msg.getMessage())
                                .append("\n\n");
                    }
                    JOptionPane.showMessageDialog(null, report.toString());
                }
            } else if (option.equals("3")) {
                running = false;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }

    public static boolean sendMessage(int msgNum) {
        String recipient = JOptionPane.showInputDialog("Enter recipient phone number (e.g., +27834567890):");

        // Validate recipient number using regex
        if (!Message.checkRecipientCell(recipient)) {
            JOptionPane.showMessageDialog(null, "Cell phone number is incorrectly formatted.");
            return false;
        }

        String content = JOptionPane.showInputDialog("Enter your message (max 250 characters):");

        // Validate message length
        String feedback = Message.validateMessageLength(content);
        JOptionPane.showMessageDialog(null, feedback);

        if (!feedback.equals("Message ready to send.")) {
            return false;
        }

        Message msg = new Message(recipient, content, msgNum);

        // Updated to show message number in the hash dialog
        JOptionPane.showMessageDialog(null, "Message #" + (msgNum + 1)
                + "\nMessage Hash: " + msg.getMessageHash());

        // Options to Send / Store / Discard
        String[] options = {"Send", "Discard", "Store"};
        int action = JOptionPane.showOptionDialog(null, "What would you like to do with this message?",
                "Message Options", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (action == 0) {
            if (sentCount < sentMessages.length) {
                sentMessages[sentCount] = msg;
                sentCount++;
                JOptionPane.showMessageDialog(null, msg.printDetails());
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Message storage full.");
                return false;
            }
        } else if (action == 1) {
            JOptionPane.showMessageDialog(null, "Message discarded.");
            return true;
        } else if (action == 2) {
            msg.storeMessageToJson();
            JOptionPane.showMessageDialog(null, "Message successfully stored.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid option.");
            return false;
        }
    }

    // === VALIDATION HELPERS ===
    public static String getValidUsername() {
        String username;
        while (true) {
            username = JOptionPane.showInputDialog("Enter username (must contain _ and be ≤ 5 chars):");
            if (QuickChatUser.checkUserName(username)) {
                return username;
            }
            JOptionPane.showMessageDialog(null, "Invalid username format.");
        }
    }

    public static String getValidPassword() {
        String password;
        while (true) {
            password = JOptionPane.showInputDialog("Enter password (8+ chars, 1 capital, 1 number, 1 special):");
            if (QuickChatUser.checkPasswordComplexity(password)) {
                return password;
            }
            JOptionPane.showMessageDialog(null, "Invalid password format.");
        }
    }

    public static String getValidCellphone() {
        String phone;
        while (true) {
            phone = JOptionPane.showInputDialog("Enter cellphone number (+27XXXXXXXXX):");
            if (QuickChatUser.checkCellPhoneNumber(phone)) {
                return phone;
            }
            JOptionPane.showMessageDialog(null, "Invalid cellphone number.");
        }
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

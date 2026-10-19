/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 ** Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.mycompany.MessagePart2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents one chat message and keeps track of every message
 * sent, stored or disregarded while the program is running.
 */
public class MessagePart2 {

    
    private static final List<MessagePart2> sentMessages = new ArrayList<>();
    private static final List<MessagePart2> storedMessages = new ArrayList<>();
    private static final List<MessagePart2> disregardedMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;

    private static final String FILE_NAME = "messages.txt";

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;

    
    public MessagePart2(int messageNumber, String recipient, String messageText) {
        this(generateMessageID(), messageNumber, recipient, messageText);
    }

   
    public MessagePart2(String messageID, int messageNumber, String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    
    public static String generateMessageID() {
        long number = (long) (Math.random() * 10_000_000_000L);
        return String.format("%010d", number);
    }

    
    public boolean checkMessageID() {
        return messageID != null && messageID.matches("\\d{1,10}");
    }

    
    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("^\\+\\d{1,3}\\d{1,10}$")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

 
    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }
        int excess = messageText.length() - 250;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }

    
    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2) {
            throw new IllegalArgumentException(
                    "Message ID must contain at least two characters.");
        }

        if (messageText == null || messageText.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Message text cannot be empty.");
        }
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^A-Za-z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        String prefix = messageID.substring(0, 2);
        return (prefix + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    
    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2:
                disregardedMessages.add(this);
                return "Press 0 to delete the message.";
            case 3:
                storedMessages.add(this);
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    
    public static String printMessages() {
        StringBuilder output = new StringBuilder();
        for (MessagePart2 Message : sentMessages) {
            output.append(Message.getDetails()).append("\n");
        }
        return output.toString();
    }

    
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    
    public static void storeMessage() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (MessagePart2 msg : storedMessages) {
                writer.println(msg.getDetails());
                writer.println("-----------------------------------");
            }
        } catch (IOException e) {
            System.out.println("Could not save messages: " + e.getMessage());
        }
    }

    
    public String getDetails() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    
    public static void resetAll() {
        sentMessages.clear();
        storedMessages.clear();
        disregardedMessages.clear();
        totalMessagesSent = 0;
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
}
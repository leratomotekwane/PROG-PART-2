/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchart;

import java.util.Random;
import java.util.ArrayList;

public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;
    private static ArrayList<Message> sentMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;
    
    public Message(String recipient, String message, int messageNumber) {
        this.messageID = generateMessageID();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
    }
    
    private String generateMessageID() {
        Random rand = new Random();
        long id = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }
    
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }
    
    public String checkRecipientCell() {
        if (recipient.startsWith("+27") && recipient.length() == 12) {
            String numbers = recipient.substring(3);
            if (numbers.matches("\\d+")) {
                return "Cell phone number successfully captured.";
            }
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }
    
    public String createMessageHash() {
        String first2 = messageID.substring(0, 2);
        String[] words = message.trim().split("\\s+");
        if (words.length == 0) {
            return first2 + ":" + messageNumber + ":EMPTY";
        }
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        return first2 + ":" + messageNumber + ":" + firstWord + lastWord;
    }
    
    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }
    
    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== All Sent Messages =====\n");
        for (Message m : sentMessages) {
            sb.append("Message ID: ").append(m.messageID).append("\n");
            sb.append("Message Hash: ").append(m.messageHash).append("\n");
            sb.append("Recipient: ").append(m.recipient).append("\n");
            sb.append("Message: ").append(m.message).append("\n");
            sb.append("-----------------------------\n");
        }
        return sb.toString();
    }
    
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
    
    public void storeMessage() {
        try {
            java.io.FileWriter writer = new java.io.FileWriter("messages.json", true);
            writer.write("{\n");
            writer.write("  \"messageID\": \"" + messageID + "\",\n");
            writer.write("  \"messageNumber\": " + messageNumber + ",\n");
            writer.write("  \"recipient\": \"" + recipient + "\",\n");
            writer.write("  \"message\": \"" + message + "\",\n");
            writer.write("  \"messageHash\": \"" + messageHash + "\"\n");
            writer.write("},\n");
            writer.close();
            System.out.println("Message stored to messages.json");
        } catch (Exception e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }
    
    public String printMessage() {
    return "Message ID: " + messageID + 
           ", Message Hash: " + messageHash + 
           ", Recipient: " + recipient + 
           ", Message: " + message;
}
    
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public int getMessageNumber() { return messageNumber; }
}
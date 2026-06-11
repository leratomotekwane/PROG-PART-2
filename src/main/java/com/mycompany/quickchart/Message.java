/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchart;

import java.util.Random;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private static int totalMessagesSent = 0;
    
    private static ArrayList<String> sentMessagesArray = new ArrayList<>();
    private static ArrayList<String> disregardedMessagesArray = new ArrayList<>();
    private static ArrayList<String> storedMessagesArray = new ArrayList<>();
    private static ArrayList<String> storedHashesArray = new ArrayList<>();
    private static ArrayList<String> storedIDsArray = new ArrayList<>();
    private static ArrayList<String> storedRecipientsArray = new ArrayList<>();
    
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }
    
    public boolean checkMessageLength() {
        if (messageText.length() <= 250) {
            System.out.println("Message ready to send.");
            return true;
        } else {
            int excess = messageText.length() - 250;
            System.out.println("Message exceeds 250 characters by " + excess + "; please reduce the size.");
            return false;
        }
    }
    
    public String checkRecipientCell() {
        if (recipient != null && recipient.startsWith("+27") && recipient.length() == 12) {
            String numbers = recipient.substring(3);
            if (numbers.matches("\\d+")) {
                return "Cell phone number successfully captured.";
            }
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }
    
    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String msgNum = String.valueOf(messageNumber);
        String[] words = messageText.trim().split("\\s+");
        
        if (words.length == 0 || messageText.trim().isEmpty()) {
            return firstTwo + ":" + msgNum + ":EMPTY";
        }
        
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        
        return firstTwo + ":" + msgNum + ":" + firstWord + lastWord;
    }
    
    private String generateMessageID() {
        Random rand = new Random();
        long id = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }
    
    public String sentMessage(int choice) {
        boolean isValidPhone = recipient != null && recipient.startsWith("+27") && recipient.length() == 12;
        
        if (choice == 1) {
            if (isValidPhone && messageText != null && !messageText.trim().isEmpty()) {
                totalMessagesSent++;
                sentMessagesArray.add(messageText);
                return "Message successfully sent.";
            } else {
                return "Cannot send message: Invalid phone number or empty message!";
            }
        } else if (choice == 0) {
            disregardedMessagesArray.add(messageText);
            return "Press 0 to delete the message. Message deleted.";
        } else if (choice == 3) {
            if (messageText != null && !messageText.trim().isEmpty()) {
                storeMessageInJSON();
                storedMessagesArray.add(messageText);
                storedHashesArray.add(messageHash);
                storedIDsArray.add(messageID);
                storedRecipientsArray.add(recipient);
                return "Message successfully stored.";
            } else {
                return "Cannot store empty message!";
            }
        } else {
            return "Invalid option.";
        }
    }
    
    public void storeMessageInJSON() {
        try {
            FileWriter writer = new FileWriter("messages.json", true);
            writer.write(messageID + "|" + messageNumber + "|" + recipient + "|" + messageText + "|" + messageHash + "\n");
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
               ", Message: " + messageText;
    }
    
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
    
    public static void loadStoredMessagesFromJSON() {
        storedMessagesArray.clear();
        storedHashesArray.clear();
        storedIDsArray.clear();
        storedRecipientsArray.clear();
        
        File file = new File("messages.json");
        if (!file.exists()) {
            return;
        }
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader("messages.json"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    storedIDsArray.add(parts[0]);
                    storedRecipientsArray.add(parts[2]);
                    storedMessagesArray.add(parts[3]);
                    storedHashesArray.add(parts[4]);
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    public static void displayAllStoredMessages() {
        loadStoredMessagesFromJSON();
        
        if (storedMessagesArray.isEmpty()) {
            System.out.println("No stored messages.");
            return;
        }
        
        System.out.println("\n=== STORED MESSAGES ===");
        for (int i = 0; i < storedMessagesArray.size(); i++) {
            System.out.println("Message: " + storedMessagesArray.get(i));
            System.out.println("Recipient: " + storedRecipientsArray.get(i));
            System.out.println("Hash: " + storedHashesArray.get(i));
            System.out.println("ID: " + storedIDsArray.get(i));
            System.out.println("-------------------------------------");
        }
    }
    
    public static void displayLongestStoredMessage() {
        loadStoredMessagesFromJSON();
        
        if (storedMessagesArray.isEmpty()) {
            System.out.println("No stored messages.");
            return;
        }
        
        String longest = storedMessagesArray.get(0);
        for (String msg : storedMessagesArray) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }
        
        System.out.println("\n=== LONGEST STORED MESSAGE ===");
        System.out.println("Message: " + longest);
        System.out.println("Length: " + longest.length() + " characters");
    }
    
    public static void searchMessageByID(String id) {
        loadStoredMessagesFromJSON();
        
        boolean found = false;
        for (int i = 0; i < storedIDsArray.size(); i++) {
            if (storedIDsArray.get(i).equals(id)) {
                System.out.println("\n=== MESSAGE FOUND ===");
                System.out.println("Recipient: " + storedRecipientsArray.get(i));
                System.out.println("Message: " + storedMessagesArray.get(i));
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No message found with ID: " + id);
        }
    }
    
    public static void searchMessagesByRecipient(String phone) {
        loadStoredMessagesFromJSON();
        
        boolean found = false;
        System.out.println("\n=== MESSAGES FOR RECIPIENT: " + phone + " ===");
        for (int i = 0; i < storedRecipientsArray.size(); i++) {
            if (storedRecipientsArray.get(i).equals(phone)) {
                System.out.println("Message: " + storedMessagesArray.get(i));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No messages found for: " + phone);
        }
    }
    
    public static void deleteMessageByHash(String hash) {
        loadStoredMessagesFromJSON();
        
        for (int i = 0; i < storedHashesArray.size(); i++) {
            if (storedHashesArray.get(i).equals(hash)) {
                System.out.println("Message: \"" + storedMessagesArray.get(i) + "\" successfully deleted.");
                storedMessagesArray.remove(i);
                storedHashesArray.remove(i);
                storedIDsArray.remove(i);
                storedRecipientsArray.remove(i);
                
                try {
                    FileWriter writer = new FileWriter("messages.json");
                    for (int j = 0; j < storedMessagesArray.size(); j++) {
                        writer.write(storedIDsArray.get(j) + "|" + (j+1) + "|" + storedRecipientsArray.get(j) + "|" + storedMessagesArray.get(j) + "|" + storedHashesArray.get(j) + "\n");
                    }
                    writer.close();
                } catch (Exception e) {
                    System.out.println("Error updating file: " + e.getMessage());
                }
                return;
            }
        }
        System.out.println("No message found with hash: " + hash);
    }
    
    public static void displayFullReport() {
        loadStoredMessagesFromJSON();
        
        if (storedMessagesArray.isEmpty()) {
            System.out.println("No stored messages to display.");
            return;
        }
        
        System.out.println("\n=== FULL STORED MESSAGES REPORT ===");
   
        for (int i = 0; i < storedMessagesArray.size(); i++) {
            System.out.println("Message Hash: " + storedHashesArray.get(i));
            System.out.println("Message ID: " + storedIDsArray.get(i));
            System.out.println("Recipient: +" + storedRecipientsArray.get(i));
            System.out.println("Message: " + storedMessagesArray.get(i));
           
        }
    }
    
    public static void populateTestData() {
        sentMessagesArray.clear();
        disregardedMessagesArray.clear();
        storedMessagesArray.clear();
        storedHashesArray.clear();
        storedIDsArray.clear();
        storedRecipientsArray.clear();
        totalMessagesSent = 0;
        
        try {
            new FileWriter("messages.json", false).close();
        } catch (Exception e) {}
        
        Message m1 = new Message(1, "+27834557896", "Did you get the cake?");
        m1.sentMessage(1);
        
        Message m2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        m2.sentMessage(3);
        
        Message m3 = new Message(3, "+27834484567", "Yohoooo, I am at your gate.");
        m3.sentMessage(0);
        
        Message m4 = new Message(4, "+27838884567", "It is dinner time !");
        m4.sentMessage(1);
        
        Message m5 = new Message(5, "+27838884567", "Ok, I am leaving without you.");
        m5.sentMessage(3);
    }
    
    public static ArrayList<String> getSentMessagesArray() { return sentMessagesArray; }
    public static ArrayList<String> getStoredMessagesArray() { return storedMessagesArray; }
    public static ArrayList<String> getStoredHashesArray() { return storedHashesArray; }
    public static ArrayList<String> getStoredIDsArray() { return storedIDsArray; }
    public static ArrayList<String> getStoredRecipientsArray() { return storedRecipientsArray; }
    public static ArrayList<String> getDisregardedMessagesArray() { return disregardedMessagesArray; }
    
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    
}

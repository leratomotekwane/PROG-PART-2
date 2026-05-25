/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchart;

import java.util.Scanner;

public class QuickChart {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        System.out.println("=== REGISTRATION ===");
        System.out.print("Enter first name: ");
        String firstName = scan.next();
        
        System.out.print("Enter last name: ");
        String lastName = scan.next();
        
        System.out.print("Enter username: ");
        String username = scan.next();
        
        System.out.print("Enter password: ");
        String password = scan.next();
        
        System.out.print("Enter cellphone (+27...): ");
        String cellphone = scan.next();
        
        String result = Login.registerUser(firstName, lastName, username, password, cellphone);
        System.out.println(result);
        
        if(result.contains("not correctly")) {
            System.out.println("Please fix your errors and try again.");
            return;
        }
        
        System.out.println("\n=== LOGIN ===");
        int attempts = 0;
        int maxAttempts = 3;
        boolean loggedIn = false;
        
        while (!loggedIn && attempts < maxAttempts) {
            System.out.print("Enter username to Login: ");
            String loginUsername = scan.next();
            
            System.out.print("Enter password to Login: ");
            String loginPassword = scan.next();
            
            boolean loginresult = Login.loginUser(loginUsername, loginPassword);
            System.out.println(Login.returnLoginStatus(loginresult));
            
            if (loginresult) {
                loggedIn = true;
            } else {
                attempts++;
                int remaining = maxAttempts - attempts;
                if (remaining > 0) {
                    System.out.println("You have " + remaining + " attempt(s) remaining.\n");
                } else {
                    System.out.println("Too many failed attempts. Exiting.");
                    return;
                }
            }
        }
        
        if (loggedIn) {
            showMessagingMenu(scan);
        }
    }
    
    public static void showMessagingMenu(Scanner scan) {
        System.out.println("\n=== Welcome to QuickChat! ===");
        
        System.out.print("How many messages do you want to send today? ");
        int numMessages = scan.nextInt();
        scan.nextLine();
        
        int messageCounter = 0;
        boolean running = true;
        
        while (running) {
            System.out.println("\n=== QuickChat Menu ===");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");
            
            String choice = scan.nextLine().trim();
            
            switch (choice) {
                case "1":
                    if (messageCounter >= numMessages) {
                        System.out.println("You have already sent all " + numMessages + " message(s).");
                        break;
                    }
                    
                    while (messageCounter < numMessages) {
                        messageCounter++;
                        System.out.println("\n--- Message " + messageCounter + " of " + numMessages + " ---");
                        
                        String recipient = "";
                        while (true) {
                            System.out.print("Enter recipient phone number (+27 and 12 digits): ");
                            recipient = scan.nextLine().trim();
                            if (recipient.startsWith("+27") && recipient.length() == 12) {
                                System.out.println("Cell phone number successfully captured.");
                                break;
                            } else {
                                System.out.println("Cell phone number is incorrectly formatted. Please use +27 followed by 9 digits.");
                            }
                        }
                        
                        String messageText = "";
                        while (true) {
                            System.out.print("Enter your message (max 250 characters): ");
                            messageText = scan.nextLine();
                            if (messageText.length() <= 250) {
                                System.out.println("Message ready to send.");
                                break;
                            } else {
                                int excess = messageText.length() - 250;
                                System.out.println("Message exceeds 250 characters by " + excess + "; please reduce the size.");
                            }
                        }
                        
                        Message msg = new Message(recipient, messageText, messageCounter);
                        
                        System.out.println("\nMessage ID generated: " + msg.getMessageID());
                        System.out.println("Message Hash: " + msg.getMessageHash());
                        
                        System.out.println("\nWhat would you like to do with this message?");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message (Delete)");
                        System.out.println("3) Store Message to send later");
                        System.out.print("Choose (1/2/3): ");
                        
                        int sendChoice = Integer.parseInt(scan.nextLine().trim());
                        String sendResult = msg.sentMessage(sendChoice);
                        System.out.println(sendResult);
                        
                        if (sendResult.equals("Message successfully sent.")) {
                            System.out.println("\n=== Message Details ===");
                            System.out.println(msg.printMessage());
                            System.out.println("Message COUNTED as sent!\n");
                        } else if (sendResult.equals("Message successfully stored.")) {
                            System.out.println("Message saved to storage (not counted).\n");
                        } else if (sendResult.contains("delete")) {
                            System.out.println("Message deleted (not counted).\n");
                        }
                    }
                    
                    System.out.println(Message.printMessages());
                    System.out.println("\n=== SUMMARY ===");
                    System.out.println("Total messages successfully sent: " + Message.returnTotalMessages());
                    break;
                    
                case "2":
                    System.out.println("Coming Soon. This feature is still in development.");
                    break;
                    
                case "3":
                    System.out.println("Goodbye! Thanks for using QuickChat.");
                    running = false;
                    break;
                    
                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }
}
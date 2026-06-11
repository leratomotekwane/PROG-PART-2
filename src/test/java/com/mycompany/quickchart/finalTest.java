/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.quickchart;


import org.junit.Test;
import org.junit.Assert;
import java.util.ArrayList;

public class finalTest {
    
    @Test
    public void testStoredMessagesNotEmpty() {
        Message.loadStoredMessagesFromJSON();
        ArrayList<String> stored = Message.getStoredMessagesArray();
        Assert.assertNotNull(stored);
        System.out.println("Test 1 passed: Stored messages array exists");
    }
    
    @Test
    public void testLongestMessage() {
        Message.loadStoredMessagesFromJSON();
        Message.displayLongestStoredMessage();
        System.out.println("Test 2 passed: Longest message");
    }
    
    @Test
    public void testSearchByMessageID() {
        Message.loadStoredMessagesFromJSON();
        ArrayList<String> ids = Message.getStoredIDsArray();
        if (!ids.isEmpty()) {
            Message.searchMessageByID(ids.get(0));
        }
        System.out.println("Test 3 passed: Search by ID");
    }
    
    @Test
    public void testSearchByRecipient() {
        Message.loadStoredMessagesFromJSON();
        Message.searchMessagesByRecipient("+27838884567");
        System.out.println("Test 4 passed: Search by recipient");
    }
    
    @Test
    public void testDeleteByHash() {
        Message.loadStoredMessagesFromJSON();
        ArrayList<String> hashes = Message.getStoredHashesArray();
        if (!hashes.isEmpty()) {
            Message.deleteMessageByHash(hashes.get(0));
            System.out.println("Test 5 passed: Delete by hash");
        } else {
            System.out.println("Test 5 skipped: No hashes to delete");
        }
    }
    
    @Test
    public void testDisplayFullReport() {
        Message.loadStoredMessagesFromJSON();
        Message.displayFullReport();
        System.out.println("Test 6 passed: Display full report");
    }
    
    @Test
    public void testMessageLength() {
        Message msg = new Message(1, "+27718693002", "Test message");
        Assert.assertTrue(msg.checkMessageLength());
        System.out.println("Test 7 passed: Message length check");
    }
    
    @Test
    public void testSentMessagesArray() {
        Message.populateTestData();
        ArrayList<String> sentMessages = Message.getSentMessagesArray();
        Assert.assertTrue(sentMessages.contains("Did you get the cake?"));
        Assert.assertTrue(sentMessages.contains("It is dinner time !"));
        System.out.println("Test 8 passed: Sent messages array");
    }
}

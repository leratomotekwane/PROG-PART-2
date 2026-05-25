/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.quickchart;

import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        boolean result = msg.getMessage().length() <= 250;
        assertTrue("Message should be 250 characters or less", result);
        System.out.println("Message ready to send.");
    }
    
    @Test
    public void testMessageLengthFailure() {
        String longMessage = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        boolean tooLong = longMessage.length() > 250;
        int excess = longMessage.length() - 250;
        assertTrue("Message exceeds 250 characters", tooLong);
        System.out.println("Message exceeds 250 characters by " + excess + "; please reduce the size.");
    }
    
    @Test
    public void testRecipientCellSuccess() {
        Message msg = new Message("+27718693002", "Hello", 1);
        String result = msg.checkRecipientCell();
        assertEquals("Cell phone number successfully captured.", result);
    }
    
    @Test
    public void testRecipientCellFailure() {
        Message msg = new Message("08575975889", "Hello", 1);
        String result = msg.checkRecipientCell();
        assertTrue(result.contains("incorrectly formatted"));
    }
    
    @Test
    public void testMessageHashCorrect() {
        Message msg = new Message("+27718693002", "Hi Mike dinner", 1);
        String hash = msg.getMessageHash();
        assertNotNull(hash);
        assertTrue(hash.contains(":"));
        System.out.println("Generated Hash: " + hash);
    }
    
    @Test
    public void testMessageIDIs10Digits() {
        Message msg = new Message("+27718693002", "Hello", 1);
        String id = msg.getMessageID();
        assertEquals(10, id.length());
        System.out.println("Message ID generated: " + id);
    }
    
    @Test
    public void testSentMessageSend() {
        Message msg = new Message("+27718693002", "Hi Keegan", 1);
        String result = msg.sentMessage(1);
        assertEquals("Message successfully sent.", result);
    }
    
    @Test
    public void testSentMessageDisregard() {
        Message msg = new Message("+27718693002", "Hi Keegan", 1);
        String result = msg.sentMessage(2);
        assertEquals("Press 0 to delete the message.", result);
    }
    
    @Test
    public void testSentMessageStore() {
        Message msg = new Message("+27718693002", "Hi Keegan", 1);
        String result = msg.sentMessage(3);
        assertEquals("Message successfully stored.", result);
    }
}

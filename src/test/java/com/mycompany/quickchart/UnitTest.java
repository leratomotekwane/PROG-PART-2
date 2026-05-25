/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.quickchart;

import org.junit.Test;
import org.junit.Assert;


public class UnitTest {
    
 
    @Test
    public void testCheckUserNameCorrect() {
        Assert.assertEquals(true, Login.checkUserName("kyl_1"));
    }
    
    @Test
    public void testCheckUserNameIncorrect() {
        Assert.assertEquals(false, Login.checkUserName("kyle!!!!!"));
    }
    
    @Test
    public void testCheckPasswordComplexityCorrect() {
        Assert.assertEquals(true, Login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }
    
    @Test
    public void testCheckPasswordComplexityIncorrect() {
        Assert.assertEquals(false, Login.checkPasswordComplexity("password"));
    }
    
    // ========== CELLPHONE TESTS ==========
    @Test
    public void testCheckCellPhoneNumberCorrect() {
        Assert.assertEquals(true, Login.checkCellPhoneNumber("+27838968976"));
    }
    
    @Test
    public void testCheckCellPhoneNumberIncorrect() {
        Assert.assertEquals(false, Login.checkCellPhoneNumber("08966553"));
    }
    
    // ========== REGISTER TEST ==========

    @Test
    public void testRegisterUser() {
        String result = Login.registerUser("Lerato", "le_01", "Ch&&sec@ke99!", "+27684578600", "Tshabalala");
        Assert.assertEquals(true, result.contains("Welcome"));
    }
    
    // ========== LOGIN TESTS ==========
    @Test
    public void testLoginUserCorrect() {
        Login.registerUser("Lerato", "le_01", "Ch&&sec@ke99!", "+27684578600", "Tshabalala");
        Assert.assertEquals(true, Login.loginUser("le_01", "Ch&&sec@ke99!"));
    }
    
    @Test
    public void testLoginUserIncorrect() {
        Login.registerUser("Lerato", "le_01", "Ch&&sec@ke99!", "+27684578600", "Tshabalala");
        Assert.assertEquals(false, Login.loginUser("wrong", "wrong"));
    }
    
    // ========== LOGIN STATUS TESTS ==========
    @Test
    public void testReturnLoginStatusSuccess() {
        Login.registerUser("Lerato", "le_01", "Ch&&sec@ke99!", "+27684578600", "Tshabalala");
        String result = Login.returnLoginStatus(true);
        Assert.assertEquals(true, result.contains("Welcome"));
    }
    
    @Test
    public void testReturnLoginStatusFailed() {
        String result = Login.returnLoginStatus(false);
        Assert.assertEquals(true, result.contains("failed"));
    }
}

package com.mycompany.quickchart;


class Login {
    static String regUsername = "";
    static String regPassword = "";
    static String regCellphone = "";
    static String regFirstname = "";
    static String regLastname = "";
    static boolean isLoggedIn = false;
 
    static boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }
    
    static boolean checkPasswordComplexity(String password) {
        if(password.length() < 8) return false;
        if(password.equals(password.toLowerCase())) return false;
        if(!password.matches(".*[!@#$%^&*()_+].*")) return false;
        if(!password.matches(".*\\d.*")) return false;
        return true;
    }
    
    static boolean checkCellPhoneNumber(String cellphone) {
        if(cellphone.startsWith("+27") && cellphone.length() == 12) {
            String number = cellphone.substring(3);
            if(number.matches("\\d+")) {
                return true;
            }
        }
        return false;
    }
    
    static String registerUser(String firstname, String lastname, String username, String password, String cellphone) {
        String error = "";
        
        if(!checkUserName(username)) {
            error += "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters length.\n";
        }
        if(!checkPasswordComplexity(password)) {
            error += "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number and a special character.\n";
        }
        if(!checkCellPhoneNumber(cellphone)) {
            error += "Cellphone is not correctly formatted or does not contain international code.\n";
        }
        
        if(!error.isEmpty()) {
            return error;
        }
        
        regUsername = username;
        regPassword = password;
        regFirstname = firstname;
        regLastname = lastname;
        regCellphone = cellphone;
        
        return "Username successfully captured\nPassword successfully captured\nCellphone successfully captured\nWelcome " + firstname + " " + lastname + " it is great to see you.";
    }
 
    static boolean loginUser(String username, String password) {
        boolean success = username.equals(regUsername) && password.equals(regPassword);
        isLoggedIn = success;
        return success;
    }
   
    static String returnLoginStatus(boolean logged) {
        if(logged) {
            return "Welcome back " + regFirstname + " " + regLastname + " it is great to see you again";
        } else {
            return "A failed login";
        }
    }
}

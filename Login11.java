/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.messagepart2;

/**
 *
 * @author Student
 */
public class Login11 {

private String firstName;
private String lastName;
private String username;
private String password;
private String cellPhoneNumber;

    // Constructor
    public Login11(String firstName, String lastName, String username,String password, String cellPhoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // Checks if the username contains an underscore
    // and is no more than five characters long
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    // Checks whether the password meets the complexity requirements
    public boolean checkPasswordComplexity() {

        if (password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {

            char character = password.charAt(i);

            if (Character.isUpperCase(character)) {
                hasCapital = true;
            }

            if (Character.isDigit(character)) {
                hasNumber = true;
            }

            if (!Character.isLetterOrDigit(character)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    // Checks whether the cellphone number contains
    // an international country code and is no more than
    // ten digits after the country code
    public boolean checkCellPhoneNumber() {

        String regex = "^\\+27\\d{9}$";

        return cellPhoneNumber.matches(regex);
    }

    // Registers the user and returns the appropriate message
    public String registerUser() {

        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell phone number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }

        return "User successfully registered.";
    }

    // Checks whether the entered username and password
    // match the registered details
    public boolean loginUser(String enteredUsername, String enteredPassword) {

        return enteredUsername.equals(username)
                && enteredPassword.equals(password);
    }

    // Returns the login status message
    public String returnLoginStatus(boolean loginStatus) {

        if (loginStatus) {
            return "Welcome " + firstName + " " + lastName
                    + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    }


package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientValidator {
    private static final int CNP_LENGTH = 12;

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    private final Client client;

    public ClientValidator(Client client) {
        this.client = client;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateName(client.getName());
        validateCardNumber(client.getCardNumber());
        validateCNP(client.getCNP());
        validateAddress(client.getAddress());
        return errors.isEmpty();
    }

    private void validateCardNumber(String cardNumber) {
        if (cardNumber.isEmpty()) {
            errors.add("Please Input CardNumber!");
        }
        else {
            if (containsSpecialCharacter(cardNumber)) {
                errors.add("CardNumber must not contain special characters!");
            }
            if (!containsDigit(cardNumber)) {
                errors.add("CardNumber must contain at least one number!");
            }
        }
    }

    private void validateName(String name) {
        if (name.isEmpty()) {
            errors.add("Please input Name!");
        }
        else {
            if (containsSpecialCharacter(name)) {
                errors.add("Name must not contain special characters!");
            }
            if (containsDigit(name)) {
                errors.add("Name must not contain numbers!");
            }
        }
    }

    private void validateCNP(String CNP) {
        if (CNP.isEmpty()) {
            errors.add("Please input CNP!");
        }
        else {
            if (CNP.length() > CNP_LENGTH) {
                errors.add("CNP is not the appropriate size!");
            }
            if (containsSpecialCharacter(CNP)) {
                errors.add("CNP must not contain special characters!");
            }
            if (!containsDigit(CNP)) {
                errors.add("CNP must contain at least one number!");
            }
        }
    }

    private void validateAddress(String address) {
        if (address.isEmpty()) {
            errors.add("Please input Address!");
        }
    }

    private boolean containsSpecialCharacter(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(s);
        return m.find();
    }

    private boolean containsDigit(String s) {
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    return true;
                }
            }
        }
        return false;
    }
}

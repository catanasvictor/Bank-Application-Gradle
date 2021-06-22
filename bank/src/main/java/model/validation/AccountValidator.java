package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountValidator {


    private static final int CNP_LENGTH = 12;
    private static final String DATE_REGEX ="^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    private final Account account;

    public AccountValidator(Account account) {
        this.account = account;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateType(account.getType());
        validateTotal(account.getTotal());
        validateDateOfCreation(account.getDateOfCreation());
        return errors.isEmpty();
    }

    private void validateType(String type) {
        if (type.isEmpty()) {
            errors.add("Please Input Type!");
        }
        else {
            if (containsSpecialCharacter(type)) {
                errors.add("Type must not contain special characters!");
            }
            if (containsDigit(type)) {
                errors.add("Type must not contain numbers!");
            }
        }
    }

    private void validateTotal(int total) {
        if (total < 0 ) {
            errors.add("Total must be a positive value.");
        }
    }

    private void validateDateOfCreation(String dateOfCreation) {
        if (dateOfCreation.isEmpty()) {
            errors.add("Please Input Date of creation!");
        }
        else {
            if (!Pattern.compile(DATE_REGEX).matcher(dateOfCreation).matches()) {
                errors.add("Invalid Date!");
            }
            if (!containsDigit(dateOfCreation)) {
                errors.add("Date of creation must contain numbers!");
            }
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

package service.employee;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.EntityNotFoundException;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import service.user.AuthenticationServiceMySQL;
import java.util.Collections;
import static database.Constants.Roles.EMPLOYEE;


public class EmployeeServiceMySQL implements EmployeeService{

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public EmployeeServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> register(String username, String password) {
        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(employeeRole))
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(AuthenticationServiceMySQL.encodePassword(password));
            userRegisterNotification.setResult(userRepository.save(user));
        }
        return userRegisterNotification;
    }

    @Override
    public Notification<Boolean> update(String employeeId,String username, String password)  {
        User oldUser = null;
        if (!employeeId.isEmpty()) {
        try {
            oldUser = userRepository.findUserById(Long.parseLong(employeeId));
        } catch (EntityNotFoundException entityNotFoundException) {
            entityNotFoundException.printStackTrace();
        }
        }

        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        User newUser = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(employeeRole))
                .build();

        UserValidator userValidator = new UserValidator(newUser);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userUpdateNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userUpdateNotification::addError);
            userUpdateNotification.setResult(Boolean.FALSE);
        } else {
            newUser.setPassword(AuthenticationServiceMySQL.encodePassword(password));
            userUpdateNotification.setResult(userRepository.update(oldUser,newUser));
        }
        return userUpdateNotification;
    }

    @Override
    public void view() {
        userRepository.view();
    }

    @Override
    public Notification<Boolean> delete(String employeeId)  {
        User userToDelete = null;
        if (!employeeId.isEmpty()) {
            try {
                userToDelete = userRepository.findUserById(Long.parseLong(employeeId));
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }
        }
        Notification<Boolean> userDeleteNotification = new Notification<>();

        if (userToDelete == null) {
            userDeleteNotification.setResult(Boolean.FALSE);
        } else {
            userDeleteNotification.setResult(userRepository.delete(userToDelete));
        }
        return userDeleteNotification;
    }
}

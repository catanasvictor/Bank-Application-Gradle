package repository.user;

import model.User;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;


public interface UserRepository {

    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException;

    User findUserById(Long id) throws EntityNotFoundException;

    boolean save(User user);

    boolean update(User oldUser,User newUser);

    void view();

    boolean delete(User user);

    void removeAll();

}
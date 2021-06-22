package service.employee;

import model.validation.Notification;

public interface EmployeeService {

    Notification<Boolean> register(String username, String password);

    Notification<Boolean> update(String employeeId,String username, String password);

    void view();

    Notification<Boolean> delete(String employeeId);

}

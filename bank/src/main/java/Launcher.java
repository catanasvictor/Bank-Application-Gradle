import controller.LoginController;
import view.LoginView;


public class Launcher {

    public static void main(String[] args) {
        ComponentFactory componentFactory = ComponentFactory.instance(false);
        new LoginController(new LoginView(), componentFactory.getAuthenticationService(), componentFactory.getClientService(), componentFactory.getAccountService(), componentFactory.getEmployeeService());
    }

}
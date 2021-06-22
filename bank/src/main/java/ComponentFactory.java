import database.DBConnectionFactory;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.employee.EmployeeService;
import service.employee.EmployeeServiceMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.sql.Connection;


public class ComponentFactory {

    private final AuthenticationService authenticationService;
    private final ClientService clientService;
    private final AccountService accountService;
    private final EmployeeService employeeService;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final RightsRolesRepository rightsRolesRepository;

    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.clientRepository = new ClientRepositoryMySQL(connection);
        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.employeeService = new EmployeeServiceMySQL(userRepository, rightsRolesRepository);
        this.clientService = new ClientServiceImpl(this.clientRepository);
        this.accountService = new AccountServiceImpl(this.accountRepository);

    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public UserRepository getUserRepository() { return userRepository; }

    public ClientRepository getClientRepository() { return clientRepository; }

    public AccountRepository getAccountRepository() { return accountRepository; }

    public RightsRolesRepository getRightsRolesRepository() { return rightsRolesRepository; }

    public ClientService getClientService() { return clientService; }

    public AccountService getAccountService() { return accountService; }
}
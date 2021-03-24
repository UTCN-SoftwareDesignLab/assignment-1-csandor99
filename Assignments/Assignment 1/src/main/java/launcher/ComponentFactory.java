package launcher;

import controller.AdminController;
import controller.EmployeeController;
import controller.LoginController;
import database.DatabaseConnectionFactory;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.activity.ActivityRepository;
import repository.activity.ActivityRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.activity.ActivityService;
import service.activity.ActivityServiceMySQL;
import service.admin.AdminService;
import service.admin.AdminServiceMySQL;
import service.authentication.AuthenticationService;
import service.authentication.AuthenticationServiceMySQL;
import service.employee.EmployeeService;
import service.employee.EmployeeServiceMySQL;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;

public class ComponentFactory {
    private final LoginView loginView;
    private final EmployeeView employeeView;
    private final AdminView adminView;
    private final LoginController loginController;
    private final EmployeeController employeeController;
    private final AdminController adminController;
    private final AuthenticationService authenticationService;
    private final EmployeeService employeeService;
    private final AdminService adminService;
    private final ActivityService activityService;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ActivityRepository activityRepository;

    private static ComponentFactory instance;

    public static ComponentFactory instance(boolean componentsForTest){
        if(instance == null){
            instance = new ComponentFactory(componentsForTest);
        }
        return instance;
    }
    public ComponentFactory(boolean componentsForTest){
        Connection connection = new DatabaseConnectionFactory().getConnectionWrapper(componentsForTest).getConnection();
        this.loginView = new LoginView();
        this.employeeView = new EmployeeView();
        this.adminView = new AdminView();
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
        accountRepository = new AccountRepositoryMySQL(connection);
        clientRepository = new ClientRepositoryMySQL(connection,accountRepository);
        activityRepository = new ActivityRepositoryMySQL(connection,userRepository);
        this.authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
        this.employeeService = new EmployeeServiceMySQL(clientRepository, accountRepository);
        this.activityService = new ActivityServiceMySQL(userRepository,activityRepository);
        this.employeeController = new EmployeeController(employeeView,employeeService, activityService);
        this.adminService = new AdminServiceMySQL(userRepository,rightsRolesRepository);
        this.adminController = new AdminController(adminView, adminService, activityService);
        this.loginController = new LoginController(loginView,employeeController,adminController,authenticationService);
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public EmployeeView getEmployeeView() {
        return employeeView;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public AdminView getAdminView() {
        return adminView;
    }
}

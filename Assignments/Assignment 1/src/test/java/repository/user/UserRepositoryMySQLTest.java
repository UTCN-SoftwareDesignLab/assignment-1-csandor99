package repository.user;

import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.User;
import model.builder.UserBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.account.AccountRepository;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import service.authentication.AuthenticationServiceMySQL;

import java.sql.SQLException;
import java.util.Collections;

import static database.Constants.Roles.EMPLOYEE;
import static org.junit.Assert.*;

public class UserRepositoryMySQLTest {

    private static UserRepository repository;
    private static RightsRolesRepository rightsRolesRepository;

    @BeforeClass
    public static void setupClass(){
        JDBConnectionWrapper connectionWrapper = DatabaseConnectionFactory.getConnectionWrapper(true);
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());
        repository = new UserRepositoryMySQL(connectionWrapper.getConnection(),rightsRolesRepository);
    }

    @Before
    public void setup() {
        repository.removeAll();
        User user = new UserBuilder().setUsername("test@test.com").setPassword("testtest1!").setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(EMPLOYEE))).build();
        User user1 = new UserBuilder().setUsername("test1@test1.com").setPassword("testtest11!").setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(EMPLOYEE))).build();
        repository.save(user);
        repository.save(user1);
    }

    @Test
    public void findByUsernameAndPassword() {
        Assert.assertEquals("test@test.com",repository.findByUsernameAndPassword("test@test.com","testtest1!").getResult().getUsername());
    }

    @Test
    public void save() {
        User user = new UserBuilder().setUsername("test2@test2.com").setPassword("testtest2!").setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(EMPLOYEE))).build();
        Assert.assertTrue(repository.save(user));
        Assert.assertEquals(3,repository.findAll().size());
    }

    @Test
    public void remove() {
        User user = new UserBuilder().setUsername("test@test.com").build();
        repository.remove(user);
        Assert.assertEquals(1,repository.findAll().size());
    }

    @Test
    public void update() throws SQLException {
        User user = repository.findByUsername("test@test.com");
        user.setPassword("testtest2!");
        repository.update(user);
        Assert.assertEquals("testtest2!",repository.findByUsername("test@test.com").getPassword());
    }

    @Test
    public void findAll() {
        User user = new UserBuilder().setUsername("test2@test2.com").setPassword("testtest2!").setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(EMPLOYEE))).build();
        repository.save(user);
        Assert.assertEquals(3,repository.findAll().size());
    }

    @Test
    public void findByUsername() throws SQLException {
        Assert.assertEquals("test@test.com", repository.findByUsername("test@test.com").getUsername());
    }

    @Test
    public void removeAll() {
        repository.removeAll();
        Assert.assertTrue(repository.findAll().isEmpty());
    }
}
package database;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class BootstrapperTest {

    @Test
    public void execute() throws SQLException {
        Bootstrapper bootstrapper =  new Bootstrapper();
        bootstrapper.execute();
    }
}
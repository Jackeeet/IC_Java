import dbClasses.DbConnectionUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnectionUtilsTest {

    Connection connection = null;
    Statement statement = null;

    @Test
    public void getConnection() throws SQLException {
        connection = DbConnectionUtils.getConnection();
        Assert.assertNotNull(connection);
        statement = connection.createStatement();
        Assert.assertNotNull(statement);
    }

    @After
    public void after() throws SQLException {
        if (statement != null) statement.close();
        if (connection != null) connection.close();
    }
}
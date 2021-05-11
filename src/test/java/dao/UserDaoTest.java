package dao;

import dbo.User;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserDaoTest {

    @Test
    public void getUserById() throws ParseException {
        int id = 2;
        User result = new UserDao().getUserById(2);
        Date dob = new SimpleDateFormat("yyyy-MM-dd").parse("1999-01-01");

        Assert.assertEquals(id, result.getId());
        Assert.assertEquals("Petr", result.getName());
        Assert.assertEquals("Petrov", result.getSurname());
        Assert.assertEquals(dob, result.getDateOfBirth());
    }

    @Test
    public void getAllUsersCount() {
        List<User> result = new UserDao().getAllUsers();
        Assert.assertEquals(4, result.size());
    }
}
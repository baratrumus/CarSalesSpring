package carsale.data;

import carsale.models.Users;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserTest {
    private static final DbTmp STORAGE = DbHibernate.getInstance();

    @Test
    @Ignore
    public void userAdd() {
       Users user = new Users("test", "test", "test@t.tu", "+7888566");
       int i = STORAGE.createUser(user);
       assertThat(user, is(this.STORAGE.getUserById(user.getId())));
    }
}

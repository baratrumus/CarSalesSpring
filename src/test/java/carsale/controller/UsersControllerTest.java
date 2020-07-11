package carsale.controller;

import org.junit.Before;
import org.junit.Test;
import carsale.config.ApplicationConfig;
import carsale.config.WebConfig;
import carsale.models.Users;
import carsale.service.UsersService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, WebConfig.class})
@WebAppConfiguration
public class UsersControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UsersController controller;

    @Mock
    private UsersService usersService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
    }


    @Test
    public void whenUsernamePasswordIsOkLoginRedirected() throws Exception {
        Users user = new Users("testUser", "1111", "1111", "pop@mail.com", "+79892131212");
        user.setId(15);
        when(usersService.save(user)).thenReturn(user);
        when(usersService.isLoginFree("testUser")).thenReturn(true);

        mockMvc.perform(post("/users/signup")
                .flashAttr("userForm", user))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?created=true"));
    }


    @Test
    public void whenPasswordDiffersErrorReturns() throws Exception {
        Users user = new Users("testUser", "1111", "4444", "pop@mail.com", "+79892131212");
        user.setId(15);
        when(usersService.save(user)).thenReturn(user);
        when(usersService.isLoginFree("testUser")).thenReturn(true);

        mockMvc.perform(post("/users/signup")
                .flashAttr("userForm", user))
                .andDo(print())
                .andExpect(model().attribute("passError", "passes are different"))
                .andExpect(view().name("signup"));
    }


    @Test
    public void whenAdminPageComesItsReturned() throws Exception {
        Users user = new Users("testUser", "1111", "4444", "pop@mail.com", "+79892131212");
        List<Users> usersList = List.of(user);
        when(usersService.getAll()).thenReturn(usersList);
        mockMvc.perform(get("/users/admin"))
                .andDo(print())
                .andExpect(model().attribute("listOfUsers", usersList))
                .andExpect(model().attribute("uEdited", "no"))
                .andExpect(view().name("admin"));
    }


    @Test
    public void whenUserToUpdateGetItsOk() throws Exception {
        Users user = new Users("testUser", "1111", "1111", "pop@mail.com", "+79892131212");
        user.setId(8);
        when(usersService.getUserById(8)).thenReturn(user);

        mockMvc.perform(get("/users/update/{id}", 8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", user))
                .andExpect(view().name("updateUser"));
    }


    @Test
    public void whenUserUpdateHeUpdated() throws Exception {
        Users baseUser = new Users("testUser", "1111", "1111", "pop@mail.com", "+79892131212");
        baseUser.setId(15);
        when(usersService.getUserById(15)).thenReturn(baseUser);
        when(usersService.isLoginFree("john")).thenReturn(true);

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("editedUserId", "15");
        requestParams.add("username", "john");
        requestParams.add("password", "2222");
        requestParams.add("passwordConfirm", "2222");
        requestParams.add("email", "pop@mail.com");
        requestParams.add("phone", "+733333333");
        requestParams.add("cameFromAdm", "");

        mockMvc.perform(post("/users/update")
                .params(requestParams))
                .andDo(print())
                .andExpect(model().attribute("user", baseUser))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }


    @Test
    public void whenUserToDeleteItsOk() throws Exception {
        Users user = new Users("testUser", "1111", "1111", "pop@mail.com", "+79892131212");
        user.setId(8);
        when(usersService.getUserById(8)).thenReturn(user);

        mockMvc.perform(post("/users/delete/{id}", 8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("userDeleted", "yes"))
                .andExpect(view().name("admin"));
    }

}
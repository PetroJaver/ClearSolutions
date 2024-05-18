package org.clearsolutions.restapi;

import org.clearsolutions.restapi.argumentsprovider.*;
import org.clearsolutions.restapi.dao.user.UserDaoDefault;
import org.clearsolutions.restapi.entity.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles(profiles = "default")
public class UsersControllerDefaultTest extends AbstractUsersControllerTest {
    @Autowired
    private UserDaoDefault userDaoDefault;

    @ParameterizedTest
    @ArgumentsSource(CreateUserArgumentsProvider.class)
    public void create(String path, HttpStatus expectedStatusCode) throws Exception {
        createRequest(path)
                .andExpect(status().is(expectedStatusCode.value()))
                .andExpect(content().json(getResponseBody(path)));
    }

    @ParameterizedTest
    @ArgumentsSource(PartialUpdateArgumentsProvider.class)
    public void partialUpdate(String path, HttpStatus expectedStatusCode) throws Exception {
        init();
        partialUpdateRequest(path)
                .andExpect(status().is(expectedStatusCode.value()))
                .andExpect(content().json(getResponseBody(path)));
    }

    @ParameterizedTest
    @ArgumentsSource(UpdateUserArgumentsProvider.class)
    public void update(String path, HttpStatus expectedStatusCode) throws Exception {
        init();
        updateRequest(path)
                .andExpect(status().is(expectedStatusCode.value()))
                .andExpect(content().json(getResponseBody(path)));
    }

    @ParameterizedTest
    @ArgumentsSource(FetchUserArgumentProvider.class)
    public void fetch(String path, HttpStatus expectedStatusCode) throws Exception {
        initList();
        fetchRequest(path)
                .andExpect(status().is(expectedStatusCode.value()))
                .andExpect(content().json(getResponseBody(path)));
    }

    @ParameterizedTest
    @ArgumentsSource(GetUserArgumentsProvider.class)
    public void get(String path, HttpStatus expectedStatusCode) throws Exception {
        init();
        getRequest(path)
                .andExpect(status().is(expectedStatusCode.value()))
                .andExpect(content().json(getResponseBody(path)));
    }

    @ParameterizedTest
    @ArgumentsSource(DeleteUserArgumentsProvider.class)
    public void delete(String path, HttpStatus expectedStatusCode) throws Exception {
        init();
        deleteRequest(path)
                .andExpect(status().is(expectedStatusCode.value()))
                .andExpect(content().string(getResponseBody(path)));
    }

    private void init() {
        userDaoDefault.save(new User(null,
                "test@test",
                "Test",
                "Test",
                LocalDate.of(1990, 1, 1),
                "Test", "+380-000-000-0000"));
    }

    private void initList() {
        userDaoDefault.save(new User(null,
                "test3@test.com",
                "Test3",
                "Test3",
                LocalDate.of(2005, 3, 3),
                "Test3", "+380-000-000-0000"));
        userDaoDefault.save(new User(null,
                "test2@test.com",
                "Test2",
                "Test2",
                LocalDate.of(2000, 2, 2),
                "Test2", "+380-000-000-0000"));
        userDaoDefault.save(new User(null,
                "test1@test.com",
                "Test1",
                "Test1",
                LocalDate.of(1995, 1, 1),
                "Test1", "+380-000-000-0000"));
        userDaoDefault.save(new User(null,
                "test4@test.com",
                "Test4",
                "Test4",
                LocalDate.of(1990, 4, 4),
                "Test4", "+380-000-000-0000"));
        userDaoDefault.save(new User(null,
                "test5@test.com",
                "Test5",
                "Test5",
                LocalDate.of(1985, 3, 15),
                "Test5", "+380-000-000-0000"));
    }
}

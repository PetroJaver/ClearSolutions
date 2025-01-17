package org.clearsolutions.restapi;

import org.clearsolutions.restapi.argumentsprovider.*;
import org.clearsolutions.restapi.extension.PostgresContainerExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(PostgresContainerExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles(profiles = "jpa")
@Sql(scripts = {"classpath:sql/clean_all.sql"})
public class UsersControllerJpaTest extends AbstractUsersControllerTest {
    @ParameterizedTest
    @ArgumentsSource(CreateUserArgumentsProvider.class)
    public void create(String path, HttpStatus expectedStatusCode) throws Exception {
        createRequest(path)
                .andExpect(status().is(expectedStatusCode.value()))
                .andExpect(content().json(getResponseBody(path)));
    }

    @ParameterizedTest
    @Sql(scripts = {"classpath:sql/clean_all.sql", "classpath:sql/test_data.sql"})
    @ArgumentsSource(PartialUpdateArgumentsProvider.class)
    public void partialUpdate(String path, HttpStatus expectedStatusCode) throws Exception {
        partialUpdateRequest(path)
                .andExpect(status().is(expectedStatusCode.value()))
                .andExpect(content().json(getResponseBody(path)));
    }

    @ParameterizedTest
    @Sql(scripts = {"classpath:sql/clean_all.sql", "classpath:sql/test_data.sql"})
    @ArgumentsSource(UpdateUserArgumentsProvider.class)
    public void update(String path, HttpStatus expectedStatusCode) throws Exception {
        updateRequest(path)
                .andExpect(status().is(expectedStatusCode.value()))
                .andExpect(content().json(getResponseBody(path)));
    }

    @ParameterizedTest
    @Sql(scripts = {"classpath:sql/clean_all.sql", "classpath:sql/test_data_list.sql"})
    @ArgumentsSource(FetchUserArgumentProvider.class)
    public void fetch(String path, HttpStatus expectedStatusCode) throws Exception {
        fetchRequest(path)
                .andExpect(status().is(expectedStatusCode.value()))
                .andExpect(content().json(getResponseBody(path)));
    }

    @ParameterizedTest
    @Sql(scripts = {"classpath:sql/clean_all.sql", "classpath:sql/test_data.sql"})
    @ArgumentsSource(GetUserArgumentsProvider.class)
    public void get(String path, HttpStatus expectedStatusCode) throws Exception {
        getRequest(path)
                .andExpect(status().is(expectedStatusCode.value()))
                .andExpect(content().json(getResponseBody(path)));
    }

    @ParameterizedTest
    @Sql(scripts = {"classpath:sql/clean_all.sql", "classpath:sql/test_data.sql"})
    @ArgumentsSource(DeleteUserArgumentsProvider.class)
    public void delete(String path, HttpStatus expectedStatusCode) throws Exception {
        deleteRequest(path)
                .andExpect(status().is(expectedStatusCode.value()))
                .andExpect(content().string(getResponseBody(path)));
    }
}

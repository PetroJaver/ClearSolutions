package org.clearsolutions.restapi;

import org.clearsolutions.restapi.extension.PostgresContainerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith({PostgresContainerExtension.class,})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles(profiles = "jpa")
@Sql(scripts = "classpath:sql/clean-all.sql")
public class UsersControllerJpaTest extends AbstractUsersControllerTest {

    @Test
    public void create() throws Exception {
        String requestBody = TestUtils.getFileData("users/created/request.json");

        createRequest(requestBody);
    }

    @ParameterizedTest
    @ArgumentsSource(BadRequestArgumentsProvider.class)
    public void createWithBadRequest(String path) throws Exception {
        String requestBody = getRequest(path);
        String responseBody = getResponse(path);

        createRequest(requestBody)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(responseBody));

        checkEmptyUsers();
    }
}

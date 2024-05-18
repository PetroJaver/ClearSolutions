package org.clearsolutions.restapi;

import org.clearsolutions.restapi.util.TestUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(classes = ClearSolutionsApplication.class)
@ExtendWith({SpringExtension.class})
@AutoConfigureMockMvc
public class AbstractUsersControllerTest {
    private static final String DEFAULT_URL = "/users";
    private static final String DEFAULT_WITH_ID_URL = "/users/{id}";
    private static final String DEFAULT_MEDIA_TYPE = "application/vnd.clearsolutions.api.v1+json";
    private static final String REQUEST_BODY_FORMAT = "%s/request.json";
    private static final String RESPONSE_BODY_FORMAT = "%s/response.json";
    private static final String REQUEST_PARAMS_FORMAT = "%s/request_params.txt";
    private static final String REQUEST_PATH_VARIABLE_FORMAT = "%s/path_variable.txt";

    @Autowired
    private MockMvc mvc;

    protected ResultActions fetchRequest(String path) throws Exception {
        return mvc.perform(get(DEFAULT_URL)
                .queryParams(getRequestParams(path))
                .accept(DEFAULT_MEDIA_TYPE));
    }

    protected ResultActions createRequest(String path) throws Exception {
        return mvc.perform(post(DEFAULT_URL)
                .accept(DEFAULT_MEDIA_TYPE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getRequestBody(path)));
    }

    protected ResultActions updateRequest(String path) throws Exception {
        return mvc.perform(put(DEFAULT_WITH_ID_URL, getPathVariable(path))
                .accept(DEFAULT_MEDIA_TYPE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getRequestBody(path)));
    }

    protected ResultActions partialUpdateRequest(String path) throws Exception {
        return mvc.perform(patch(DEFAULT_WITH_ID_URL, getPathVariable(path))
                .accept(DEFAULT_MEDIA_TYPE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getRequestBody(path)));
    }

    protected ResultActions getRequest(String path) throws Exception {
        return mvc.perform(get(DEFAULT_WITH_ID_URL, getPathVariable(path))
                .accept(DEFAULT_MEDIA_TYPE));
    }

    protected ResultActions deleteRequest(String path) throws Exception {
        return mvc.perform(delete(DEFAULT_WITH_ID_URL, getPathVariable(path))
                .accept(DEFAULT_MEDIA_TYPE));
    }

    protected String getResponseBody(String path) {
        return TestUtils.getFileData(format(RESPONSE_BODY_FORMAT, path));
    }

    private String getRequestBody(String path) {
        return TestUtils.getFileData(format(REQUEST_BODY_FORMAT, path));
    }

    private MultiValueMap<String, String> getRequestParams(String path) {
        String rawRequestParams = TestUtils.getFileData(format(REQUEST_PARAMS_FORMAT, path));
        Map<String, List<String>> requestParamsMap = Arrays.stream(rawRequestParams.split("&"))
                .map(s -> s.split("="))
                .map(arr -> Map.entry(arr[0], Collections.singletonList(arr.length > 1 ? arr[1] : null)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return CollectionUtils.toMultiValueMap(requestParamsMap);
    }

    private String getPathVariable(String path) {
        TestUtils.getFileData(format(REQUEST_PATH_VARIABLE_FORMAT, path));

        return TestUtils.getFileData(format(REQUEST_PATH_VARIABLE_FORMAT, path));
    }
}

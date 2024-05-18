package org.clearsolutions.restapi.argumentsprovider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

public class FetchUserArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.arguments("users/fetch/ok/with_all_users", HttpStatus.OK),
                Arguments.arguments("users/fetch/ok/with_birth_date_range", HttpStatus.OK),
                Arguments.arguments("users/fetch/bad_request/with_birth_date_from", HttpStatus.BAD_REQUEST),
                Arguments.arguments("users/fetch/bad_request/with_birth_date_to", HttpStatus.BAD_REQUEST),
                Arguments.arguments("users/fetch/bad_request/with_invalid_birth_date_range", HttpStatus.BAD_REQUEST)
        );
    }
}

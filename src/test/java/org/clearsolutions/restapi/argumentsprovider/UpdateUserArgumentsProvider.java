package org.clearsolutions.restapi.argumentsprovider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

public class UpdateUserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of("users/update/ok/with_full_user_data", HttpStatus.OK),
                Arguments.of("users/update/ok/with_required_user_data", HttpStatus.OK),
                Arguments.of("users/update/bad_request/with_birth_date", HttpStatus.BAD_REQUEST),
                Arguments.of("users/update/bad_request/with_email", HttpStatus.BAD_REQUEST),
                Arguments.of("users/update/bad_request/with_phone_number", HttpStatus.BAD_REQUEST),
                Arguments.of("users/update/bad_request/with_required", HttpStatus.BAD_REQUEST),
                Arguments.of("users/update/bad_request/with_early_birth_date", HttpStatus.BAD_REQUEST),
                Arguments.of("users/update/not_found", HttpStatus.NOT_FOUND),
                Arguments.of("users/update/server_error", HttpStatus.INTERNAL_SERVER_ERROR)
        );
    }
}

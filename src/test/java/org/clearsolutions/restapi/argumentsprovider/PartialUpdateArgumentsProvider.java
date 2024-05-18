package org.clearsolutions.restapi.argumentsprovider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

public class PartialUpdateArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of("users/partial_update/ok/with_address", HttpStatus.OK),
                Arguments.of("users/partial_update/ok/with_birth_date", HttpStatus.OK),
                Arguments.of("users/partial_update/ok/with_email", HttpStatus.OK),
                Arguments.of("users/partial_update/ok/with_first_name", HttpStatus.OK),
                Arguments.of("users/partial_update/ok/with_last_name", HttpStatus.OK),
                Arguments.of("users/partial_update/ok/with_phone_number", HttpStatus.OK),
                Arguments.of("users/partial_update/bad_request/with_birth_date", HttpStatus.BAD_REQUEST),
                Arguments.of("users/partial_update/bad_request/with_email", HttpStatus.BAD_REQUEST),
                Arguments.of("users/partial_update/bad_request/with_phone_number", HttpStatus.BAD_REQUEST),
                Arguments.of("users/partial_update/bad_request/with_early_birth_date", HttpStatus.BAD_REQUEST),
                Arguments.of("users/partial_update/not_found", HttpStatus.NOT_FOUND),
                Arguments.of("users/partial_update/server_error", HttpStatus.INTERNAL_SERVER_ERROR)
        );
    }
}
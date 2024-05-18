package org.clearsolutions.restapi.argumentsprovider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

public class CreateUserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of("users/create/created/with_full_user_data", HttpStatus.CREATED),
                Arguments.of("users/create/created/with_required_user_data", HttpStatus.CREATED),
                Arguments.of("users/create/bad_request/with_birth_date", HttpStatus.BAD_REQUEST),
                Arguments.of("users/create/bad_request/with_email", HttpStatus.BAD_REQUEST),
                Arguments.of("users/create/bad_request/with_phone_number", HttpStatus.BAD_REQUEST),
                Arguments.of("users/create/bad_request/with_required", HttpStatus.BAD_REQUEST),
                Arguments.of("users/create/bad_request/with_early_birth_date", HttpStatus.BAD_REQUEST)
        );
    }
}
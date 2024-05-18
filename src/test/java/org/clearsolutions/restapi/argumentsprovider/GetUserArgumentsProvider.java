package org.clearsolutions.restapi.argumentsprovider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

public class GetUserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.arguments("users/get/ok", HttpStatus.OK),
                Arguments.arguments("users/get/not_found", HttpStatus.NOT_FOUND),
                Arguments.arguments("users/get/server_error", HttpStatus.INTERNAL_SERVER_ERROR)
        );
    }
}

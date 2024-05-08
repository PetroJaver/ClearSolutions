package org.clearsolutions.restapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.clearsolutions.restapi.dto.ResponseDto;
import org.clearsolutions.restapi.dto.UserDto;
import org.clearsolutions.restapi.dto.GetUsersParams;
import org.clearsolutions.restapi.service.UserService;
import org.clearsolutions.restapi.validation.group.UserBasicInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/users", produces = {"application/vnd.clearsolutions.api.v1+json"})
@AllArgsConstructor
@Tag(name = "Users Controller", description = "performs CRUD operations with the Users")
public class UsersController {
    private final UserService userService;

    @GetMapping("{id}")
    @Operation(summary = "Get the user by id", description = "finds the user by id", responses = {@ApiResponse(responseCode = "200", description = "Successful found", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"data\": {\n" + "    \"email\": \"user1@example.com\",\n" + "    \"firstName\": \"Wong\",\n" + "    \"lastName\": \"William\",\n" + "    \"birthDate\": \"1990-01-01\",\n" + "    \"address\": null,\n" + "    \"phoneNumber\": null\n" + "  }\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")}), @ApiResponse(responseCode = "400", description = "Client error", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"status\": 400,\n" + "  \"error\": {\n" + "    \"detail\": \"Client error\"\n" + "  }\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")}), @ApiResponse(responseCode = "404", description = "Not found error", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"status\": 404,\n" + "  \"error\": {\n" + "    \"detail\": \"Not found the user with id=1\"\n" + "  }\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")}), @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"status\": 500,\n" + "  \"errors\": [\n" + "    {\n" + "      \"detail\": \"Something went wrong\"\n" + "    }\n" + "  ]\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")})}, parameters = {@Parameter(in = ParameterIn.PATH, name = "id", content = {@Content(examples = {@ExampleObject("1")})}),})
    public ResponseEntity<ResponseDto<UserDto>> get(@PathVariable Long id) {
        UserDto user = userService.get(id);
        ResponseDto<UserDto> response = new ResponseDto<>(user);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Fetch the users", description = "fetches all users by provided request params", responses = {@ApiResponse(responseCode = "200", description = "Successful fetched", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"pageable\": {\n" + "    \"pageNumber\": 0,\n" + "    \"pageSize\": 10,\n" + "    \"sort\": {\n" + "      \"empty\": true,\n" + "      \"sorted\": false,\n" + "      \"unsorted\": true\n" + "    },\n" + "    \"offset\": 0,\n" + "    \"unpaged\": false,\n" + "    \"paged\": true\n" + "  },\n" + "  \"last\": true,\n" + "  \"totalElements\": 1,\n" + "  \"totalPages\": 1,\n" + "  \"size\": 10,\n" + "  \"number\": 0,\n" + "  \"sort\": {\n" + "    \"empty\": true,\n" + "    \"sorted\": false,\n" + "    \"unsorted\": true\n" + "  },\n" + "  \"first\": true,\n" + "  \"numberOfElements\": 1,\n" + "  \"empty\": false,\n" + "  \"data\": [\n" + "    {\n" + "      \"id\": 1,\n" + "      \"email\": \"user1@example.com\",\n" + "      \"firstName\": \"Wong\",\n" + "      \"lastName\": \"William\",\n" + "      \"birthDate\": \"1990-01-01\",\n" + "      \"address\": \"808 Redwood St\",\n" + "      \"phoneNumber\": \"+380-123-456-7890\"\n" + "    }\n" + "  ]\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")}), @ApiResponse(responseCode = "400", description = "Client error", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"status\": 400,\n" + "  \"errors\": [\n" + "    {\n" + "      \"detail\": \"Birth day range is invalid\"\n" + "    }\n" + "  ]\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")}), @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"status\": 500,\n" + "  \"errors\": [\n" + "    {\n" + "      \"detail\": \"Something went wrong\"\n" + "    }\n" + "  ]\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")})}, parameters = {@Parameter(name = "birthDateFrom", content = {@Content(examples = {@ExampleObject("1000-01-01")})}), @Parameter(name = "birthDateTo", content = {@Content(examples = {@ExampleObject("3000-01-01")})}), @Parameter(name = "page", content = {@Content(examples = {@ExampleObject("0")})}), @Parameter(name = "size", content = {@Content(examples = {@ExampleObject("10")})}), @Parameter(name = "sort", content = {@Content(examples = {@ExampleObject("id,asc")})}, description = "keep in mind that the application must run in JPA mode.")})
    public ResponseEntity<Page<UserDto>> fetch(@Parameter(hidden = true) Pageable pageable, @Parameter(hidden = true) @Valid GetUsersParams getUsersParams) {
        Page<UserDto> users = userService.fetch(getUsersParams, pageable);

        return ResponseEntity.ok(users);
    }

    @PostMapping
    @Operation(summary = "Create the user", description = "creates the user by provided body", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {@Content(examples = {@ExampleObject(name = "Only required", value = "{\n" + "    \"birthDate\": \"1990-01-01\",\n" + "    \"email\": \"user1@example.com\",\n" + "    \"lastName\": \"William\",\n" + "    \"firstName\": \"Wong\"\n" + "}", description = "Creates the user with only required data"), @ExampleObject(name = "All", value = "{\n" + "    \"birthDate\": \"1990-01-01\",\n" + "    \"email\": \"user1@example.com\",\n" + "    \"lastName\": \"William\",\n" + "    \"firstName\": \"Wong\",\n" + "    \"phoneNumber\": \"+380-123-456-7890\",\n" + "    \"address\": \"808 Redwood St\"\n" + "}", description = "Creates the user with all data")})}), responses = {@ApiResponse(responseCode = "201", description = "The user successful created", content = {@Content(examples = {@ExampleObject(name = "Only required", value = "{\n" + "    \"birthDate\": \"1990-01-01\",\n" + "    \"email\": \"user1@example.com\",\n" + "    \"lastName\": \"William\",\n" + "    \"firstName\": \"Wong\",\n" + "    \"phoneNumber\": \"null\",\n" + "    \"address\": \"null\"\n" + "}", description = "Returns when the user successfully created with only required data"), @ExampleObject(name = "All", value = "{\n" + "    \"birthDate\": \"1990-01-01\",\n" + "    \"email\": \"user1@example.com\",\n" + "    \"lastName\": \"William\",\n" + "    \"firstName\": \"Wong\",\n" + "    \"phoneNumber\": \"+380-123-456-7890\",\n" + "    \"address\": \"808 Redwood St\"\n" + "}", description = "Returns when the user successfully created with all data")}, mediaType = "application/vnd.clearsolutions.api.v1+json")}, headers = {@Header(name = HttpHeaders.LOCATION, description = "where the resource is located")}), @ApiResponse(responseCode = "400", description = "Client error", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"status\": 400,\n" + "  \"errors\": [\n" + "    {\n" + "      \"detail\": \"The field 'email' must be a well-formed email address\"\n" + "    }\n" + "  ]\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")}), @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"status\": 500,\n" + "  \"errors\": [\n" + "    {\n" + "      \"detail\": \"Something went wrong\"\n" + "    }\n" + "  ]\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")})})
    public ResponseEntity<ResponseDto<UserDto>> create(@RequestBody @Valid UserDto dto, UriComponentsBuilder uriComponentsBuilder) {
        UserDto user = userService.create(dto);
        UriComponents userUri = uriComponentsBuilder.replacePath("/users/{id}").buildAndExpand(user.getId());
        ResponseDto<UserDto> response = new ResponseDto<>(user);

        return ResponseEntity.created(userUri.toUri()).body(response);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update the user", description = "updates the user by provided body", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {@Content(examples = {@ExampleObject(name = "Only required", value = "{\n" + "  \"id\": 1,\n" + "  \"birthDate\": \"1990-01-01\",\n" + "  \"email\": \"user1@example.com\",\n" + "  \"lastName\": \"William\",\n" + "  \"firstName\": \"Wong\"\n" + "}", description = "Updates the user with only required data"), @ExampleObject(name = "All", value = "{\n" + "  \"id\": 1,\n" + "  \"birthDate\": \"1990-01-01\",\n" + "  \"email\": \"user1@example.com\",\n" + "  \"lastName\": \"William\",\n" + "  \"firstName\": \"Wong\",\n" + "  \"phoneNumber\": \"+380-123-456-7890\",\n" + "  \"address\": \"808 Redwood St\"\n" + "}", description = "Updates the user with all data")})}), responses = {@ApiResponse(responseCode = "200", description = "The user successful updated", content = {@Content(examples = {@ExampleObject(name = "Only required", value = "{\n" + "  \"data\": {\n" + "    \"email\": \"user1@example.com\",\n" + "    \"firstName\": \"Wong\",\n" + "    \"lastName\": \"William\",\n" + "    \"birthDate\": \"1990-01-01\",\n" + "    \"address\": null,\n" + "    \"phoneNumber\": null\n" + "  }\n" + "}", description = "Returns when the user successfully updated with only required data"), @ExampleObject(name = "All", value = "{\n" + "  \"data\": {\n" + "    \"email\": \"user1@example.com\",\n" + "    \"firstName\": \"Wong\",\n" + "    \"lastName\": \"William\",\n" + "    \"birthDate\": \"1990-01-01\",\n" + "    \"address\": \"808 Redwood St\",\n" + "    \"phoneNumber\": \"+380-123-456-7890\"\n" + "  }\n" + "}", description = "Returns when the user successfully updated with all data")}, mediaType = "application/vnd.clearsolutions.api.v1+json")}), @ApiResponse(responseCode = "400", description = "Client error", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"status\": 400,\n" + "  \"errors\": [\n" + "    {\n" + "      \"detail\": \"The field 'birthDate' must be earlier\"\n" + "    }\n" + "  ]\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")}), @ApiResponse(responseCode = "404", description = "Not found error", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"status\": 404,\n" + "  \"error\": {\n" + "    \"detail\": \"Not found the user with id=1\"\n" + "  }\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")}), @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"status\": 500,\n" + "  \"errors\": [\n" + "    {\n" + "      \"detail\": \"Something went wrong\"\n" + "    }\n" + "  ]\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")})}, parameters = {@Parameter(in = ParameterIn.PATH, name = "id", content = {@Content(examples = {@ExampleObject("1")})}),})
    public ResponseEntity<ResponseDto<UserDto>> update(@PathVariable Long id, @RequestBody @Valid UserDto dto) {
        UserDto user = userService.update(id, dto);
        ResponseDto<UserDto> response = new ResponseDto<>(user);

        return ResponseEntity.ok(response);
    }


    @PatchMapping("{id}")
    @Operation(summary = "Partially  update the user", description = "partially updates the user by provided body", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {@Content(examples = {@ExampleObject(name = "Update email", value = "{\n" + "  \"email\": \"user1@example.com\"\n" + "}", description = "Updates only the user's email"), @ExampleObject(name = "Update address", value = "{\n" + "  \"address\": \"808 Redwood St\"\n" + "}", description = "Updates only the user's address")})}), responses = {@ApiResponse(responseCode = "200", description = "The user successful updated", content = {@Content(examples = {@ExampleObject(name = "All", value = "{\n" + "  \"data\": {\n" + "    \"email\": \"user1@example.com\",\n" + "    \"firstName\": \"Wong\",\n" + "    \"lastName\": \"William\",\n" + "    \"birthDate\": \"1990-01-01\",\n" + "    \"address\": \"808 Redwood St\",\n" + "    \"phoneNumber\": \"+380-123-456-7890\"\n" + "  }\n" + "}", description = "Returns when the user successfully updated")}, mediaType = "application/vnd.clearsolutions.api.v1+json")}), @ApiResponse(responseCode = "400", description = "Client error", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"status\": 400,\n" + "  \"errors\": [\n" + "    {\n" + "      \"detail\": \"The field 'phoneNumber' must match \\\"^\\\\+?\\\\d{1,3}?[-\\\\s]?\\\\(?\\\\d{3}\\\\)?[-\\\\s]?\\\\d{3}[-\\\\s]?\\\\d{4}$\\\"\"\n" + "    }\n" + "  ]\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")}), @ApiResponse(responseCode = "404", description = "Not found error", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"status\": 404,\n" + "  \"error\": {\n" + "    \"detail\": \"Not found the user with id=1\"\n" + "  }\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")}), @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"status\": 500,\n" + "  \"errors\": [\n" + "    {\n" + "      \"detail\": \"Something went wrong\"\n" + "    }\n" + "  ]\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")})}, parameters = {@Parameter(in = ParameterIn.PATH, name = "id", content = {@Content(examples = {@ExampleObject("1")})}),})
    public ResponseEntity<ResponseDto<UserDto>> partialUpdate(@PathVariable Long id, @RequestBody @Validated(UserBasicInfo.class) UserDto dto) {
        UserDto user = userService.partialUpdate(id, dto);
        ResponseDto<UserDto> response = new ResponseDto<>(user);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete the user", description = "deletes the user by id", responses = {@ApiResponse(responseCode = "200", description = "Successfully deleted", content = {@Content(mediaType = "application/vnd.clearsolutions.api.v1+json")}), @ApiResponse(responseCode = "400", description = "Client error", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"status\": 400,\n" + "  \"error\": {\n" + "    \"detail\": \"Client error\"\n" + "  }\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")}), @ApiResponse(responseCode = "404", description = "Not found error", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"status\": 404,\n" + "  \"error\": {\n" + "    \"detail\": \"Not found the user with id=1\"\n" + "  }\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")}), @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(examples = {@ExampleObject(value = "{\n" + "  \"status\": 500,\n" + "  \"errors\": [\n" + "    {\n" + "      \"detail\": \"Something went wrong\"\n" + "    }\n" + "  ]\n" + "}")}, mediaType = "application/vnd.clearsolutions.api.v1+json")})}, parameters = {@Parameter(in = ParameterIn.PATH, name = "id", content = {@Content(examples = {@ExampleObject("1")})}),})
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);

        return ResponseEntity.ok().build();
    }
}

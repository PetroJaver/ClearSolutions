package org.clearsolutions.restapi.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.clearsolutions.restapi.dto.UserDto;
import org.clearsolutions.restapi.dto.GetUsersParams;
import org.clearsolutions.restapi.entity.User;
import org.clearsolutions.restapi.mapper.UserMapper;
import org.clearsolutions.restapi.repository.UserRepository;
import org.clearsolutions.restapi.service.UserService;
import org.clearsolutions.restapi.validation.group.UserBasicInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;

import static java.lang.String.format;

@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    public static final String NOT_FOUND_USER_MESSAGE = "Not found the user with id=%d";
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto get(Long id) {
        logger.info("Get the user with id={}", id);

        User user = findUserById(id);

        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto create(@Valid UserDto dto) {
        logger.info("Create the user={}", dto);

        User user = userMapper.userDtoToUser(dto);
        User savedUser = userRepository.save(user);

        return userMapper.userToUserDto(savedUser);
    }

    @Override
    public UserDto update(@NotNull Long id, @Valid UserDto dto) {
        logger.info("Update user {}", dto);

        User user = findUserById(id);
        userMapper.updateUserFromUserDto(dto, user);
        User savedUser = userRepository.save(user);

        return userMapper.userToUserDto(savedUser);
    }

    @Override
    public UserDto partialUpdate(@NotNull Long id, @Validated(UserBasicInfo.class) UserDto dto) {
        logger.info("Partial update the user={}", dto);

        User user = findUserById(id);
        userMapper.updateUserFromUserDto(dto, user);
        User savedUser = userRepository.save(user);

        return userMapper.userToUserDto(savedUser);
    }

    @Override
    public void delete(@NotNull Long id) {
        logger.info("Delete user with id={}", id);
        findUserById(id);
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserDto> fetch(@Valid GetUsersParams getUsersParams, Pageable pageable) {
        logger.info("Search user by {}", getUsersParams.getClass());
        Page<User> users = userRepository.fetch(getUsersParams, pageable);

        return users.map(userMapper::userToUserDto);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> HttpClientErrorException.create(HttpStatus.NOT_FOUND,
                        format(NOT_FOUND_USER_MESSAGE, id),
                        null,
                        null,
                        null));
    }
}

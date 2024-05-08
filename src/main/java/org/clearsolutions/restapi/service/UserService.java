package org.clearsolutions.restapi.service;

import org.clearsolutions.restapi.dto.UserDto;
import org.clearsolutions.restapi.dto.GetUsersParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto get(Long id);

    UserDto create(UserDto dto);

    UserDto update(Long id, UserDto dto);

    UserDto partialUpdate(Long id, UserDto dto);

    void delete(Long id);

    Page<UserDto> fetch(GetUsersParams dto, Pageable pageable);
}

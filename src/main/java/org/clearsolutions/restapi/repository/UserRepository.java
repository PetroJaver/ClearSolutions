package org.clearsolutions.restapi.repository;

import jakarta.validation.constraints.NotNull;
import org.clearsolutions.restapi.dto.GetUsersParams;
import org.clearsolutions.restapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository  {
    void deleteById(@NotNull Long id);

    User save(User user);

    Optional<User> findById(Long id);

    Page<User> fetch(GetUsersParams getUsersParams, Pageable pageable);
}

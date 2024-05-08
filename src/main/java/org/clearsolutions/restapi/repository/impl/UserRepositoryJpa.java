package org.clearsolutions.restapi.repository.impl;

import org.clearsolutions.restapi.dto.GetUsersParams;
import org.clearsolutions.restapi.entity.User;
import org.clearsolutions.restapi.repository.UserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "clearsolutions.mode", havingValue = "jpa")
public interface UserRepositoryJpa extends UserRepository, JpaRepository<User, Long> {
    @Query("FROM User U WHERE (cast(cast(:#{#getUsersParams.birthDateFrom} as string) as date) IS NULL OR U.birthDate > :#{#getUsersParams.birthDateFrom}) AND (cast(cast(:#{#getUsersParams.birthDateTo} as string) as date) IS NULL OR U.birthDate < :#{#getUsersParams.birthDateTo})")
    Page<User> fetch(GetUsersParams getUsersParams, Pageable pageable);
}

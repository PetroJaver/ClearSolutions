package org.clearsolutions.restapi.dao;

import org.clearsolutions.restapi.entity.User;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<ID, ENTITY> {
    ENTITY save(ENTITY user);

    Optional<ENTITY> read(ID id);

    void delete(ID id);

    List<ENTITY> readAll();
}

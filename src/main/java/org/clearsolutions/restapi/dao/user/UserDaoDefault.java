package org.clearsolutions.restapi.dao.user;

import org.clearsolutions.restapi.dao.AbstractDao;
import org.clearsolutions.restapi.entity.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
@ConditionalOnProperty(name = "clearsolutions.mode", havingValue = "default")
public class UserDaoDefault implements AbstractDao<Long, User> {
    private final Map<Long, User> userMap;
    private final AtomicLong idCounter;

    public UserDaoDefault() {
        this.userMap = new ConcurrentHashMap<Long, User>();
        this.idCounter = new AtomicLong(1);
    }

    @Override
    public User save(User user) {
        Long id = user.getId() == null ? idCounter.getAndIncrement() : user.getId();
        user.setId(id);
        userMap.put(id, user);

        return user;
    }

    @Override
    public Optional<User> read(Long id) {
        return Optional.ofNullable(userMap.get(id)) ;
    }

    @Override
    public void delete(Long id) {
        userMap.remove(id);
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<>(userMap.values());
    }
}

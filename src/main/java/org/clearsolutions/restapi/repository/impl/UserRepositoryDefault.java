package org.clearsolutions.restapi.repository.impl;

import lombok.AllArgsConstructor;
import org.clearsolutions.restapi.dao.AbstractDao;
import org.clearsolutions.restapi.dto.GetUsersParams;
import org.clearsolutions.restapi.entity.User;
import org.clearsolutions.restapi.repository.UserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@ConditionalOnProperty(name = "clearsolutions.mode", havingValue = "default")
@AllArgsConstructor
public class UserRepositoryDefault implements UserRepository {
    private final AbstractDao<Long, User> userAbstractDao;

    @Override
    public void deleteById(Long id) {
        userAbstractDao.delete(id);
    }

    @Override
    public User save(User user) {
        return userAbstractDao.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userAbstractDao.read(id);
    }

    @Override
    public Page<User> fetch(GetUsersParams getUsersParams, Pageable pageable) {
        long limit = (long) pageable.getPageNumber() * pageable.getPageSize();
        limit = limit == 0 ? pageable.getPageSize() : limit;
        List<User> users = (userAbstractDao.readAll());
        List<User> processedUsers = UserStream.getInstance(users.stream())
                .filterByBirthDayFrom(getUsersParams.getBirthDateFrom())
                .filterByBirthDayTo(getUsersParams.getBirthDateTo())
                .sort(pageable.getSort())
                .skip(pageable.getOffset())
                .limit(limit)
                .collectToList();

        return new PageImpl<>(processedUsers, pageable, users.size());
    }

    private static class UserStream {
        public static final String SORT_REQUEST_NOT_SUPPORTED_MESSAGE = "The sort request is not supported";
        private Stream<User> stream;

        private UserStream(Stream<User> stream) {
            this.stream = stream;
        }

        private UserStream filterByBirthDayFrom(LocalDate birthDayFrom) {
            doIfNotNull(birthDayFrom, () -> this.stream.filter(u -> u.getBirthDate().isAfter(birthDayFrom)));

            return this;
        }

        private UserStream filterByBirthDayTo(LocalDate birthDayTo) {
            doIfNotNull(birthDayTo, () -> stream.filter(u -> u.getBirthDate().isBefore(birthDayTo)));

            return this;
        }

        private UserStream skip(Long skip) {
            doIfNotNull(skip, () -> this.stream.skip(skip));

            return this;
        }

        private UserStream limit(Long limit) {
            doIfNotNull(limit, () -> this.stream.limit(limit));

            return this;
        }

        private UserStream sort(Sort sort) {
            if (sort.isSorted()) {
                throw HttpClientErrorException.create(HttpStatus.BAD_REQUEST,
                        SORT_REQUEST_NOT_SUPPORTED_MESSAGE,
                        null,
                        null,
                        null);
            }

            return this;
        }

        private void doIfNotNull(Object o, Supplier<Stream<User>> f) {
            if (o != null) {
                this.stream = f.get();
            }
        }

        private List<User> collectToList() {
            return this.stream.collect(Collectors.toList());
        }

        private static UserStream getInstance(Stream<User> stream) {
            return new UserStream(stream);
        }
    }
}

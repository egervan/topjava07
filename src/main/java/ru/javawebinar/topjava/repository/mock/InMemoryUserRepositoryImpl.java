package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UserUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserUtil.USER_LIST.forEach(this::save);
    }


    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);

        if(!repository.containsKey(id)) return false;

        repository.remove(id);
        return true;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);

        if(user.isNew()) user.setId(counter.incrementAndGet());

        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        List<User> allUsers = new ArrayList<>(repository.values());
        allUsers.sort((user1, user2) -> user1.getName().compareTo(user2.getName()));
        /*sorting without lambda:
          new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user1.getName().compareTo(user2.getName());
            }
        }*/
        return allUsers;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        User userByEmail = repository.entrySet()
                .stream()
                .filter(user -> user.getValue().getEmail().equals(email))
                .findFirst().get().getValue();
        return userByEmail;
    }
}

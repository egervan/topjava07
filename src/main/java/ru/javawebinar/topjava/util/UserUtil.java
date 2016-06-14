package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jager on 15.06.2016.
 */
public class UserUtil {
    public static final List<User> USER_LIST = Arrays.asList(
            new User("Ivan", "ivant66@yandex.ru", "12345", Role.ROLE_ADMIN),
            new User("Alex", "alex@yandex.ru", "353", Role.ROLE_USER),
            new User("Martin", "martin@yandex.ru", "54", Role.ROLE_USER),
            new User("Alla", "alla@yandex.ru", "1234345", Role.ROLE_USER)
    );
}

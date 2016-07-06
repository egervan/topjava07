package ru.javawebinar.topjava.service.abstractTest;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by nz on 06.07.16.
 */
@ActiveProfiles(profiles = {Profiles.ACTIVE_DB, Profiles.JDBC})
public class UserServiceJDBCTest extends UserServiceTest {
}

package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.abstractTest.UserServiceTest;

/**
 * Created by nz on 06.07.16.
 */
@ActiveProfiles(profiles = {Profiles.ACTIVE_DB, Profiles.JPA})
public class UserServiceTestDataJPA extends UserServiceTest {
}

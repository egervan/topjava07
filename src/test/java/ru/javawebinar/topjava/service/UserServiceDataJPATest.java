package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.abstractTest.UserServiceTest;

/**
 * Created by nz on 06.07.16.
 */
@ActiveProfiles(profiles = {Profiles.ACTIVE_DB, Profiles.JPA})
public class UserServiceDataJPATest extends UserServiceTest {
}

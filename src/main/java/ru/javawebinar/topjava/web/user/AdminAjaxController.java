package ru.javawebinar.topjava.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.List;

/**
 * User: grigory.kislin
 */
@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController extends AbstractUserController {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createOrUpdate(@RequestParam("id") Integer id,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("enabled") boolean enabled) {

        User user = new User(id, name, email, password, Role.ROLE_USER);
        user.setEnabled(enabled);
        if (user.isNew()) {
            super.create(user);
        } else {
            super.update(user, id);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void enable(@PathVariable("id") int id,
                       @RequestBody() String enabled) {
        User user = super.get(id);
        User updatedUser = new User(id, user.getName(), user.getEmail(), user.getPassword(),Role.ROLE_USER);
        updatedUser.setEnabled(Boolean.parseBoolean(enabled));
        super.update(updatedUser, id);
    }
}

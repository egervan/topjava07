package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Jager on 05.07.2016.
 */

@Transactional(readOnly = true)
public interface ProxyMealRepository extends JpaRepository<UserMeal, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM UserMeal m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    UserMeal save(UserMeal userMeal);
/*

    @Modifying
    UserMeal findOne(Integer id, Integer userId);

    @Modifying
    @Query("SELECT FROM UserMeal m WHERE m.user.id=:userId")
    List<UserMeal> findAll(@Param("userId") int userId);
*/
    UserMeal findOneByIdAndUserId(Integer id, Integer userId);

    List<UserMeal> findAllByUserIdOrderByIdDesc(int userId);

    List<UserMeal> findAllByDateTimeBetweenAndUserIdOrderByDateTimeDesc(LocalDateTime startDate, LocalDateTime endDate, int userId);
}

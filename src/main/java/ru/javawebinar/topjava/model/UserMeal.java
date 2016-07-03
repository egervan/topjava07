package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
@NamedQueries({
        @NamedQuery(name = UserMeal.ALL_SORTED, query = "SELECT m FROM UserMeal m WHERE m.user.id=:userId ORDER BY m.id DESC"),
        @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal m WHERE m.user.id=:userId AND m.id=:id"),
        @NamedQuery(name = UserMeal.DELETE_ALL, query = "DELETE FROM UserMeal m WHERE m.user.id=:userId"),
        @NamedQuery(name = UserMeal.BY_ID, query = "SELECT m FROM UserMeal m WHERE m.user.id=:userId AND m.id=:id"),
        @NamedQuery(name = UserMeal.BETWEEN, query = "SELECT m FROM UserMeal m WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.id DESC"),


})
@Entity
@Transactional
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = "date_time", name = "meals_unique_user_datetime_idx"), @UniqueConstraint(columnNames = "user_id", name = "meals_unique_user_datetime_idx")})
public class UserMeal extends BaseEntity {

    public static final String DELETE = "UserMeal.delete";
    public static final String ALL_SORTED = "UserMeal.getAllSorted";
    public static final String BY_ID = "UserMeal.getById";
    public static final String BETWEEN = "UserMeal.between";
    public static final String DELETE_ALL = "UserMeal.deleteAll";

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name= "description", nullable = false)
    @NotEmpty
    private String description;

    @Column(name = "calories")
    protected int calories;

    @ManyToOne(fetch = FetchType.LAZY)
  //  @JoinColumn(name = "user_id", nullable=false)
    private User user;

    public UserMeal() {
    }

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}

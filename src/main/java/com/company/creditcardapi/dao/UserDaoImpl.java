package com.company.creditcardapi.dao;

import com.company.creditcardapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    JdbcTemplate jdbcTemplate;

    private static final String ADD_USER_SQL =
            "INSERT INTO users (name, address) VALUES (?, ?)";

    private static final String DELETE_USER_SQL =
            "DELETE FROM users WHERE id = ?";

    private static final String FIND_ALL_SQL =
            "SELECT * FROM users";

    private static final String FIND_USER_BY_ID_SQL =
            "SELECT * FROM users WHERE id = ?";


    private static final String UPDATE_USER_SQL =
            "UPDATE users SET name = ?, address = ? WHERE id = ?";

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUser(User user) {
        jdbcTemplate.update(
                ADD_USER_SQL,
                user.getName(),
                user.getAddress());

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        user.setId(id);

        return user;
    }

    @Override
    public void deleteUser(Integer id) {
        jdbcTemplate.update(DELETE_USER_SQL, id);
    }

    @Override
    public User findUserById(Integer userId) {
        try {
            return jdbcTemplate.queryForObject(FIND_USER_BY_ID_SQL, this::mapRowToUser, userId);
        } catch (EmptyResultDataAccessException e) {
            // if there is no match for this user id, return null
            return null;
        }
    }

    @Override
    public List<User> findAllUsers() {
        return jdbcTemplate.query(FIND_ALL_SQL, this::mapRowToUser);
    }

    @Override
    public void updateUser(User user) {
        jdbcTemplate.update(
                UPDATE_USER_SQL,
                user.getName(),
                user.getAddress(),
                user.getId()
        );
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setAddress(rs.getString("address"));

        return user;
    }
}

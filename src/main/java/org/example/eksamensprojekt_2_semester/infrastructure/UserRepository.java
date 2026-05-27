package org.example.eksamensprojekt_2_semester.infrastructure;

import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.models.enums.*;
import org.example.eksamensprojekt_2_semester.models.interfaces.IUserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements IUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findUserById(int userId) {
        String sql = "SELECT  first_name, last_name, username, password, email, role  FROM users WHERE id = ?";

        try {
            List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    Role.valueOf(rs.getString("role"))

            ), userId);

            return users.isEmpty() ? Optional.empty() : Optional.of(users.getFirst());
        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke finde nogen bruger med ID " + userId, e);
        }
    }

    @Override
    public int createUser(User user) {
        String sql = "INSERT INTO users (first_name, last_name, username, password , email, role) VALUES (?, ?, ?, ?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(con  -> {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setString(3, user.getUsername());
                ps.setString(5, user.getPassword());
                ps.setString(4, user.getEmail());
                ps.setString(6, user.getRole().name());
                return ps;
            }, keyHolder);

        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke oprette bruger med username: " + user.getUsername(), e);
        }

        return keyHolder.getKey().intValue();
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        String sql = "SELECT id, first_name, last_name, username, password, email, role FROM users WHERE username = ?";

        try {
            List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    Role.valueOf(rs.getString("role"))
            ), username);

            return users.isEmpty() ? Optional.empty() : Optional.of(users.getFirst());
        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke finde nogen bruger med username: " + username, e);
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        String sql = "SELECT id, first_name, last_name, username, password, email, role FROM users WHERE email = ?";

        try {
            List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    Role.valueOf(rs.getString("role"))
            ), email);

            return users.isEmpty() ? Optional.empty() : Optional.of(users.getFirst());
        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke finde nogen bruger med email: " + email, e);
        }
    }

    @Override
    public void removeUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke fjerne bruger med ID " + id, e);
        }
    }

    @Override
    public void updateUser(int id, String username, String email, String password) {
        String sql = "UPDATE users set username = ?, email = ?, password = ? WHERE id = ?";

        try {
            jdbcTemplate.update(sql, username, email, password, id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke opdatere bruger med ID " + id, e);
        }
    }
}
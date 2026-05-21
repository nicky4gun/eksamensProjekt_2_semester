package org.example.eksamensprojekt_2_semester.repositorys;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.models.enums.*;
import org.example.eksamensprojekt_2_semester.models.interfaces.IUserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
    public Optional<Card> findCardByUserId(int userId, int cardId) {
        String sql = """
                SELECT c.id, c.name, c.card_type, c.color, c.expansions, c.rarity, c.rule_text, c.image_url
                FROM cards c
                JOIN collection_cards cc ON c.id = cc.card_id
                JOIN collections col ON cc.collection_id = col.id
                WHERE col.user_id = ? AND c.id = ?""";

        try {
            List<Card> cards = jdbcTemplate.query(sql, (rs, rowNum) -> new Card(
                    rs.getInt("id"),
                    rs.getString("name"),
                    CardType.valueOf(rs.getString("card_type")),
                    ManaColor.valueOf(rs.getString("color")),
                    rs.getString("expansions"),
                    Rarity.valueOf(rs.getString("rarity")),
                    rs.getString("rule_text"),
                    rs.getString("image_url")
            ), userId, cardId);

            return cards.isEmpty() ? Optional.empty() : Optional.of(cards.getFirst());
        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke finde kort med Bruger ID " +
                    userId + "fra brugerens samling!", e);
        }
    }

    @Override
    public Optional<User> findUserById(int userId) {
        String sql = "SELECT  first_name, last_name, username, password, email, role, image_url  FROM users WHERE id = ?";

        try {
            List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    Role.valueOf(rs.getString("role")),
                    rs.getString("image_url")

            ), userId);

            return users.isEmpty() ? Optional.empty() : Optional.of(users.getFirst());
        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke finde nogen bruger med ID " + userId, e);
        }
    }

    @Override
    public List<Card> findCardsByUserId(int userId) {
        String sql = """
                    SELECT c.id, c.name, c.card_type, c.color, c.expansions, c.rarity, c.rule_text, c.image_url
                    FROM cards c
                    JOIN collection_cards cc ON c.id = cc.card_id
                    JOIN collections col ON cc.collection_id = col.id
                    WHERE col.user_id = ?
                """;

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new Card(
                    rs.getString("name"),
                    CardType.valueOf(rs.getString("card_type").trim()),
                    ManaColor.valueOf(rs.getString("color").trim().toUpperCase()),
                    rs.getString("expansions"),
                    Rarity.valueOf(rs.getString("rarity").trim()),
                    rs.getString("rule_text"),
                    rs.getString("image_url")

            ), userId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke hente kort fra brugerens samling!", e);
        }
    }

    @Override
    public List<Card> getFavorites(int userId) {
        String sql = "SELECT card_id where user_id = ?";

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new Card(
                    rs.getInt("id")
            ));
        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke hente favoritkort for bruger med ID " + userId, e);
        }
    }

    @Override
    public List<Card> getFavoritesLimitBy10(Integer userId) {
        String sql = "SELECT card_id where user_id = ? LIMIT 10";

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new Card(
                    rs.getInt("id")
            ));
        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke hente favoritkort for bruger med ID " + userId, e);
        }
    }

    @Override
    public void saveFavorites(int userId, int cardId) {
        String sql = "INSERT INTO favorite_cards (user_id,card_id) VALUES (?,?)";

        try {
            jdbcTemplate.update(sql, userId, cardId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke gemme favoritkort med ID " + cardId, e);
        }
    }

    @Override
    public void removeFavorites(int userId, int cardId) {
        String sql = "DELETE FROM favorite_cards WHERE user_id = ? AND card_id = ?";

        try {
            jdbcTemplate.update(sql, userId, cardId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke fjerne favoritkort med ID " + cardId, e);
        }
    }

    @Override
    public int createUser(User user) {
        String sql = "INSERT INTO users (first_name, last_name, username, password , email, role, image_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
                ps.setString(7, user.getImage());
                return ps;
            }, keyHolder);

        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke oprette bruger med username: " + user.getUsername(), e);
        }

        return keyHolder.getKey().intValue();
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        String sql = "SELECT id, first_name, last_name, username, password, email, role, image_url FROM users WHERE username = ?";

        try {
            List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    Role.valueOf(rs.getString("role")),
                    rs.getString("image_url")

            ), username);

            return users.isEmpty() ? Optional.empty() : Optional.of(users.getFirst());
        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke finde nogen bruger med username: " + username, e);
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
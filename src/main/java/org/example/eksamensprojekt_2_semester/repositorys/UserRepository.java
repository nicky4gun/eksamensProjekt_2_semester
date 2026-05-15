package org.example.eksamensprojekt_2_semester.repositorys;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.models.enums.*;
import org.example.eksamensprojekt_2_semester.models.interfaces.IUserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
                       SELECT c.id, c.name, c.card_type, c.color, c.set, c.rarity, c.ruleText, c.imageUrl
                       FROM cards c
                       JOIN collection_cards cc ON c.id = cc.card_id
                       JOIN collections col ON cc.collection_id = col.id
                       WHERE col.user_id = ? AND c.id = ?""";

        List<Card> cards = jdbcTemplate.query(sql, (rs, rowNum) -> new Card(
                rs.getString("name"),
                CardType.valueOf(rs.getString("card_type")),
                ManaColor.valueOf(rs.getString("color")),
                rs.getString("set"),
                Rarity.valueOf(rs.getString("rarity")),
                rs.getString("ruleText"),
               rs.getString("imageUrl")
        ), userId, cardId);

        return cards.isEmpty() ? Optional.empty() : Optional.of(cards.getFirst());
    }

   @Override
    public Optional<User> findUserById(int userId) {
       String sql = "SELECT  first_Name,last_Name,user_Name,password, email, role, image  FROM users WHERE id = ?";

       List<User> user  = jdbcTemplate.query(sql, (rs, rowNum) -> new User(
               rs.getString("first_Name"),
               rs.getString("last_Name"),
               rs.getString("user_Name"),
               rs.getString("password"),
               rs.getString("email"),
               Role.valueOf(rs.getString("role")),
               rs.getString("image")

       ), userId);

       return user.isEmpty() ? Optional.empty() : Optional.of(user.getFirst());
    }

}

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
                       SELECT c.id, c.name, c.card_type, c.color, c.expansions, c.rarity, c.rule_text, c.image_url
                       FROM cards c
                       JOIN collection_cards cc ON c.id = cc.card_id
                       JOIN collections col ON cc.collection_id = col.id
                       WHERE col.user_id = ? AND c.id = ?""";

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
    }

   @Override
    public Optional<User> findUserById(int userId) {
       String sql = "SELECT  first_name, last_name, username, password, email, role, image_url  FROM users WHERE id = ?";

       List<User> user  = jdbcTemplate.query(sql, (rs, rowNum) -> new User(
               rs.getString("first_name"),
               rs.getString("last_name"),
               rs.getString("username"),
               rs.getString("password"),
               rs.getString("email"),
               Role.valueOf(rs.getString("role")),
               rs.getString("image_url")

       ), userId);

       return user.isEmpty() ? Optional.empty() : Optional.of(user.getFirst());
    }
    @Override
    public List<Card> findCardsByUserId(int userId){
        String sql = """
        SELECT c.id, c.name, c.card_type, c.color, c.expansions, c.rarity, c.rule_text, c.image_url
        FROM cards c
        JOIN collection_cards cc ON c.id = cc.card_id
        JOIN collections col ON cc.collection_id = col.id
        WHERE col.user_id = ?
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Card(
                rs.getString("name"),
                CardType.valueOf(rs.getString("card_type").trim()),
                ManaColor.valueOf(rs.getString("color").trim().toUpperCase()),
                rs.getString("expansions"),
                Rarity.valueOf(rs.getString("rarity").trim()),
                rs.getString("rule_text"),
                rs.getString("image_url")

        ), userId);

    }
@Override
    public void saveFavorites(int userId,int cardId){
        String sql = "INSERT INTO favorite_cards (user_id,card_id) VALUES (?,?)";
        jdbcTemplate.update(sql,userId,cardId);

}
@Override
    public void removeFavorites(int userId,int cardId){
        String sql = "DELETE FROM favorite_cards WHERE user_id = ? AND card_id = ?";
        jdbcTemplate.update(sql,userId,cardId);
}




}

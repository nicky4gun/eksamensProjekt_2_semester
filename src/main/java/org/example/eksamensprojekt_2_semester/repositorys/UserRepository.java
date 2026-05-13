package org.example.eksamensprojekt_2_semester.repositorys;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Deck;
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
    public List<Card> findCardsByUserId(int userId, List<Integer> cardIds){
        String sql = "SELECT  name, cardType, colors, set, rarity, ruleText, imageUrl FROM cards WHERE card_id=? AND user_id=?";
        List<Card> cards= jdbcTemplate.query(sql, (rs, rowNum) -> new Card(
                rs.getString("name"),
                CardType.valueOf(rs.getString("cardtype")),
                ManaColor.valueOf(rs.getString("color")),
                rs.getString("set"),
                Rarity.valueOf("rarity"),
                rs.getString("ruleText"),
               rs.getString("imageUrl")
        ),cardIds,userId);
        return cards;



    }
   @Override
    public Optional<User> findUserById(int userId) {
       String sql = "SELECT  firstName,lastName,username,password, email, role,  FROM users WHERE id = ?";

       List<User> user  = jdbcTemplate.query(sql, (rs, rowNum) -> new User(
               rs.getString("firstname"),
               rs.getString("lastname"),
               rs.getString("username"),
               rs.getString("password"),
               rs.getString("email"),
               Role.valueOf(rs.getString("role"))
       ), userId);

       return user.isEmpty() ? Optional.empty() : Optional.of(user.getFirst());




    }

}

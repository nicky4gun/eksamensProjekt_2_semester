package org.example.eksamensprojekt_2_semester.repositorys;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.enums.CardType;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.models.enums.Rarity;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICardRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CardRepository implements ICardRepository {

    private final JdbcTemplate jdbcTemplate;

    public CardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createCard(Card card) {
        String sql = "INSERT INTO CARD (name,  card_type,  color,  expansions,  rarity,  rule_text,  image_url) VALUES (?, ?, ?, ?, ?, ?, ?";
        return jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, card.getName());
            ps.setString(2, card.getCardType().toString());
            ps.setString(3, card.getColor().toString());
            ps.setString(4, card.getRarity().toString());
            ps.setString(5, card.getRuleText());
            ps.setString(6, card.getImageUrl());
            return ps;
        });
    }

    @Override
    public List<Card> findAll() {
        String sql = "SELECT id, name,  card_type,  color,  expansions,  rarity,  rule_text,  image_url FROM cards";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Card(
                rs.getInt("id"),
                rs.getString("name"),
                CardType.valueOf(rs.getString("card_type")),
                ManaColor.valueOf(rs.getString("color")),
                rs.getString("expansions"),
                Rarity.valueOf(rs.getString("rarity")),
                rs.getString("rule_text"),
                rs.getString("image_url")
        ));
    }


    @Override
    public void updateCard(Card card) {
        String sql = "UPDATE CARD set name = ?, card_type = ?, color = ?, rarity = ?, rule_text = ?, image_url = ? where id = ?";
        ;

        jdbcTemplate.update(sql, card.getName(), card.getCardType().toString(), card.getColor().toString(),
                card.getRuleText(), card.getRarity().toString(), card.getExpansions(), card.getImageUrl(), card.getId());
    }

    @Override
    public Card findCardById(int cardId) {
        String sql = "SELECT name, card_type, color,expaintions, rarity, rule_text, image_url,    FROM card WHERE id = ?";

        List<Card> card  = jdbcTemplate.query(sql, (rs, rowNum) -> new Card(
                rs.getString("name"),
                CardType.valueOf(rs.getString("card_type")),
                ManaColor.valueOf(rs.getString("color")),
                rs.getString("expansions"),
                Rarity.valueOf(rs.getString("rarity")),
                rs.getString("rule_text"),
                rs.getString("image_url")





        ), cardId);


        return null;
    }

}

package org.example.eksamensprojekt_2_semester.repositorys;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICardRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class CardRepository implements ICardRepository {

    private final JdbcTemplate jdbcTemplate;
    public CardRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public int createCard(Card card){
        String sql = "INSERT INTO CARD (name,  cardType,  color,  expansion,  rarity,  ruleText,  image_url) VALUES (?, ?, ?, ?, ?, ?, ?";
        return jdbcTemplate.update(con ->  {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, card.getName());
            ps.setString(2,card.getCardType().toString());
            ps.setString(3,card.getColor().toString());
            ps.setString(4,card.getRarity().toString());
            ps.setString(5,card.getRuleText());
            ps.setString(6,card.getImageUrl());
            return ps;
        });

    }
    @Override
    public void updateCard(Card card){
        String sql = "UPDATE CARD set name = ?, cardType = ?, color = ?, rarity = ?, ruleText = ?, image_url = ? where id = ?";

        jdbcTemplate.update(sql, card.getName(), card.getCardType().toString(), card.getColor().toString(),
                card.getRuleText(), card.getRarity().toString(), card.getSet(), card.getImageUrl(), card.getId());
    }

}

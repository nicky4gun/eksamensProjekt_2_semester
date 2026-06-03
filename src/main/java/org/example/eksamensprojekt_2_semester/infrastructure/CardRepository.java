package org.example.eksamensprojekt_2_semester.infrastructure;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.enums.CardType;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.models.enums.Rarity;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICardRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class CardRepository implements ICardRepository {

    private final JdbcTemplate jdbcTemplate;

    public CardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createCard(Card card) {
        String sql = "INSERT INTO cards (name,  card_type,  color,  expansions,  rarity,  rule_text,  image_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, card.getName());
                ps.setString(2, card.getCardType().toString());
                ps.setString(3, card.getColor().toString());
                ps.setString(4, card.getExpansions());
                ps.setString(5, card.getRarity().toString());
                ps.setString(6, card.getRuleText());
                ps.setString(7, card.getImageUrl());
                return ps;
            }, keyHolder);

        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke oprette kort med ID " + card.getId(), e);
        }

        return keyHolder.getKey().intValue();
    }

    @Override
    public List<Card> findAll() {
        String sql = "SELECT id, name,  card_type,  color,  expansions,  rarity,  rule_text,  image_url FROM cards";

        try {
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
        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke hente kort!", e);
        }
    }

    @Override
    public void updateCard(Card card) {
        String sql = "UPDATE cards set name = ?, card_type = ?, color = ?, rarity = ?, expansions = ?, rule_text = ?, image_url = ? where id = ?";

        try {
            jdbcTemplate.update(sql, card.getName(), card.getCardType().toString(), card.getColor().toString(),
                    card.getRarity().toString(), card.getExpansions(), card.getRuleText(), card.getImageUrl(), card.getId());
        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke opdatere kort med ID " + card.getId(), e);
        }
    }

    @Override
    public void deleteCard(int cardId) {
        String sql = "DELETE FROM cards WHERE id = ?";

        try {
            jdbcTemplate.update(sql, cardId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke hente kort!", e);
        }
    }

    @Override
    public Optional<Card> findById(int cardId) {
        String sql = "SELECT id, name, card_type, color, expansions, rarity, rule_text, image_url FROM cards WHERE id = ?";

        try {
            Card card = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Card(
                    rs.getInt("id"),
                    rs.getString("name"),
                    CardType.valueOf(rs.getString("card_type")),
                    ManaColor.valueOf(rs.getString("color")),
                    rs.getString("expansions"),
                    Rarity.valueOf(rs.getString("rarity")),
                    rs.getString("rule_text"),
                    rs.getString("image_url")
            ), cardId);

            return Optional.of(card);
        } catch (Exception e){
            return Optional.empty();
        }
    }
}

package org.example.eksamensprojekt_2_semester.repositorys;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Deck;
import org.example.eksamensprojekt_2_semester.models.enums.CardType;
import org.example.eksamensprojekt_2_semester.models.enums.Format;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.models.enums.Rarity;
import org.example.eksamensprojekt_2_semester.models.interfaces.IDeckRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class DeckRepository implements IDeckRepository {

    private final JdbcTemplate jdbcTemplate;

    public DeckRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createDeck(Deck deck) {
        String sql = "INSERT INTO decks (deck_name, format, user_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, deck.getDeckName());
                ps.setString(2, deck.getFormat().name());
                ps.setInt(3, deck.getUserId());
                return ps;
            }, keyHolder);

        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke oprette deck, Prøv igen", e);
        }

        return keyHolder.getKey().intValue();
    }

    @Override
    public void addCardToDeck(int deckId, int cardId) {
        String sql = "INSERT INTO deck_cards (deck_id, card_id) VALUES (?, ?)";

        try {
            jdbcTemplate.update(sql, deckId, cardId);

        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke tilføje kort med ID " + cardId + " til deck", e);
        }
    }

    @Override
    public List<Deck> findDecksByUserId(int userId) {
        String sql = "SELECT id, deck_name, format, user_id FROM decks WHERE user_id = ?";

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new Deck(
                    rs.getInt("id"),
                    rs.getString("deck_name"),
                    Format.valueOf(rs.getString("format")),
                    rs.getInt("user_id")
            ), userId);

        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke finde nogle decks med bruger ID "+ userId, e);
        }
    }

    @Override
    public Optional<Deck> findDeckById(int deckId) {
        String sql = "SELECT id, deck_name, format, user_id FROM decks WHERE id = ?";

        try {
            List<Deck> decks = jdbcTemplate.query(sql, (rs, rowNum) -> new Deck(
                    rs.getInt("id"),
                    rs.getString("deck_name"),
                    Format.valueOf(rs.getString("format")),
                    rs.getInt("user_id")
            ), deckId);

            return decks.isEmpty() ? Optional.empty() : Optional.of(decks.getFirst());
        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke finde deck med ID " + deckId, e);
        }
    }

    @Override
    public List<Card> findAllCards(int deckId) {
        String sql = """
                        SELECT c.id, c.name,  c.card_type,  c.color,  c.expansions,  c.rarity,  c.rule_text,  c.image_url FROM cards c
                        JOIN deck_cards dc ON c.id = dc.card_id
                        JOIN decks d ON d.id = dc.deck_id
                        WHERE d.id = ?""";

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
            ), deckId);

        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke finde kort i deck med ID " + deckId, e);
        }
    }

    @Override
    public void updateDeck(Deck deck) {
        String sql = "UPDATE decks SET deck_name = ?, format = ? WHERE id = ?";

        try {
            jdbcTemplate.update(sql, deck.getDeckName(), deck.getFormat().name(), deck.getId());

        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke opdatere deck, Prøv igen", e);
        }
    }

    @Override
    public void removeCardFromDeck(int cardId, int deckId) {
        String sql = "DELETE FROM deck_cards WHERE card_id = ? AND deck_id = ?";

        try {
            jdbcTemplate.update(sql, cardId, deckId);

        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke fjerne kort med ID " + cardId + " fra deck med ID " + deckId, e);
        }
    }

    @Override
    public void deleteDeck(int deckId) {
        String sql = "DELETE FROM decks WHERE id = ? ";

        try {
            jdbcTemplate.update(sql, deckId);

        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke slette deck med ID " + deckId, e);
        }
    }

    @Override
    public List<Deck> getDecksByUserIdOnly5(Integer userId) {
        String sql = "SELECT id, deck_name, format, user_id FROM decks WHERE user_id = ? LIMIT 5";

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new Deck(
                    rs.getInt("id"),
                    rs.getString("deck_name"),
                    Format.valueOf(rs.getString("format")),
                    rs.getInt("user_id")
            ), userId);

        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke finde nogle decks med bruger ID "+ userId, e);
        }

    }
}


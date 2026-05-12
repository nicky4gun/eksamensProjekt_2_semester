package org.example.eksamensprojekt_2_semester.repositorys;

import org.example.eksamensprojekt_2_semester.models.Deck;
import org.example.eksamensprojekt_2_semester.models.enums.Format;
import org.example.eksamensprojekt_2_semester.models.interfaces.IDeckRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DeckRepository implements IDeckRepository {

    private final JdbcTemplate jdbcTemplate;

    public DeckRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createDeck(Deck deck) {
        String sql = "INSERT INTO decks (deck_name, format, user_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(sql, deck.getDeckName(), deck.getFormat().name(), deck.getUserId(), keyHolder);

        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke oprette deck, Prøv igen", e);
        }

        deck.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void addCardToDeck(int deckId, int cardId) {
        String sql = "INSERT INTO deck_cards (deck_id, card_id) VALUES (?, ?)";

        try {
            jdbcTemplate.update(sql, deckId, cardId);

        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke tilføje kort til deck, Prøv igen", e);
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
            throw new RuntimeException("Kunne ikke finde nogle decks, Prøv igen", e);
        }
    }

    @Override
    public Optional<Deck> findDeckById(int deckId) {
        String sql = "SELECT id, deck_name, format, user_id FROM decks WHERE deck_id = ?";

        List<Deck> decks  = jdbcTemplate.query(sql, (rs, rowNum) -> new Deck(
                rs.getInt("id"),
                rs.getString("deck_name"),
                Format.valueOf(rs.getString("format")),
                rs.getInt("user_id")
        ), deckId);

        return decks.isEmpty() ? Optional.empty() : Optional.of(decks.getFirst());
    }

    @Override
    public void removeCardFromDeck(int cardId, int deckId) {
        String sql = "DELETE FROM deck_cards WHERE card_id = ? AND deck_id = ?";
        jdbcTemplate.update(sql, cardId, deckId);
    }

    @Override
    public void deleteDeck(int deckId) {
        String sql = "DELETE FROM deck WHERE id = ? ";
        jdbcTemplate.update(sql,deckId);
    }
}


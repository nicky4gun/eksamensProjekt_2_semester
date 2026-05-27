package org.example.eksamensprojekt_2_semester.infrastructure;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Collection;
import org.example.eksamensprojekt_2_semester.models.enums.CardType;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.models.enums.Rarity;
import org.example.eksamensprojekt_2_semester.models.enums.Visibility;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICollectionRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CollectionRepository implements ICollectionRepository {

    private final JdbcTemplate jdbcTemplate;

    public CollectionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createCollection(int userId, Visibility visibility) {
        String sql = "INSERT INTO collections (user_id, visibility) VALUES (?, ?)";

        try {
            jdbcTemplate.update(sql, userId, visibility.name());

        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke oprette collection for bruger med ID " + userId, e);
        }
    }

    @Override
    public void addCard(int collectionId, Integer cardId) {
        String sql = "INSERT INTO collection_cards (collection_id, card_id) VALUES (?, ?)";

        try {
            jdbcTemplate.update(sql, collectionId, cardId);

        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke tilføje kort med ID " + cardId +
                    " til collection med ID " + collectionId, e);
        }
    }

    @Override
    public Optional<Collection> findByUserId(int userId) {
        String sql = "SELECT id, user_id, visibility FROM collections WHERE user_id = ?";

        try {
            List<Collection> collections = jdbcTemplate.query(sql, (rs, rowNum) -> new Collection(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    Visibility.valueOf(rs.getString("visibility"))
            ), userId);

            return collections.isEmpty() ? Optional.empty() : Optional.of(collections.getFirst()) ;

        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke finde samling for bruger med ID " + userId, e);
        }
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
    public List<Card> findAllCards(int collectionId) {
        String sql = """
                SELECT c.id, c.name,  c.card_type,  c.color,  c.expansions,  c.rarity,  c.rule_text,  c.image_url
                FROM cards c
                JOIN collection_cards cc ON c.id = cc.card_id
                JOIN collections col ON col.id = cc.collection_id
                WHERE col.id = ?
                """;

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
            ), collectionId);

        } catch (DataAccessException e) {
            throw new RuntimeException("Kunne ikke finde nogle kort for samling med ID " + collectionId, e);
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
    public List<Card> findTheFirst10Cards(int collectionId) {
        String sql = """
                SELECT c.id, c.name,  c.card_type,  c.color,  c.expansions,  c.rarity,  c.rule_text,  c.image_url
                FROM cards c
                JOIN collection_cards cc ON c.id = cc.card_id
                JOIN collections col ON col.id = cc.collection_id
                WHERE col.id = ?
                LIMIT 10
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Card(
            rs.getInt("id"),
            rs.getString("name"),
            CardType.valueOf(rs.getString("card_type")),
            ManaColor.valueOf(rs.getString("color")),
            rs.getString("expansions"),
            Rarity.valueOf(rs.getString("rarity")),
            rs.getString("rule_text"),
            rs.getString("image_url")
        ),collectionId);
    }
}

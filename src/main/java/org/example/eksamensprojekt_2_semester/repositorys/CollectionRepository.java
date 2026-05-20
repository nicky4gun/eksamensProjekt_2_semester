package org.example.eksamensprojekt_2_semester.repositorys;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Collection;
import org.example.eksamensprojekt_2_semester.models.enums.CardType;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.models.enums.Rarity;
import org.example.eksamensprojekt_2_semester.models.enums.Visibility;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICollectionRepository;
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
        jdbcTemplate.update(sql, userId, visibility.name());
    }

    @Override
    public void addCard(int collectionId, Integer cardId) {
        String sql = "INSERT INTO collection_cards (collection_id, card_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, collectionId, cardId);
    }

    @Override
    public Optional<Collection> findById(int collectionId) {
        String sql = "SELECT id, user_id, visibility FROM collections WHERE id = ?";

        List<Collection> collections = jdbcTemplate.query(sql, (rs, rowNum) -> new Collection(
                rs.getInt("id"),
                rs.getInt("user_id"),
                Visibility.valueOf(rs.getString("visibility"))
        ), collectionId);

        return collections.isEmpty() ? Optional.empty() : Optional.of(collections.getFirst()) ;
    }

    @Override
    public Optional<Collection> findByUserId(int userId) {
        String sql = "SELECT id, user_id, visibility FROM collections WHERE user_id = ?";
        List<Collection> collections = jdbcTemplate.query(sql, (rs, rowNum) -> new Collection(
                rs.getInt("id"),
                rs.getInt("user_id"),
                Visibility.valueOf(rs.getString("visibility"))
        ), userId);

        return collections.isEmpty() ? Optional.empty() : Optional.of(collections.getFirst()) ;
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
    }
}

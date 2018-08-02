package ru.whalekit.likesapp.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import ru.whalekit.likesapp.domain.Player;

import java.util.Optional;

@Repository
public class MongoPlayerDao implements PlayerDao {
    public static final String COLLECTION_NAME = "player";
    private final MongoTemplate template;
    private final Update update = new Update();

    public MongoPlayerDao(MongoTemplate template) {
        this.template = template;
        update.inc("count", 1);
    }

    public long like(String playerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(playerId));
        return template.updateFirst(query, update, Player.class, COLLECTION_NAME).getModifiedCount();
    }

    public Optional<Player> find(String playerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(playerId));
        return Optional.ofNullable(template.findOne(query, Player.class, COLLECTION_NAME));
    }
}

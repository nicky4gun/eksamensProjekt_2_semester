package org.example.eksamensprojekt_2_semester.services;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.models.enums.Role;
import org.example.eksamensprojekt_2_semester.models.enums.Visibility;
import org.example.eksamensprojekt_2_semester.models.exceptions.CardNotFoundException;
import org.example.eksamensprojekt_2_semester.models.exceptions.UserNotFoundException;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICardRepository;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICollectionRepository;
import org.example.eksamensprojekt_2_semester.models.interfaces.IUserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final IUserRepository userRepository;
    private final ICardRepository cardRepository;
    private final ICollectionRepository collectionRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public UserService(IUserRepository userRepository, ICardRepository cardRepository, ICollectionRepository collectionRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.collectionRepository = collectionRepository;
    }

    public void removeUser(int id) {
        userRepository.removeUser(id);
    }

    public Card findCardByUserId(int userId, int cardId) {
        return userRepository.findCardByUserId(userId, cardId).orElseThrow(
                () -> new CardNotFoundException("Kort med ID " + cardId + " ikke fundet i brugerens samling!")
        );
    }

    public User findUserById(int userId) {
        return userRepository.findUserById(userId).orElseThrow(
                () -> new UserNotFoundException("Ingen bruger med ID " + userId + " fundet!"));
    }

    public List<Card> getFavoriteCards(int userId) {
        return userRepository.getFavorites(userId);
    }

    public List<Card> findCardsByUserId(int userId) {
        return userRepository.findCardsByUserId(userId);
    }

    public List<Card> searchCards(List<Card> cards, String query) {
        if (query == null || query.isEmpty()) return cards;
        return cards.stream().filter(c -> c.getName().toLowerCase()
                .contains(query.toLowerCase())).toList();
    }

    public List<Card> filterCardsByColor(List<Card> cards, List<ManaColor> colors) {
        if (colors == null || colors.isEmpty()) return cards;
        return cards.stream().filter(c -> colors.contains(c.getColor())).toList();
    }

    public void addFavoriteCard(int userId, int cardId) {
        userRepository.saveFavorites(userId, cardId);
    }

    public void removeFavoriteCard(int userId, int cardId) {
        findUserById(userId);
        cardRepository.findCardById(cardId);

        userRepository.removeFavorites(userId, cardId);
    }

    public void registerUser(String firstname, String lastname, String username, String email, String password, Role role, String image) {
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(firstname, lastname, username, email, hashedPassword, role, image);
        int userId = userRepository.createUser(user);
        collectionRepository.createCollection(userId, Visibility.PRIVATE);
    }

    public User loginUser(String username, String password) {
        User existingUser = userRepository.findUserByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Ingen bruger fundet!"));

        if (passwordEncoder.matches(password, existingUser.getPassword())) {
            return existingUser;
        }

        return null;
    }

    public void updateUser(int id, String username, String email, String password) {
        if (id <= 0) {
            throw new IllegalArgumentException("Ugyldigt bruger ID, Prøv igen!");
        }

        String hashedPassword = passwordEncoder.encode(password);
        userRepository.updateUser(id, username, email, hashedPassword);
    }

    public List<Card>  getFavoriteCardsLimitBy10(Integer userId) {
        return userRepository.getFavoritesLimitBy10(userId);



    }
}

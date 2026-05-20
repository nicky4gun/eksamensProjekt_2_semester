package org.example.eksamensprojekt_2_semester.services;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.models.enums.Role;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICardRepository;
import org.example.eksamensprojekt_2_semester.models.interfaces.IUserRepository;
import org.example.eksamensprojekt_2_semester.repositorys.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final IUserRepository userRepository;
    private final ICardRepository cardRepository;

    public UserService(IUserRepository userRepository, ICardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.collectionRepository = collectionRepository;
    }

    public void removeUser(int id) {
        userRepository.removeUser(id);
    }

    public Optional<Card> findCardByUserId(int userId, int cardId) {
        return userRepository.findCardByUserId(userId, cardId);
    }

    public Optional<User> findUserById(int userId) {
        return userRepository.findUserById(userId);
    }

    public List<Card> getFavoriteCards(int userId) {
        User user = userRepository.findUserById(userId).orElseThrow();
        return user.getFavoriteCards();
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
    public void removeFavoriteCard(int userId,int cardId) {
        findUserById(userId).orElseThrow();
        cardRepository.findCardById(cardId);

        userRepository.removeFavorites(userId,cardId);
    }
    public void registerUser(String firstname, String lastname, String username, String email, String password, Role role, String image) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        User user = new User(firstname,lastname,username,email,hashedPassword,role,image);
        userRepository.createUser(user);
    }

    public User loginUser(String username, String password) {
        User existingUser = userRepository.findUserByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Ingen bruger fundet!"));

        if (existingUser != null && passwordEncoder.matches(password, existingUser.getPassword())) {
            return existingUser;
        }

        return null;
    }

    public void updateUser(int id, String username, String email, String password) {
        if (id <= 0) {
            throw new IllegalArgumentException("Uyldigt bruger ID, Prøv igen!");
        }

        String hashedPassword = passwordEncoder.encode(password);
        userRepository.updateUser(id, username, email, hashedPassword);
    }
}

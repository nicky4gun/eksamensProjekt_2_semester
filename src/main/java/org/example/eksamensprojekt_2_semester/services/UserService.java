package org.example.eksamensprojekt_2_semester.services;

import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.models.enums.Role;
import org.example.eksamensprojekt_2_semester.models.enums.Visibility;
import org.example.eksamensprojekt_2_semester.models.exceptions.UserNotFoundException;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICollectionRepository;
import org.example.eksamensprojekt_2_semester.models.interfaces.IUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final IUserRepository userRepository;
    private final ICollectionRepository collectionRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public UserService(IUserRepository userRepository, ICollectionRepository collectionRepository) {
        this.userRepository = userRepository;
        this.collectionRepository = collectionRepository;
    }

    public User findUserById(int userId) {
        return userRepository.findUserById(userId).orElseThrow(
                () -> new UserNotFoundException("Ingen bruger med ID " + userId + " fundet!"));
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

    public void removeUser(int id) {
        userRepository.removeUser(id);
    }
}

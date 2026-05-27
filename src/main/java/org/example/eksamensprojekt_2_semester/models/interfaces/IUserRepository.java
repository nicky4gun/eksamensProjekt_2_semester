package org.example.eksamensprojekt_2_semester.models.interfaces;

import org.example.eksamensprojekt_2_semester.models.User;

import java.util.Optional;

public interface IUserRepository  {
    int createUser(User user);

    Optional<User> findUserById(int userId);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    void updateUser(int id, String username, String email, String password);

    void removeUser(int id);
}

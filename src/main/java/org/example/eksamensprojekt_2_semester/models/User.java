package org.example.eksamensprojekt_2_semester.models;

import org.example.eksamensprojekt_2_semester.models.enums.Role;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Role role;

    public User() {}

    public User(String firstName, String lastName, String username, String password, String email, Role role) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Angiv venligst et brugernavn!");
        }

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Angiv venligst en adgangskode!");
        }

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Angiv venligst en email!");
        }

        if (role == null) {
            throw new IllegalArgumentException("Ugyldig rolle!");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(int id, String firstName, String lastName, String username, String password, String email, Role role) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Angiv venligst et brugernavn!");
        }

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Angiv venligst en adgangskode!");
        }

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Angiv venligst en email!");
        }

        if (role == null) {
            throw new IllegalArgumentException("Ugyldig rolle!");
        }

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public boolean isNotAdmin() {
        return getRole() != Role.ADMIN;
    }
}

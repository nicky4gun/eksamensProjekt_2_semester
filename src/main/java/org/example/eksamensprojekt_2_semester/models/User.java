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

    public User(String firstName, String lastName, String username, String password, String email, Role role ) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if (password == null || password.isEmpty() ) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (email == null || email.isEmpty() ) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (role == null) {
            throw new IllegalArgumentException("Invalid role");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;

    }
    public User( int id, String firstName, String lastName, String username, String password, String email, Role role) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if (password == null || password.isEmpty() ) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (email == null || email.isEmpty() ) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (role == null) {
            throw new IllegalArgumentException("Invalid role");
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}

package hr.algebra.model;

import hr.algebra.utilities.PasswordHasher;

import java.util.Objects;

public class User extends BaseClass<User> {
    private String username;
    private String passwordHash;
    private Role role;


    public User(String username, String passwordHash, Role role) {
        super(null);
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }


    public User(Long id, String username, String passwordHash, Role role) {
        super(id);
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    @Override
    public int compareTo(User other) {
        return this.username.compareTo(other.getUsername());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(passwordHash, user.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, passwordHash);
    }

    @Override
    public String toString() {
        return username;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .id(getId())
                .username(username)
                .passwordHash(passwordHash)
                .role(role);
    }

    public static class Builder {
        private Long id;
        private String username;
        private String passwordHash;
        private Role role = Role.USER;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        /** Hashes the plain-text password, so only the salted hash ends up in the user. */
        public Builder password(String password) {
            this.passwordHash = PasswordHasher.hash(password);
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            if (username == null || username.isBlank()) {
                throw new IllegalStateException("Username is required.");
            }
            if (passwordHash == null || passwordHash.isBlank()) {
                throw new IllegalStateException("Password is required.");
            }
            return new User(id, username, passwordHash, role);
        }
    }
}

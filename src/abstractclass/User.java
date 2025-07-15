package abstractclass;

public abstract class User {
    protected String username;
    protected String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public abstract void showMenu();

    public String getUsername() { return username; }
    public String getRole() { return role; }
}

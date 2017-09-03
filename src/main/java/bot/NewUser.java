package bot;


import org.telegram.telegrambots.api.objects.User;

public class NewUser {
    private Integer id;
    private String firstName;
    private String lastName;
    private String userName;

    public NewUser() {
    }

    public Integer getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewUser newUser = (NewUser) o;

        if (!id.equals(newUser.id)) return false;
        if (firstName != null ? !firstName.equals(newUser.firstName) : newUser.firstName != null) return false;
        if (lastName != null ? !lastName.equals(newUser.lastName) : newUser.lastName != null) return false;
        return userName != null ? userName.equals(newUser.userName) : newUser.userName == null;
    }

    public String toString() {
        return "User{id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName + '\'' + ", userName='" + this.userName + '\'' + '}';
    }
}



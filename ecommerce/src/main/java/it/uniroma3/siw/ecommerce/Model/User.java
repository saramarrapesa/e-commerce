package it.uniroma3.siw.ecommerce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.CollectionId;
import it.uniroma3.siw.ecommerce.Model.Role;

import java.util.List;

@Entity
@Table(name = "users") // cambiamo nome perchè in postgres user è una parola riservata
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String firstName;
   private String lastName;

   @Column(nullable = false, unique = true)
    @NotEmpty
    @Email(message = "{errors.invalid_email}")
    private String email;

   private String password;
   @ManyToMany(cascade = CascadeType.MERGE , fetch = FetchType.EAGER)
   @JoinTable(
           name = "user_role",
           joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
               inverseJoinColumns = {@JoinColumn(name = "ROLE_ID",referencedColumnName ="ID")}
   )
   private List<Role> roles;

    public User(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }

    public User(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}

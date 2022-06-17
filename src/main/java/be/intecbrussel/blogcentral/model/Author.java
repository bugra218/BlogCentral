package be.intecbrussel.blogcentral.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "first_name")
    @NotBlank(message = "First name is required")
    @Size(min = 1, max = 20, message = "First name can not be less than " +
            "1 or greater than 20 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name is required")
    @Size(min = 1, max = 45, message = "Last name can not be less than " +
            "1 or greater than 45 characters")
    private String lastName;

    @Column(name = "user_name", unique = true)
    @NotBlank(message = "User name is required")
    @Size(min = 1, max = 20, message = "Username can not be less than 1 " +
            "or greater than 45 characters")
    private String userName;

    @NotBlank(message = "Please enter a password")
    private String password;

    @NotBlank(message = "Email is required")
//    @Size(min = 5, max = 45, message = "Your email can not be less than 3 " +
//            "character or greater than 45")
//    @Email(message = "Invalid Email format")
    @Pattern(regexp = "^(.+)@(\\S+)$", message="Invalid email format")
    private String email;


//    @NotBlank(message = "Please enter your street using letters")
    @Size(max = 45, message = "Street can not be more 45 characters")
    private String street;

//    @NotBlank(message = "Please enter your house nr using letters")
    @Column(name = "house_number")
    @Size(max = 9, message = "House number can not be more than 9 characters")
    private String houseNr;

//    @NotBlank(message = "Please enter your city using letters")
    @Size(max = 45, message = "City can not be more than 45 characters")
    private String city;

    // TODO
    @Max(999999)
    private int zip;

    @Column(name = "avatar_path")
    private String avatarPath = "/images/default.png";

}

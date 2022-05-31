package be.intecbrussel.blogcentral.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="user_name")
    private String userName;

    private String password;

    private String email;

    private String street;

    @Column(name="house_number")
    private String houseNr;

    private String city;
    private int zip;

    @Column(name="avatar_path")
    private String avatarPath;

//    @Column(name="blog_posts")
//    private List<BlogPost> blogPosts;

}

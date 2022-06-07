package be.intecbrussel.blogcentral.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "Likes")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="author_id") // this way the id from author is taken
    // over in a column in BlogPost table as foreign key
    private Author author;

    @ManyToOne
    @JoinColumn(name = "blogpost_id")
    private BlogPost blogPost;
}

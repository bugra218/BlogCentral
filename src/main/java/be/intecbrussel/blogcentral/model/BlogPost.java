package be.intecbrussel.blogcentral.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor // gives an issue as this requires id should be
//// provided
@ToString
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne // is this the indication to indicate a foreign key?
    @JoinColumn(name="author_id") // this way the id from author is taken
    // over in a column in BlogPost table as foreign key
    private Author author;

    private String title;

    private String body;

    @CreationTimestamp
    @Column(name="timestamp_created")
    private Date timestampCreated;

//    @OneToMany
//    @Column(name="comment_list")
//    private List<Comment> commentList;

}

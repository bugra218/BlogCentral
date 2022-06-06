package be.intecbrussel.blogcentral.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor // gives an issue as this requires id should be
//// provided
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="blog_post_id")
    private BlogPost blogPost;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;

    @Column(name="body")
    private String body;

    @CreationTimestamp
    @Column(name="timestamp_created")
    private Date timestampCreated;

}

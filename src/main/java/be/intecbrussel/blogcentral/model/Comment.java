package be.intecbrussel.blogcentral.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "Please enter your content using letters")
    private String body;

    @CreationTimestamp
    @Column(name="timestamp_created")
    private Timestamp timestampCreated;

    @Column(name ="timestamp_created_display")
    private String timestampCreatedDisplay;

    @CreationTimestamp
    @Column(name="timestamp_updated")
    private Timestamp timestampUpdated;

    @Column(name ="timestamp_updated_display")
    private String timestampUpdatedDisplay;

}

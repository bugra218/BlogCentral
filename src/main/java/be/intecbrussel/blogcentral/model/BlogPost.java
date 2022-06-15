package be.intecbrussel.blogcentral.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;

    @NotBlank(message = "Please give your post a title")
    @Size(min = 3, max = 40, message = "The title should be at least 3 character and at last 40.")
    private String title;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "Please enter your content using letters")
    @Size(max = 4000)
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

    @ManyToMany()
    @JoinColumn(name="tag_id")
    private List<Tag> tags;
}

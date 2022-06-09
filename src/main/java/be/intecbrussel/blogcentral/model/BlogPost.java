package be.intecbrussel.blogcentral.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;

    private String title;

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

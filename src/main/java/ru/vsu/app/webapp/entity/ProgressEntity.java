package ru.vsu.app.webapp.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "progress", schema = "vsu_java")
@Data
public class ProgressEntity implements BaseEntity{

    @Id
    @GeneratedValue(generator = "custom_progress_seq")
    @GenericGenerator(name = "custom_progress_seq",
            strategy = "ru.vsu.app.webapp.sequences.ProgressIdGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "resource_id")
    private ResourceEntity resource;

    @Column(name = "score")
    private Long score;

    @Column(name = "max_score")
    private Long maxScore;

    public ProgressEntity(Long id) {
        this.id = id;
    }

    public ProgressEntity() {}
}

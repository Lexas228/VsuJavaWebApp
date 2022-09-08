package ru.vsu.app.webapp.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "item", schema = "vsu_java")
@Data
public class ItemEntity implements BaseEntity{

    @Id
    @GeneratedValue(generator = "custom_item_seq")
    @GenericGenerator(name = "custom_item_seq",
            strategy = "ru.vsu.app.webapp.sequences.ItemIdGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "resource_id")
    private Long resourceId;

    @Column(name = "count")
    private Long count;

    @Column(name = "level")
    private Integer level;

    public ItemEntity(Long id) {this.id = id;}

    public ItemEntity() {}
}

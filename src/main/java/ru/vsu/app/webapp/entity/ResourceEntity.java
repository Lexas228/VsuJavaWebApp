package ru.vsu.app.webapp.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "resource", schema = "vsu_java")
@Data
public class ResourceEntity implements BaseEntity {

    @Id
    @GeneratedValue(generator = "custom_resource_seq")
    @GenericGenerator(name = "custom_resource_seq",
            strategy = "ru.vsu.app.webapp.sequences.ResourceIdGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "resource")
    private List<CurrencyEntity> currencies;

    @OneToMany(mappedBy = "resource")
    private List<ProgressEntity> progresses;

    @OneToMany(mappedBy = "resource")
    private List<ItemEntity> items;

    public ResourceEntity(Long id) {this.id = id;}
    public ResourceEntity(){}
}

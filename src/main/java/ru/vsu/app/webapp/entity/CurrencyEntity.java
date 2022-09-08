package ru.vsu.app.webapp.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "currency", schema = "vsu_java")
@Data
public class CurrencyEntity implements BaseEntity{
    @Id
    @GeneratedValue(generator = "custom_currency_seq")
    @GenericGenerator(name = "custom_currency_seq",
            strategy = "ru.vsu.app.webapp.sequences.CurrencyIdGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "resource_id")
    private ResourceEntity resource;

    @Column(name = "name")
    private String name;

    @Column(name = "count")
    private Long count;

    public CurrencyEntity(Long id){this.id = id;}
    public CurrencyEntity(){}
}

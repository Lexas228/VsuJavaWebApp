package ru.vsu.app.webapp.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "player", schema = "vsu_java")
@Data
public class PlayerEntity implements BaseEntity{

    @Id
    @GeneratedValue(generator = "custom_player_seq")
    @GenericGenerator(name = "custom_player_seq",
            strategy = "ru.vsu.app.webapp.sequences.PlayerIdGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname")
    private String nickName;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<ProgressEntity> progresses;

    @ManyToMany( cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE,
    })
    @JoinTable(joinColumns = {@JoinColumn(name = "player_id")},
    inverseJoinColumns = {@JoinColumn(name = "currency_id")},
    name = "currency_player_map",
    schema = "vsu_java")
    private List<CurrencyEntity> currencies;

    @ManyToMany( cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE,
    })
    @JoinTable(joinColumns = {@JoinColumn(name = "player_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")},
            name = "item_player_map",
            schema = "vsu_java")
    private List<ItemEntity> items;

    public PlayerEntity(Long id) {this.id = id;}

    public PlayerEntity() {}
}

package dev.ricardo.movies.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="producer")
@Getter
@Setter
public class ProducerEntity {

    @Id
    @Column(name = "producer_id")
    private UUID producerId;

    @Column(name="name", length=100, nullable=false)
    private String name;

}

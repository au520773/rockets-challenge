package com.backend.challenge.rockets.entity;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Rocket")
@Table(name = "rockets")
public class Rocket {

    @Id
    @Column(name = "channel")
    String channel;

    @Column(name = "status")
    String status;

    @Column(name = "type")
    String type;

    @Column(name = "speed")
    int speed;

    @Column(name = "mission")
    String mission;
}

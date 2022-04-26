package com.alkemy.ong.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;


@Entity
@Getter @Setter
@Table(name="categories")
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE categories SET softDelete = true WHERE id=?")
@Where(clause = "softDelete=false")
public class Categories {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    @org.hibernate.annotations.Type(type="uuid-char")
    private UUID id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "description")
    private String description;

    @NonNull
    @Column(name = "image")
    private String image;

    @NonNull
    @Column(name = "timestamps")
    //@Temporal(TemporalType.DATE)
    private Timestamp timestamp = Timestamp.from(Instant.now());

    @NonNull
    @Column(name = "softDelete")
    private Boolean softDelete = Boolean.FALSE;


}

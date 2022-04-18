package com.alkemy.ong.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;


@Entity
@Table(name="categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE categories SET softDelete = true WHERE id=?")
@Where(clause = "softDelete=false")
public class Categories {

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy="uuid2")
    @Column(name = "ID")
    private String id;

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
    @Temporal(TemporalType.DATE)
    private Timestamp timestamp = Timestamp.from(Instant.now());

    @NonNull
    @Column(name = "softDelete")
    private Boolean softDelete = Boolean.FALSE;


}

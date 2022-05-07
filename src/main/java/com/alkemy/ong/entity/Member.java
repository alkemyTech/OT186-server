package com.alkemy.ong.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "members")
@Getter
@Setter
@SQLDelete(sql = "UPDATE members SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Member {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @org.hibernate.annotations.Type(type="uuid-char")
    private UUID id;

    private boolean deleted = Boolean.FALSE;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "facebook_Url")
    private String facebookUrl;

    @Column(name = "instagram_Url")
    private String instagramUrl;

    @Column(name = "linkedin_Url")
    private String linkedinUrl;

    @Column(name = "image", nullable = false)
    private String image;

    private String description;

    @Column(nullable = false)
    //@Temporal(TemporalType.DATE)
    private Timestamp timestamps = Timestamp.from(Instant.now());

}
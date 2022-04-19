package com.alkemy.ong.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

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
    private String id;

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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

}

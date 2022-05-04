package com.alkemy.ong.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "organization")
@SQLDelete(sql = "UPDATE organization SET soft_delete = true WHERE id=?")
@Where(clause = "soft_delete=false")
@NoArgsConstructor
@AllArgsConstructor

public class Organization {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @org.hibernate.annotations.Type(type="uuid-char")
    private UUID id;

    private String name;

    private String image;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer phone;

    private String email;

    private String welcomeText;

    @Column(nullable = false)
    private Timestamp timestamps;

    @Column(nullable = false)
    private String aboutUsText;

    private Boolean softDelete = Boolean.FALSE;
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Organization other = (Organization) obj;
        return Objects.equals(id, other.id);
    }

    private String facebook;

    private String linkedin;

    private String instagram;

}
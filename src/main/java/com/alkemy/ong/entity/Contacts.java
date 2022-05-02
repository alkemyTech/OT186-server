package com.alkemy.ong.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "contacts")
@SQLDelete(sql = "UPDATE contacts SET deleted_at = true WHERE id=?")
@Where(clause = "deleted_at = false")
@NoArgsConstructor
@AllArgsConstructor
public class Contacts {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @org.hibernate.annotations.Type(type="uuid-char")
    private UUID id;

    private String name;

    private Integer phone;

    private String email;

    private String message;

    @Column(name = "deleted_at")
    private Boolean deletedAt = Boolean.FALSE;

}

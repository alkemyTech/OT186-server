package com.alkemy.ong.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "contacts")
@SQLDelete(sql = "UPDATE contacts SET deletedAt = true WHERE id=?")
@Where(clause = "deletedAt=false")
@NoArgsConstructor
@AllArgsConstructor
public class Contacts {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @org.hibernate.annotations.Type(type="uuid-char")
    private UUID id;

    @NotEmpty(message = "Please provide a name")
    private String name;

    private Integer phone;

    @NotEmpty(message = "Please provide a email")
    private String email;

    private String message;

    private Boolean deletedAt = Boolean.FALSE;

}

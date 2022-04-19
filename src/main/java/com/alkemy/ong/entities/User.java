package com.alkemy.ong.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET softDelete = true WHERE id=?")
@Where(clause = "softDelete=false")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NonNull
    private String  firstName; //NOT NULL

    @NonNull
    private String lastName; // NOT NULL

    @NonNull
    private String email; // UNIQUE NOT NULL

    @NonNull
    private String password; // NOT NULL

    @Nullable
    private String photo; // NULLABLE

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @NonNull
    @Column(name = "softDelete")
    private Boolean softDelete = Boolean.FALSE;

}



package com.alkemy.ong.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET soft_delete = true WHERE id=?")
@Where(clause = "soft_delete=false")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role roles;

    @NonNull
    @Column(name ="first_name")
    private String  firstName;

    @NonNull
    @Column(name ="last_name")
    private String lastName;

    @NonNull
    @Column(name ="email")
    private String email;

    @NonNull
    @Column(name ="password")
    private String password;

    @Nullable
    @Column(name ="photo")
    private String photo;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @NonNull
    @Column(name = "soft_delete")
    private Boolean softDelete = Boolean.FALSE;

}



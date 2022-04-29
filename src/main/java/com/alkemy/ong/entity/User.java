package com.alkemy.ong.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import javax.persistence.*;
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
    @org.hibernate.annotations.Type(type="uuid-char")
    private UUID id;

<<<<<<< HEAD
    @OneToOne(optional = false)
=======
    @ManyToOne
>>>>>>> origin/main
    @JoinColumn(name = "id_role")
    private Role roles;

    @NonNull
    private String  firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @Nullable
    private String photo;

    @Column(name = "created_at")
    //@Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @NonNull
    @Column(name = "soft_delete")
    private Boolean softDelete = Boolean.FALSE;

}



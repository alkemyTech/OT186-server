package com.alkemy.ong.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "slide")
public class Slide {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @org.hibernate.annotations.Type(type="uuid-char")
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;
    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "order_", nullable = false)
    private Long order;
    @Column(name ="organization_id", nullable = false)
    private Long organizationId;

}

package com.alkemy.ong.entity;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "news")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@SQLDelete(sql = "UPDATE news SET soft_delete = true WHERE id=?")
@Where(clause = "soft_delete=false")
public class News {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "image", nullable = false)
	private String image;

	@Column(name = "soft_delete")
	private Boolean softDelete = Boolean.FALSE;

	@Column(name = "created_at")
	private Timestamp timestamp = Timestamp.from(Instant.now());

	@ManyToOne
	@JoinColumn(name="categories_id")
	private Categories categories;
		
}

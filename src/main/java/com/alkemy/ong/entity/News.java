package com.alkemy.ong.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "news")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class News {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;
	
	@NonNull
	private String name;
	
	@NonNull
	private String content;
	
	@NonNull
	private String image;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_categories_id"), name="categories_id", referencedColumnName = "id", columnDefinition = "String")
	private Categories categories;
		
}

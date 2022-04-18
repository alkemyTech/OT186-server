package com.alkemy.ong.entity;

import javax.persistence.*;

@Entity
@Table(name = "slide")
public class Slide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slide_id")
    private Long slideId;
    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "order", nullable = false)
    private Long order;
    @Column(name ="organization_id", nullable = false)
    private Long organizationId;

    public Slide() {
    }

    public Slide(Long slideId, String imageUrl, String text, Long order, Long organizationId) {
        this.slideId = slideId;
        this.imageUrl = imageUrl;
        this.text = text;
        this.order = order;
        this.organizationId = organizationId;
    }

    public Long getSlideId() {
        return slideId;
    }

    public void setSlideId(Long slideId) {
        this.slideId = slideId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
}


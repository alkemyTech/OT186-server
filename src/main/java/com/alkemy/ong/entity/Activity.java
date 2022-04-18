package com.alkemy.ong.entity;

import javax.persistence.*;
import java.sql.Timestamp;

//ticket 21
@Entity
@Table(name="Activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_activity")
    private Long idActivity;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "image", nullable = false)
    private String image;
    @Column(name = "timestamp")
    private Timestamp timestamps;
    @Column(name = "soft_delete")
    private Boolean softDelete;

    public Activity() {
    }

    public Activity(Long idActivity, String name, String content, String image, Timestamp timestamps, Boolean softDelete) {
        this.idActivity = idActivity;
        this.name = name;
        this.content = content;
        this.image = image;
        this.timestamps = timestamps;
        this.softDelete = softDelete;
    }

    public Long getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(Long idActivity) {
        this.idActivity = idActivity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(Timestamp timestamps) {
        this.timestamps = timestamps;
    }

    public Boolean getSoftDelete() {
        return softDelete;
    }

    public void setSoftDelete(Boolean softDelete) {
        this.softDelete = softDelete;
    }
}

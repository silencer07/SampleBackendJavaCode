package com.englishcentral.video;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Document(collection = "videos")
public class Video
{
    @Id
    private String id;

    @NotBlank
    private String name;

    private String description;

    @Min(value = 1)
    private long lengthInSecs;

    @NotBlank
    private String uploadedBy;

    @NotNull
    private LocalDateTime uploadTime = LocalDateTime.now();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getLengthInSecs() {
        return lengthInSecs;
    }

    public void setLengthInSecs(long lengthInSecs) {
        this.lengthInSecs = lengthInSecs;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }
}

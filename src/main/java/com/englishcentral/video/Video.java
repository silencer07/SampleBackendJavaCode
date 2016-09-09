package com.englishcentral.video;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * This class contains the metadata of the video file.
 */
@Document(collection = "videos")
public class Video
{
    /**
     * Unique identifier for the video. Auto-generated by the underlying storage.
     */
    @Id
    private String id;

    /**
     * Name of the video that serves as the title too.
     */
    @NotBlank
    @Max(100)
    private String name;

    /**
     * Description of the video that is filled out by the user to tell the viewers what the video is for.
     */
    @Max(1000)
    private String description;

    /**
     * The length of the video in seconds, stored for faster access.
     */
    @Min(value = 1)
    private long lengthInSecs;

    /**
     * The username of the uploader. for simplicity's sake of this exercise i just opt to have it as a string
     */
    @NotBlank
    @Max(50)
    private String uploadedBy;

    /**
     * The time the user uploaded the video, which is auto-generated.
     */
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

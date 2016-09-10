package com.englishcentral.video;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
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
    @Size(max = 100)
    private String name;

    /**
     * Description of the video that is filled out by the user to tell the viewers what the video is for.
     */
    @Size(max = 1000)
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
    @Size(max = 50)
    private String uploadedBy;

    /**
     * The time the user uploaded the video, which is auto-generated.
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Video video = (Video) o;

        return getId().equals(video.getId());

    }

    @Override
    public int hashCode() {
        //workaround for errors in hibernate validator, id is guaranteed to have a value anyway.
        return getId() != null ? getId().hashCode() : 0;
    }
}

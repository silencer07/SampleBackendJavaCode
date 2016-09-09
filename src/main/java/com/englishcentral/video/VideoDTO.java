package com.englishcentral.video;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * DTO class for {@link Video}. The fields here are the editable fields in the said class.
 */
class VideoDTO {

    /**
     * @see Video#name
     */
    @NotBlank
    @Size(max = 100)
    private String name;

    /**
     * @see Video#description
     */
    @Size(max = 1000)
    private String description;

    /**
     * @see Video#lengthInSecs
     */
    @Min(value = 1)
    private long lengthInSecs;

    /**
     * @see Video#uploadedBy
     */
    @NotBlank
    @Size(max = 1000)
    private String uploadedBy;

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
}

package com.englishcentral.video;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

class VideoDTO {
    @NotBlank
    private String name;

    private String description;

    @Min(value = 1)
    private long lengthInSecs;

    @NotBlank
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

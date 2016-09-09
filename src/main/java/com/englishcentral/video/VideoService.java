package com.englishcentral.video;

import java.util.Map;

/**
 * Service class for {@link Video}.
 */
public interface VideoService {

    /**
     * Find all the existing videos sorted according to the specified sorting parameters.
     * @param sortingParams key-value pair wherein key serves as the valid field in the {@link Video}.
     *                      key is ignored when not a valid property. Value is the order of the field sorting which can
     *                      be ASC or DESC, defaults to ASC when wrong value is specified.
     * @return Iterable list of existing videos in the storage.
     */
    Iterable<Video> findAll(Map<String, String> sortingParams);

    /**
     * Saves the video data.
     * @param dto object that contains the metadata of the {@link Video}.
     * @return saved {@link Video} object from the storage.
     */
    Video save(VideoDTO dto);

    /**
     * Saves many video data at one go.
     * @param dtos Iterable object that contains the metadata of the {@link Video videos}.
     * @return saved Iterable {@link Video videos} object from the storage.
     */
    Iterable<Video> save(Iterable<VideoDTO> dtos);

    /**
     * Finds a particular {@link Video} using the unique id.
     * @param id the unique auto-generated id.
     * @return existing {@link Video} object from storage.
     */
    Video findOne(String id);

    /**
     * Deletes a particular {@link Video} using the unique id. Does nothing when the id specified does not exists.
     * @param id the unique auto-generated id.
     */
    void delete(String id);

    /**
     * Updates an existing record referenced via id. The editable fields are contained in the dto parameter.
     * Should throw an exception if the specified id does not exists.
     * @param id the unique auto-generated id.
     * @param dto object that contains the editable fields.
     * @return updated {@link Video} object from storage.
     */
    Video update(String id, VideoDTO dto);
}

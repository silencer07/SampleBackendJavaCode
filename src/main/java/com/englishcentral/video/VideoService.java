package com.englishcentral.video;

import java.util.Map;

public interface VideoService {
    Iterable<Video> findAll(Map<String, String> sortingParams);

    Video save(VideoDTO dto);

    Iterable<Video> save(Iterable<VideoDTO> dtos);

    Video findOne(String s);

    void delete(String s);

    Video update(String id, VideoDTO dto);
}

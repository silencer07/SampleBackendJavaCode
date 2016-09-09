package com.englishcentral.video;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private Validator validator;

    @Override
    public Iterable<Video> findAll(Map<String, String> sortingParams) {
        if(sortingParams != null && !sortingParams.isEmpty()){
            Sort sort = null;

            for(Map.Entry<String, String> sortingParam : sortingParams.entrySet()){
                String field = sortingParam.getKey();
                String direction = sortingParam.getValue();

                if(sort == null){
                    sort = new Sort(Sort.Direction.fromString(direction), field);
                } else {
                    sort = sort.and(new Sort(Sort.Direction.fromString(direction), field));
                }
            }
            return videoRepository.findAll(sort);
        }
        return  videoRepository.findAll();
    }

    @Override
    public Video save(VideoDTO dto) {
        validate(dto);
        Video video = new Video();
        BeanUtils.copyProperties(dto, video);
        return videoRepository.save(video);
    }

    @Override
    public Iterable<Video> save(Iterable<VideoDTO> dtos) {
        List<Video> videos = new ArrayList<>();
        dtos.forEach(dto -> {
            validate(dto);
            Video video = new Video();
            BeanUtils.copyProperties(dto, video);
            videos.add(video);
        });
        return videoRepository.save(videos);
    }

    @Override
    public Video findOne(String s) {
        return videoRepository.findOne(s);
    }

    @Override
    public void delete(String s) {
        videoRepository.delete(s);
    }

    @Override
    public Video update(String id, VideoDTO dto){
        validate(dto);
        Video existing = videoRepository.findOne(id);
        if(existing != null){
            existing.setName(dto.getName());
            existing.setDescription(dto.getDescription());
            existing.setLengthInSecs(dto.getLengthInSecs());
            existing.setUploadedBy(dto.getUploadedBy());
            return videoRepository.save(existing);
        } else {
            throw new IllegalArgumentException("Video with id " + id + " does not exists in the database");
        }
    }

    private void validate(Object o){
        if(validator.validate(o).size() != 0){
            throw new IllegalArgumentException("DTO should have valid fields");
        }
    }
}

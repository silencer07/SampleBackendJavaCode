package com.englishcentral.video;

import com.englishcentral.util.ValidList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Video save(@Valid @RequestBody VideoDTO dto){
        Video video = new Video();
        BeanUtils.copyProperties(dto, video);
        return videoRepository.save(video);
    }

    @RequestMapping(value = "saveAll", method = RequestMethod.POST)
    public Iterable<Video> saveAll(@Valid @RequestBody ValidList<VideoDTO> dtos){
        List<Video> videos = new ArrayList<>();
        dtos.forEach(dto -> {
            Video video = new Video();
            BeanUtils.copyProperties(dto, video);
            videos.add(video);
        });
        return videoRepository.save(videos);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Video find(@PathVariable String id){
        return videoRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Video> findAll(@RequestParam Map<String,String> sortingParams){

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

    ///correct code below

    @RequestMapping(value = "/{video}", method = RequestMethod.PUT)
    public Video update(@Valid Video video){
        return videoRepository.save(video);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(String id){
        videoRepository.delete(id);
    }
}

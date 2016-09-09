package com.englishcentral.video;

import com.englishcentral.util.ValidList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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

    ///correct code below

    //sorting should be configurable per field asc/desc
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Video> findAll(@RequestParam(value = "sortFields", required = false) List<String> sortFields,
                            @RequestParam(value = "direction", required = false) Sort.Direction direction){

        if(sortFields != null){
            String[] fields = (String[]) sortFields.toArray();
            return videoRepository.findAll(new Sort(direction, fields));
        }
        return  videoRepository.findAll();
    }

    @RequestMapping(value = "/{video}", method = RequestMethod.PUT)
    public Video update(@Valid Video video){
        return videoRepository.save(video);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(String id){
        videoRepository.delete(id);
    }
}

package com.englishcentral.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Video save(@RequestBody VideoDTO dto){
        return null;
    }

    @RequestMapping(value = "saveAll", method = RequestMethod.POST)
    public Iterable<Video> saveAll(@RequestBody List<VideoDTO> dtos){
        return null;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Video find(long id){
        return videoRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Video> findAll(@RequestParam(value = "sortFields", required = false) Iterable<String> sortFields,
                            @RequestParam(value = "direction", required = false) Sort.Direction direction){
        return videoRepository.findAll();
    }

    @RequestMapping(value = "/{video}", method = RequestMethod.PUT)
    public Video update(Video video){
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(long id){

    }
}

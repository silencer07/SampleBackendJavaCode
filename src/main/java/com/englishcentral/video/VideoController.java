package com.englishcentral.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    @RequestMapping(value = "/{dto}", method = RequestMethod.POST)
    public Video save(VideoDTO dto){
        return null;
    }

    @RequestMapping(value = "/{dtos}", method = RequestMethod.POST)
    public Iterable<Video> save(Iterable<VideoDTO> dtos){
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

package com.englishcentral.video;

import com.englishcentral.util.ValidList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @RequestMapping(method = RequestMethod.POST)
    public Video save(@Valid @RequestBody VideoDTO dto){
        return videoService.save(dto);
    }

    @RequestMapping(value = "saveAll", method = RequestMethod.POST)
    public Iterable<Video> saveAll(@Valid @RequestBody ValidList<VideoDTO> dtos){
        return videoService.save(dtos);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Video find(@PathVariable String id){
        return videoService.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Video> findAll(@RequestParam Map<String,String> sortingParams){
        return  videoService.findAll(sortingParams);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Video find(@PathVariable String id, @RequestBody @Valid VideoDTO dto){
        return videoService.update(id, dto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id){
        videoService.delete(id);
    }
}

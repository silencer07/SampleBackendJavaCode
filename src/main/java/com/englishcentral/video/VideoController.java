package com.englishcentral.video;

import com.englishcentral.util.ValidList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @RequestMapping(method = RequestMethod.POST)
    public Resources<Resource> save(@Valid @RequestBody VideoDTO dto){
        Video saved = videoService.save(dto);

        List<Resource> videoResources = new ArrayList<>();
        videoResources.add(buildAsResource(saved));

        Link thisLink = linkTo(VideoController.class).withSelfRel();
        Resources<Resource> resourceList = new Resources<>(videoResources, thisLink);

        return new Resources<>(resourceList, thisLink);
    }

    @RequestMapping(value = "saveAll", method = RequestMethod.POST)
    public Resources<Resource> saveAll(@Valid @RequestBody ValidList<VideoDTO> dtos){
        Iterable<Video> videos = videoService.save(dtos);

        List<Resource> videoResources = buildAllAsResource(videos);

        Link thisLink = linkTo(VideoController.class).slash("saveAll").withSelfRel();
        Resources<Resource> resourceList = new Resources<>(videoResources, thisLink);

        return new Resources<>(resourceList, thisLink);
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

    private List<Resource> buildAllAsResource(Iterable<Video> videos){
        List<Resource> list = new ArrayList<>();
        videos.forEach(v -> list.add(buildAsResource(v)));
        return list;
    }

    private Resource buildAsResource(Video video) {
        Link videoLink = linkTo(VideoController.class).slash(video.getId()).withSelfRel();
        return new Resource(video, videoLink.expand(video.getId()));
    }
}

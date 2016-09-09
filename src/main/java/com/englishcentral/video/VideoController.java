package com.englishcentral.video;

import com.englishcentral.util.ValidList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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

        Link thisLink = linkTo(methodOn(VideoController.class).saveAll(dtos)).withSelfRel();
        Resources<Resource> resourceList = new Resources<>(videoResources, thisLink);

        return new Resources<>(resourceList, thisLink);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Resources<Resource>  find(@PathVariable String id){
        Video existing = videoService.findOne(id);
        List<Resource> videoResources = new ArrayList<>();

        if(existing != null){
            videoResources.add(buildAsResource(existing));
        }

        Link thisLink = linkTo(VideoController.class).slash(id).withSelfRel();
        Resources<Resource> resourceList = new Resources<>(videoResources, thisLink);

        return new Resources<>(resourceList, thisLink);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Resources<Resource> findAll(@RequestParam Map<String,String> sortingParams){

        Iterable<Video> videos = videoService.findAll(sortingParams);

        List<Resource> videoResources = buildAllAsResource(videos);

        Link thisLink = linkTo(methodOn(VideoController.class).findAll(sortingParams)).withSelfRel();
        Resources<Resource> resourceList = new Resources<>(videoResources, thisLink);

        return new Resources<>(resourceList, thisLink);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Resources<Resource> update(@PathVariable String id, @RequestBody @Valid VideoDTO dto){
        Video saved = videoService.update(id, dto);

        List<Resource> videoResources = new ArrayList<>();
        videoResources.add(buildAsResource(saved));

        Link thisLink = linkTo(methodOn(VideoController.class).update(id, dto)).withSelfRel();
        Resources<Resource> resourceList = new Resources<>(videoResources, thisLink);

        return new Resources<>(resourceList, thisLink);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Resources<Resource> delete(@PathVariable String id){
        videoService.delete(id);
        Link thisLink = linkTo(methodOn(VideoController.class).delete(id)).withSelfRel();
        return new Resources<>(Collections.emptyList(), thisLink);
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

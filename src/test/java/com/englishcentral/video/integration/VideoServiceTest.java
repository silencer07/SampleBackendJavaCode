package com.englishcentral.video.integration;

import com.englishcentral.TestUtil;
import com.englishcentral.video.Video;
import com.englishcentral.video.VideoDTO;
import com.englishcentral.video.VideoRepository;
import com.englishcentral.video.VideoService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoServiceTest {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoService videoService;

    private Video video1;
    private Video video2;
    private VideoDTO dto;

    @Before
    public void setup(){
        video1 = new Video();
        video1.setName("test B");
        video1.setUploadedBy("test");
        video1.setLengthInSecs(1L);
        video1.setDescription("test desc");
        video1 = videoRepository.save(video1);
        assertThat(video1.getId()).isNotEmpty();

        video2 = new Video();
        video2.setName("test A");
        video2.setUploadedBy("test");
        video2.setLengthInSecs(1L);
        video2.setDescription("test desc");
        video2 = videoRepository.save(video2);
        assertThat(video2.getId()).isNotEmpty();

        assertThat(videoRepository.count()).isEqualTo(2);

        dto = new VideoDTO();
        dto.setName("new record");
        dto.setUploadedBy("new record uploaded by");
        dto.setLengthInSecs(2L);
        dto.setDescription("new record description");

        assertThat(TestUtil.getOffendingFieldAndValues(dto)).isEmpty();
    }

    @After
    public void teardown(){
        videoRepository.deleteAll();
    }

    @Test
    public void whenFindingAllWithEmptyMapThenReturnAllExistingVideos(){
        List<Video> list = new ArrayList<>();
        videoService.findAll(null).forEach(list::add);
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0)).isEqualTo(video1);
        assertThat(list.get(1)).isEqualTo(video2);

        list = new ArrayList<>();
        videoService.findAll(new HashMap<>()).forEach(list::add);
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0)).isEqualTo(video1);
        assertThat(list.get(1)).isEqualTo(video2);
    }

    @Test
    public void whenFindingAllWithCorrectMapThenReturnSortedVideos(){
        Map<String, String> sortingParams = new HashMap<>();
        sortingParams.put("name", "asc");

        List<Video> list = new ArrayList<>();
        videoService.findAll(sortingParams).forEach(list::add);
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0)).isEqualTo(video2);
        assertThat(list.get(1)).isEqualTo(video1);
    }

    @Test
    public void whenFindingAllWithIncorrectMapThenReturnSortedVideos(){
        Map<String, String> sortingParams = new HashMap<>();
        sortingParams.put("name", "somerandomstring");

        List<Video> list = new ArrayList<>();
        videoService.findAll(sortingParams).forEach(list::add);
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0)).isEqualTo(video2);
        assertThat(list.get(1)).isEqualTo(video1);

        sortingParams = new HashMap<>();
        sortingParams.put("somerandomstring", "asc");

        list = new ArrayList<>();
        videoService.findAll(sortingParams).forEach(list::add);
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0)).isEqualTo(video1);
        assertThat(list.get(1)).isEqualTo(video2);
    }

    @Test
    public void whenSavingVideoUsingValidDTOThenStoreToDB(){
        Video video = videoService.save(dto);
        assertThat(videoRepository.count()).isEqualTo(3);

        assertThat(video.getId()).isNotEmpty();
        assertThat(videoRepository.findOne(video.getId())).isNotNull();
        assertThat(video).isNotEqualTo(video1);
        assertThat(video).isNotEqualTo(video2);
    }

    @Test
    public void whenSavingVideosUsingValidDTOThenStoreToDB(){
        List<VideoDTO> list = new ArrayList<>();
        list.add(dto);

        List<Video> result = new ArrayList<>();
        videoService.save(list).forEach(result::add);
        assertThat(videoRepository.count()).isEqualTo(3);

        assertThat(result.size()).isEqualTo(1);
        Video video = result.get(0);

        assertThat(video.getId()).isNotEmpty();
        assertThat(videoRepository.findOne(video.getId())).isNotNull();
        assertThat(video).isNotEqualTo(video1);
        assertThat(video).isNotEqualTo(video2);
    }

    @Test
    public void whenFindingOneUsingIdThenReturnFromDB(){
        assertThat(videoService.findOne(video1.getId())).isEqualTo(video1);
    }

    @Test
    public void whenDeletingUsingIdThenRemoveFromDB(){
        videoService.delete(video1.getId());
        assertThat(videoService.findOne(video1.getId())).isEqualTo(null);
    }

    @Test
    public void whenUpdatingUsingIdAndDTOThenRemoveFromDB(){
        Video video = videoService.update(video1.getId(), dto);
        assertThat(video).isEqualTo(video1); // same ID

        //assert new values
        assertThat(video.getName()).isEqualTo(dto.getName());
        assertThat(video.getDescription()).isEqualTo(dto.getDescription());
        assertThat(video.getLengthInSecs()).isEqualTo(dto.getLengthInSecs());
        assertThat(video.getUploadedBy()).isEqualTo(dto.getUploadedBy());

        //assert uneditable fields
        assertThat(video.getUploadTime()).isEqualTo(video1.getUploadTime());
    }
}

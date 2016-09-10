package com.englishcentral.video.integration;

import com.englishcentral.TestUtil;
import com.englishcentral.video.Video;
import com.englishcentral.video.VideoDTO;
import com.englishcentral.video.VideoRepository;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.apache.commons.lang3.ArrayUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.jayway.restassured.RestAssured.when;
import static java.lang.Math.toIntExact;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VideoControllerTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private VideoRepository videoRepository;

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

        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/video";
        RestAssured.port = port;
    }

    @After
    public void teardown(){
        videoRepository.deleteAll();
    }

    @Test
    public void whenAccessingVideosWithGetThenReturnAllVideos(){
        Response response = when().get("");
        assertBodyResponse(response, "", video1, video2);
    }

    private void assertBodyResponse(Response response, String path, Video... vids){
        List<Video> videos = Arrays.asList(vids);
        String[] names = videos.stream().map(Video::getName).toArray(String[]::new);
        String[] descriptions = videos.stream().map(Video::getDescription).toArray(String[]::new);
        String[] uploadedBy = videos.stream().map(Video::getUploadedBy).toArray(String[]::new);
        String[] selfUris = videos.stream().map(v -> getSelfUri(v.getId())).toArray(String[]::new);
        Integer[] lengthInSecs = videos.stream().mapToInt(v -> toIntExact(v.getLengthInSecs())).boxed().toArray(Integer[]::new);

        response.then()
            .statusCode(HttpStatus.OK.value())
            .body("_embedded.videos.id", Matchers.hasSize(2))
            .body("_embedded.videos", Matchers.hasSize(2))
            .body("_embedded.videos.name", Matchers.contains(names))
            .body("_embedded.videos.description", Matchers.contains(descriptions))
            .body("_embedded.videos.uploadedBy", Matchers.contains(uploadedBy))
            .body("_embedded.videos.lengthInSecs", Matchers.contains(lengthInSecs))
            .body("_embedded.videos.uploadTime", Matchers.hasSize(2))
            .body("_embedded.videos._links.self.href", Matchers.contains(selfUris))
            .body("_links.self.href", Matchers.equalTo(getUri() + path))
        ;
    }

    private String getUri(){
        return RestAssured.baseURI + ":" + RestAssured.port + RestAssured.basePath;
    }

    private String getSelfUri(String id){
        return getUri() + "/" + id;
    }
}

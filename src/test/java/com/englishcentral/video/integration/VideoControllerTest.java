package com.englishcentral.video.integration;

import com.englishcentral.TestUtil;
import com.englishcentral.video.Video;
import com.englishcentral.video.VideoDTO;
import com.englishcentral.video.VideoRepository;
import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
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

    private static final Gson gson = new Gson();

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
        video2.setLengthInSecs(2L);
        video2.setDescription("test desc");
        video2 = videoRepository.save(video2);
        assertThat(video2.getId()).isNotEmpty();

        assertThat(videoRepository.count()).isEqualTo(2);

        dto = new VideoDTO();
        dto.setName("new record");
        dto.setUploadedBy("new record uploaded by");
        dto.setLengthInSecs(3L);
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
        String urlPart = "";
        Response response = when().get(urlPart);
        assertBodyResponse(response, urlPart, video1, video2);
    }

    @Test
    public void whenAccessingVideosWithGetWithSortingCriteriaThenReturnAllVideos(){
        String urlPart = "?lengthInSecs=desc";
        Response response = when().get(urlPart);
        assertBodyResponse(response, urlPart, video2, video1);
    }

    @Test
    public void whenAccessingVideoWithGetUsingIdThenReturnSpecificVideo(){
        String urlPart = "/" + video1.getId();
        Response response = when().get(urlPart);
        assertBodyResponse(response, urlPart, video1);
    }

    @Test
    public void whenSavingDTOThenAddToDB(){
        String urlPart = "";
        Response response = given().contentType("application/json")
                .body(gson.toJson(dto)).when().post();

        List<Video> result = new ArrayList<>();
        videoRepository.findByIdNotIn(video1.getId(), video2.getId()).forEach(result::add);
        assertThat(result).hasSize(1);
        assertBodyResponse(response, urlPart, result.get(0));
    }

    @Test
    public void whenDeletingVideoUsingIdThenReflectToDB(){
        String urlPart = "/" + video1.getId();
        when().delete(urlPart).then()
                .body("_links.self.href", Matchers.equalTo(getUri() + urlPart));

        assertThat(videoRepository.findOne(video1.getId())).isNull();
    }

    @Test
    public void whenUpdatingVideoUsingPutThenReturnUpdatedVideo(){
        String urlPart = "/" + video1.getId();
        Response response = given().contentType("application/json")
                .body(gson.toJson(dto)).when().put(urlPart);

        Video video = videoRepository.findOne(video1.getId());
        assertBodyResponse(response, urlPart, video);

        assertThat(video1.getName()).isNotEqualTo(video.getName());
        assertThat(video1.getDescription()).isNotEqualTo(video.getDescription());
        assertThat(video1.getUploadedBy()).isNotEqualTo(video.getUploadedBy());
        assertThat(video1.getLengthInSecs()).isNotEqualTo(video.getLengthInSecs());
    }

    //problem with serialized object via gson. cannot properly deserialized by the endpoint
    @Ignore
    @Test
    public void whenSavingDTOListThenAddToDB(){
        String urlPart = "/saveAll";

        List<VideoDTO> dtos = new ArrayList<>();
        dtos.add(dto);

        System.out.println(gson.toJson(dtos));

        Response response = given().contentType("application/json")
                .body(gson.toJson(dtos)).when().post();

        System.out.println(response.then().extract().asString());

        List<Video> result = new ArrayList<>();
        videoRepository.findByIdNotIn(video1.getId(), video2.getId()).forEach(result::add);
        assertThat(result).hasSize(1);
        assertBodyResponse(response, urlPart, result.get(0));
    }

    private void assertBodyResponse(Response response, String path, Video... videos){
        String[] names = Arrays.stream(videos).map(Video::getName).toArray(String[]::new);
        String[] descriptions = Arrays.stream(videos).map(Video::getDescription).toArray(String[]::new);
        String[] uploadedBy = Arrays.stream(videos).map(Video::getUploadedBy).toArray(String[]::new);
        String[] selfUris = Arrays.stream(videos).map(v -> getSelfUri(v.getId())).toArray(String[]::new);
        Integer[] lengthInSecs = Arrays.stream(videos).mapToInt(v -> toIntExact(v.getLengthInSecs())).boxed().toArray(Integer[]::new);

        response.then()
            .statusCode(HttpStatus.OK.value())
            .body("_embedded.videos.id", Matchers.hasSize(videos.length))
            .body("_embedded.videos", Matchers.hasSize(videos.length))
            .body("_embedded.videos.name", Matchers.contains(names))
            .body("_embedded.videos.description", Matchers.contains(descriptions))
            .body("_embedded.videos.uploadedBy", Matchers.contains(uploadedBy))
            .body("_embedded.videos.lengthInSecs", Matchers.contains(lengthInSecs))
            .body("_embedded.videos.uploadTime", Matchers.hasSize(videos.length))
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

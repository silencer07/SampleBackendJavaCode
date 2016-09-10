package com.englishcentral.video.unit;

import com.englishcentral.TestUtil;
import com.englishcentral.video.Video;
import com.englishcentral.video.VideoDTO;
import com.englishcentral.video.VideoService;
import com.englishcentral.video.VideoServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit test for the constraints of the {@link VideoServiceImpl} class.
 */
public class VideoServiceTest {

    private VideoService videoService;

    private VideoDTO invalidDTO;

    @Before
    public void setup(){
        videoService = new VideoServiceImpl();
        ((VideoServiceImpl) videoService).setValidator(TestUtil.VALIDATOR);

        invalidDTO = new VideoDTO();
        invalidDTO.setName(null);
        invalidDTO.setUploadedBy("test");
        invalidDTO.setLengthInSecs(1);
        invalidDTO.setDescription("test desc");

        assertThat(TestUtil.getOffendingFieldAndValues(invalidDTO)).isNotEmpty();
    }

    @Test
    public void whenSavingInvalidDTOThenThrowException(){
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> videoService.save((VideoDTO) null));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> videoService.save(invalidDTO));
    }

    @Test
    public void whenSavingInvalidListDTOThenThrowException(){
        List<VideoDTO> list = new ArrayList<>();
        list.add(invalidDTO);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> videoService.save(list));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> videoService.save((Iterable) null));
    }


    @Test
    public void whenFindingOneVideoUsingBlankIdThenThrowException(){
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> videoService.findOne(null));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> videoService.findOne(""));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> videoService.findOne("\n"));
    }

    @Test
    public void whenDeletingVideoUsingBlankIdThenThrowException(){
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> videoService.delete(null));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> videoService.delete(""));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> videoService.delete("\n"));
    }

    @Test
    public void whenUpdatingVideoUsingBlankIdThenThrowException(){
        invalidDTO.setName("test name");
        assertThat(TestUtil.getOffendingFieldAndValues(invalidDTO)).isEmpty();

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> videoService.update(null, invalidDTO));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> videoService.update("", invalidDTO));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> videoService.update("\n", invalidDTO));
    }

    @Test
    public void whenUpdatingVideoWithInvalidDTOThenThrowException(){
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> videoService.update("anId", invalidDTO));
    }
}

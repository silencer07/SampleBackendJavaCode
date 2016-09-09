package com.englishcentral.video;

import com.englishcentral.ValidatorUtil;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for the constraints of the {@link Video} class.
 */
public class VideoTest {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    Video video;

    @Before
    public void setup(){
        video = new Video();
        video.setName("test");
        video.setDescription("test description");
        video.setLengthInSecs(100);
        video.setUploadedBy("test uploader");

        Map<String, Object> offendingFieldsAndValues = ValidatorUtil.getOffendingFieldAndValues(video, validator);
        assertThat(offendingFieldsAndValues.size()).isEqualTo(0);
    }

    @Test
    public void whenNameIsNullThenObjectInvalid(){

    }


}

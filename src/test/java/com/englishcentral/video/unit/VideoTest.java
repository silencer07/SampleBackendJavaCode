package com.englishcentral.video.unit;

import com.englishcentral.TestUtil;
import com.englishcentral.video.Video;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Map;

import static com.englishcentral.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for the constraints of the {@link Video} class.
 */
public class VideoTest {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    Video video;

    @Before
    public void setup() {
        video = new Video();
        video.setName("test");
        video.setDescription("test description");
        video.setLengthInSecs(100);
        video.setUploadedBy("test uploader");

        Map<String, Object> offendingFieldsAndValues = TestUtil.getOffendingFieldAndValues(video);
        assertThat(offendingFieldsAndValues.size()).isEqualTo(0);
        assertThat(video.getUploadTime()).isNotNull();
    }

    @Test
    public void whenNameIsBlankThenObjectInvalid() {
        video.setName(null);
        assertOffendingField(video, "name", null);

        video.setName("");
        assertOffendingField(video, "name", "");

        video.setName("\n");
        assertOffendingField(video, "name", "\n");
    }

    @Test
    public void whenNameIsOverMaxSizeThenObjectInvalid() {
        video.setName(ONE_HUNDRED_ONE_CHAR_STRING);
        assertOffendingField(video, "name", ONE_HUNDRED_ONE_CHAR_STRING);
    }

    @Test
    public void whenDescriptionIsOverMaxSizeThenObjectInvalid() {
        video.setName(ONE_THOUSAND_AND_ONE_CHAR_STRING);

        assertOffendingField(video, "name", ONE_THOUSAND_AND_ONE_CHAR_STRING);
    }

    @Test
    public void whenLengInSecsIsLessThanOneThenObjectInvalid() {
        video.setLengthInSecs(0L);

        assertOffendingField(video, "lengthInSecs", 0L);
    }

    @Test
    public void whenUploadedByIsBlankThenObjectInvalid() {
        video.setUploadedBy(null);
        assertOffendingField(video, "uploadedBy", null);

        video.setUploadedBy("");
        assertOffendingField(video, "uploadedBy", "");

        video.setUploadedBy("\n");
        assertOffendingField(video, "uploadedBy", "\n");
    }

    @Test
    public void whenUploadedByIsOverMaxSizeThenObjectInvalid() {
        video.setUploadedBy(FIFTY_ONE_CHAR_STRING);

        assertOffendingField(video, "uploadedBy", FIFTY_ONE_CHAR_STRING);
    }

}

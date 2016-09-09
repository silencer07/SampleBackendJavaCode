package com.englishcentral.video;

import com.englishcentral.TestUtil;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Map;

import static com.englishcentral.TestUtil.assertOffendingField;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for the constraints of the {@link VideoDTO} class.
 */
public class VideoDTOTest {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    VideoDTO dto;

    @Before
    public void setup() {
        dto = new VideoDTO();
        dto.setName("test");
        dto.setDescription("test description");
        dto.setLengthInSecs(100);
        dto.setUploadedBy("test uploader");

        Map<String, Object> offendingFieldsAndValues = TestUtil.getOffendingFieldAndValues(dto);
        assertThat(offendingFieldsAndValues.size()).isEqualTo(0);
    }

    @Test
    public void whenNameIsBlankThenObjectInvalid() {
        dto.setName(null);
        assertOffendingField(dto, "name", null);

        dto.setName("");
        assertOffendingField(dto, "name", "");

        dto.setName("\n");
        assertOffendingField(dto, "name", "\n");
    }

    @Test
    public void whenNameIsOverMaxSizeThenObjectInvalid() {
        dto.setName(TestUtil.ONE_HUNDRED_ONE_CHAR_STRING);
        assertOffendingField(dto, "name", TestUtil.ONE_HUNDRED_ONE_CHAR_STRING);
    }

    @Test
    public void whenDescriptionIsOverMaxSizeThenObjectInvalid() {
        dto.setName(TestUtil.ONE_THOUSAND_AND_ONE_CHAR_STRING);

        assertOffendingField(dto, "name", TestUtil.ONE_THOUSAND_AND_ONE_CHAR_STRING);
    }

    @Test
    public void whenLengInSecsIsLessThanOneThenObjectInvalid() {
        dto.setLengthInSecs(0L);

        assertOffendingField(dto, "lengthInSecs", 0L);
    }

    @Test
    public void whenUploadedByIsBlankThenObjectInvalid() {
        dto.setUploadedBy(null);
        assertOffendingField(dto, "uploadedBy", null);

        dto.setUploadedBy("");
        assertOffendingField(dto, "uploadedBy", "");

        dto.setUploadedBy("\n");
        assertOffendingField(dto, "uploadedBy", "\n");
    }

    @Test
    public void whenUploadedByIsOverMaxSizeThenObjectInvalid() {
        dto.setUploadedBy(TestUtil.FIFTY_ONE_CHAR_STRING);

        assertOffendingField(dto, "uploadedBy", TestUtil.FIFTY_ONE_CHAR_STRING);
    }
}

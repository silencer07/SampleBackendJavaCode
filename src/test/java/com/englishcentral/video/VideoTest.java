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
    public void setup() {
        video = new Video();
        video.setName("test");
        video.setDescription("test description");
        video.setLengthInSecs(100);
        video.setUploadedBy("test uploader");

        Map<String, Object> offendingFieldsAndValues = ValidatorUtil.getOffendingFieldAndValues(video, validator);
        assertThat(offendingFieldsAndValues.size()).isEqualTo(0);
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

    private void assertOffendingField(Video video, String fieldName, Object fieldValue) {
        Map<String, Object> offendingFieldsAndValues = ValidatorUtil.getOffendingFieldAndValues(video, validator);
        assertThat(offendingFieldsAndValues.size()).isEqualTo(1);
        Map.Entry<String, Object> entry = offendingFieldsAndValues.entrySet().iterator().next();
        assertThat(entry.getKey()).isEqualTo(fieldName);
        assertThat(entry.getValue()).isEqualTo(fieldValue);
    }

    private static final String ONE_HUNDRED_ONE_CHAR_STRING = "7BGPpt4UGsXZoyNiW7JeXWXzXGTPBL9fBzhDV1xL9g" +
            "L6uRc4Ey1ahLjhMpyr2eN9IlvT60ScWKbWhz5Frm3aGWMTIYWFb8bExPBQA";
    private static final String ONE_THOUSAND_AND_ONE_CHAR_STRING =
            "WjfXNDQ1HWTKH8vzGNgUZk50GraOvqV9aOALo6QMlz0jywrS11ja4aD5FlA7hwy6BqOkGjpS" +
                    "bVAPPVVtD6tzi17Ifyzw5afrwGfiT5EJoMvxKpHXFROnbHD5CDocwbOfgGHtyJqMOZU1XqyyLnqA5Dmub4z" +
                    "DID9n7juVsuICDMoUMSbY7fUujTHqJ68EnGAu72oxmVmUHUVEYxvq8LZWvvbvDYXsaakDmRFTzTLKWwgkxYOO" +
                    "Kww7S6WNLn80X3YRD6hyPiHfgT2yKeT99LGpcPzhEhDUWuORwMYWP6JFUoIYmhZ17TaXeyhaHMRbhHc6qG4iJ" +
                    "uGFimelX4OeY3HqZBu1vBMf32xNZrkoDj5noUaHfzVGjaZ5T4CYBCCOCkuYXJ0b9Y9yKqmNGW4pilyGfQtuD1F56" +
                    "DSi78PO1WXOUiis3am1XPi4TYxF2yvhb4klTyMY07W8pQo9ubMWmKkuqrFhFlLABYVNJsRF6jhimjZVBw32Bg2Jtm" +
                    "beUrhyD8iZRFfHPu7xzKEu8GNv9G1f61Rkq3o5qIrJl3zTx14VCtaaj7jAJ5Yy1L1hIjAxGp0zbfxzODn2gPlCo3Sc" +
                    "kFMCBz8TjfD6h0CvZXo94NzUNGAcfGBQA5oWxU4JWHbz0wmapJEkwlosLv8e62GpMJsTXg0LZ7uVcDnw87R8" +
                    "7fHi9ZNpmuayhTtszS6nQB6AwBcP5r2eBpIv9nViw42Jev0ot6Y3nnlSr2SbysaHvXZMOvONM80No1V5SLEyYHhHA" +
                    "q9t534C0vnBcFfo1DIUjnszFqItVA7e8j9tg83f6egLgXRlwmkahnyRZqiRIQi1ObEXClGOIVUS0hjF29Xo6sEF3DexpYUM" +
                    "XvyYhxTBNZ1CeMHylbzkL52w6i53U2bYgsY7KTowpqRPy1rz2Kp6RjYRXVmJVsQpC9P2eiERYRitZHfNtVMAz6oyYX68hsVo" +
                    "CTemiWrpSFpYCN7aTBKWIPKaxgWV7GMvYolAuEG4DC";
    private static final String FIFTY_ONE_CHAR_STRING = "DmLqaNAw3MAxs8nwskiUwVtRaMy5BRguULXFUtYj5s5BbX3RqVL";
}

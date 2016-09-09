package com.englishcentral.video;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO class for the {@link Video} class. This class <b>HAS NO</b> implementation because Spring Data takes care of it.
 */
@Repository
public interface VideoRepository extends PagingAndSortingRepository<Video, String> {

}

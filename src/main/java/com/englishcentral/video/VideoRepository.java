package com.englishcentral.video;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
interface VideoRepository extends PagingAndSortingRepository<Video, Long> {

}

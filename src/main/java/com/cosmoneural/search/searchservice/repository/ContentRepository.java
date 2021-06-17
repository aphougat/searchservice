package com.cosmoneural.search.searchservice.repository;

import com.cosmoneural.search.searchservice.model.ContentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface ContentRepository extends SolrCrudRepository<ContentModel, String> {

    @Query("id:*?0* OR desc:*?0* OR keywords:*?0* OR title:*?0*")
    Page<ContentModel> findByCustomQuery(String searchTerm, Pageable pageable);

    @Query(name = "content.findByNamedQuery")
    Page<ContentModel> findByNamedQuery(String searchTerm, Pageable pageable);

    List<ContentModel> findByTitle(String title);

}

package com.cosmoneural.search.searchservice.repository;

import com.cosmoneural.search.searchservice.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface UserRepository extends SolrCrudRepository<UserModel, String> {

    @Query("id:*?0* OR name:*?0* OR keywords:*?0* OR brand:*?0*")
    Page<UserModel> findByCustomQuery(String searchTerm, Pageable pageable);

    @Query(name = "user.findByNamedQuery")
    Page<UserModel> findByNamedQuery(String searchTerm, Pageable pageable);

}

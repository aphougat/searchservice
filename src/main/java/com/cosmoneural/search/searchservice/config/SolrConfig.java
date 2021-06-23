package com.cosmoneural.search.searchservice.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(
        basePackages = "com.cosmoneural.search.searchservice.repository",
        namedQueriesLocation = "classpath:application.properties",
        schemaCreationSupport = true)
public class SolrConfig {

    @Bean
    public SolrClient solrClient() {
        return new CloudSolrClient.Builder()
                .withZkHost("localhost:9983")
                .build();
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient client) {
        return new SolrTemplate(client);
    }
}
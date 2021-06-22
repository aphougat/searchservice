package com.cosmoneural.search.searchservice.repository;

import com.cosmoneural.search.searchservice.config.SolrConfig;
import com.cosmoneural.search.searchservice.model.ContentModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = SolrConfig.class)
public class ContentRepositoryTest {

    @Resource
    private ContentRepository contentRepository;

    @BeforeEach
    public void clearSolrData() {
        contentRepository.deleteAll();
    }

    @Test
    public void whenSavingContentModel_thenAvailableOnRetrieval()  {
        final ContentModel contentModel = new ContentModel();
        contentModel.setId("P000089998");
        contentModel.setTitle("Desk");
        contentRepository.save(contentModel);
        if (contentRepository.findById(contentModel.getId()).isPresent())
        {
            final ContentModel retrievedContentModel = contentRepository.findById(contentModel.getId()).get();
            assertEquals(contentModel.getId(), retrievedContentModel.getId());
        }

    }

    @Test
    public void whenUpdatingContentModel_thenChangeAvailableOnRetrieval()  {
        final ContentModel contentModel = new ContentModel();
        contentModel.setId("P0001");
        contentModel.setTitle("T-Shirt");

        contentRepository.save(contentModel);

        contentModel.setTitle("Shirt");
        contentRepository.save(contentModel);

        if (contentRepository.findById(contentModel.getId()).isPresent())
        {
            final ContentModel retrievedContentModel = contentRepository.findById(contentModel.getId()).get();
            assertEquals(contentModel.getTitle(), retrievedContentModel.getTitle());
        }
    }

    @Test
    public void whenDeletingContentModel_thenNotAvailableOnRetrieval()  {
        final ContentModel contentModel = new ContentModel();
        contentModel.setId("P0001");
        contentModel.setTitle("Desk");
        contentRepository.save(contentModel);

        contentRepository.delete(contentModel);

        if (contentRepository.findById(contentModel.getId()).isPresent())
        {
            final ContentModel retrievedContentModel = contentRepository.findById(contentModel.getId()).get();
            assertNotNull(retrievedContentModel);
        }

    }

    @Test
    public void whenFindByName_thenAvailableOnRetrieval()  {
        ContentModel phone = new ContentModel();
        phone.setId("P0001");
        phone.setTitle("Phone");
        contentRepository.save(phone);

        List<ContentModel> retrievedContentModels = contentRepository.findByTitle("Phone");
        assertEquals(phone.getId(), retrievedContentModels.get(0).getId());
    }

    @Test
    public void whenSearchingContentModelsByQuery_thenAllMatchingContentModelsShouldAvailable()  {
        final ContentModel phone = new ContentModel();
        phone.setId("P0001");
        phone.setTitle("Smart Phone");
        phone.setDesc("This is a smart phone");
        List<String> keyWords = new ArrayList<>();
        keyWords.add("Smart Phone");
        keyWords.add("Phone");
        keyWords.add("Android");
        phone.setKeywords(keyWords);
        contentRepository.save(phone);

        final ContentModel phoneCover = new ContentModel();
        phoneCover.setId("P0002");
        phoneCover.setTitle("Phone Cover");
        phoneCover.setDesc("This is a smart phone");
        phoneCover.setKeywords(keyWords);
        contentRepository.save(phoneCover);

        final ContentModel wirelessCharger = new ContentModel();
        wirelessCharger.setId("P0003");
        wirelessCharger.setTitle("Phone Charging Cable");
        wirelessCharger.setDesc("Phone Charging Cable");
        wirelessCharger.setKeywords(keyWords);
        contentRepository.save(wirelessCharger);

        Page<ContentModel> result = contentRepository.findByCustomQuery("Phone", PageRequest.of(0, 10));
        assertEquals(3, result.getNumberOfElements());
    }

    @Test
    public void whenSearchingContentModelsByNamedQuery_thenAllMatchingContentModelsShouldAvailable()  {
        final ContentModel phone = new ContentModel();
        phone.setId("P0001");
        phone.setTitle("Smart Phone");
        phone.setDesc("This is a smart phone");
        List<String> keyWords = new ArrayList<>();
        keyWords.add("Smart Phone");
        keyWords.add("Phone");
        keyWords.add("Android");
        phone.setKeywords(keyWords);
        contentRepository.save(phone);

        final ContentModel phoneCover = new ContentModel();
        phoneCover.setId("P0002");
        phoneCover.setTitle("Phone Cover");
        phoneCover.setDesc("This is a smart phone");
        phoneCover.setKeywords(keyWords);
        contentRepository.save(phoneCover);

        final ContentModel wirelessCharger = new ContentModel();
        wirelessCharger.setId("P0003");
        wirelessCharger.setTitle("Phone Charging Cable");
        wirelessCharger.setDesc("Phone Charging Cable");
        wirelessCharger.setKeywords(keyWords);
        contentRepository.save(wirelessCharger);

        Page<ContentModel> result = contentRepository.findByNamedQuery("one",  PageRequest.of(0, 10));
        assertEquals(3, result.getNumberOfElements());
    }
}

package com.cosmoneural.search.searchservice.repository;

import com.cosmoneural.search.searchservice.config.SolrConfig;
import com.cosmoneural.search.searchservice.model.ContentModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ContextConfiguration(classes = SolrConfig.class)
public class ContentRepositoryTest {

    @Resource
    private ContentRepository contentRepository;

    @BeforeTestMethod
    public void clearSolrData() {
        contentRepository.deleteAll();
    }

    @Test
    public void whenSavingContentModel_thenAvailableOnRetrieval() throws Exception {
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
    public void whenUpdatingContentModel_thenChangeAvailableOnRetrieval() throws Exception {
        final ContentModel contentModel = new ContentModel();
        contentModel.setId("P0001");
        contentModel.setTitle("T-Shirt");

        contentRepository.save(contentModel);

        contentModel.setTitle("Shirt");
        contentRepository.save(contentModel);

        if (contentRepository.findById(contentModel.getId()).isPresent())
        {
            final ContentModel retrievedContentModel = contentRepository.findById(contentModel.getId()).get();
            assertEquals(contentModel.getContentText(), retrievedContentModel.getContentText());
        }
    }

    @Test
    public void whenDeletingContentModel_thenNotAvailableOnRetrieval() throws Exception {
        final ContentModel contentModel = new ContentModel();
        contentModel.setId("P0001");
        contentModel.setTitle("Desk");
        contentRepository.save(contentModel);

        contentRepository.delete(contentModel);

        if (contentRepository.findById(contentModel.getId()).isPresent())
        {
            final ContentModel retrievedContentModel = contentRepository.findById(contentModel.getId()).get();
            assertNull(retrievedContentModel);
        }

    }

    @Test
    public void whenFindByName_thenAvailableOnRetrieval() throws Exception {
        ContentModel phone = new ContentModel();
        phone.setId("P0001");
        phone.setTitle("Phone");
        contentRepository.save(phone);

        List<ContentModel> retrievedContentModels = contentRepository.findByTitle("Phone");
        assertEquals(phone.getId(), retrievedContentModels.get(0).getId());
    }

    @Test
    public void whenSearchingContentModelsByQuery_thenAllMatchingContentModelsShouldAvialble() throws Exception {
        final ContentModel phone = new ContentModel();
        phone.setId("P0001");
        phone.setTitle("Smart Phone");
        contentRepository.save(phone);

        final ContentModel phoneCover = new ContentModel();
        phoneCover.setId("P0002");
        phoneCover.setTitle("Phone Cover");
        contentRepository.save(phoneCover);

        final ContentModel wirelessCharger = new ContentModel();
        wirelessCharger.setId("P0003");
        wirelessCharger.setTitle("Phone Charging Cable");
        contentRepository.save(wirelessCharger);

        Page<ContentModel> result = contentRepository.findByCustomQuery("Phone", PageRequest.of(0, 10));
        assertEquals(3, result.getNumberOfElements());
    }

    @Test
    public void whenSearchingContentModelsByNamedQuery_thenAllMatchingContentModelsShouldAvialble() throws Exception {
        final ContentModel phone = new ContentModel();
        phone.setId("P0001");
        phone.setTitle("Smart Phone");
        contentRepository.save(phone);

        final ContentModel phoneCover = new ContentModel();
        phoneCover.setId("P0002");
        phoneCover.setTitle("Phone Cover");
        contentRepository.save(phoneCover);

        final ContentModel wirelessCharger = new ContentModel();
        wirelessCharger.setId("P0003");
        wirelessCharger.setTitle("Phone Charging Cable");
        contentRepository.save(wirelessCharger);

        Page<ContentModel> result = contentRepository.findByNamedQuery("one",  PageRequest.of(0, 10));
        assertEquals(3, result.getNumberOfElements());
    }
}

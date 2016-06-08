package com.cintriq.agenda.core.entity;

import com.cintriq.agenda.core.AssetTypeService;
import com.cintriq.agenda.core.TagService;
import com.cintriq.agenda.core.testingUtil.TestUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class TagTest {

    @Deployment
    public static WebArchive createDeployment() {
        return TestUtils.createDeployment();
    }

    @Inject
    AssetTypeService assetTypeService;

    @Inject
    TagService tagService;

    @Test
    public void testDeletingAssetTypeDeletesTags() throws Exception {
        AssetType assetType = new AssetType();
        assetType.setName("Test AssetType");

        assetTypeService.insert(assetType);

        Tag tag = new Tag();
        tag.setName("Test Tag");
        tag.setAssetType(assetType);

        tagService.insert(tag);

        assertEquals("Incorrect number of Tags in database!", 1, tagService.getAll().size());

        assetTypeService.delete(assetType.getId());

        assertEquals("There is still a Tag in the database!", 0, tagService.getAll().size());
    }
}

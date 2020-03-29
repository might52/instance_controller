package org.might.instancecontroller.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.might.instancecontroller.dba.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ImageServiceTest {
    @Autowired
    private ImageService imageService;

    @Test
    public void canAddNewImage() {
        Image image = prepateTestImage();
        imageService.saveImage(image);
        Assert.assertEquals(prepateTestImage(), image);
    }

    private Image prepateTestImage() {
        Image image = new Image();
        image.setReference("testReference");
        return image;
    }
}

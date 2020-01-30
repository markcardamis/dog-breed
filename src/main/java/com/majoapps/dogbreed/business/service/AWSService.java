package com.majoapps.dogbreed.business.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Data
@Service
public class AWSService {

    private final AmazonS3 s3client;

    @Value("${aws.bucketname}")
    private String bucketName;

    @Autowired
    public AWSService(AmazonS3 s3client){
        this.s3client = s3client;
    }

    public URL storeImageInS3(String storageFileName, String imageUrlLocation) {
        try {
            URL url = new URL(imageUrlLocation);
            BufferedImage image = ImageIO.read(url);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", os);
            byte[] buffer = os.toByteArray();
            InputStream is = new ByteArrayInputStream(buffer);
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(buffer.length);
            meta.setContentType("image/jpeg");
            s3client.putObject(new PutObjectRequest(bucketName, storageFileName, is, meta)
                .withCannedAcl(CannedAccessControlList.PublicRead));
            return s3client.getUrl(bucketName, storageFileName);
        } catch(AmazonClientException ace) {
            throw new RuntimeException("AWS upload error " + ace.getLocalizedMessage());
        } catch (Exception e) {
            throw new RuntimeException("AWS Service error " + e.getLocalizedMessage());
        }
    }

    public void deleteFile(String fileName) {
        try {
            s3client.deleteObject(bucketName, fileName);
        } catch(AmazonClientException e) {
            throw new RuntimeException("AWS delete error " + e.getLocalizedMessage());
        }
    }

}

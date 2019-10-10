package com.example.demo.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.example.demo.bucket.BucketName;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStore {

    private final AmazonS3 s3;

    public FileStore(AmazonS3 s3) {
        this.s3 = s3;
    }

    public void save(String path, String fileName,
                     Optional<Map<String, String>> optionalMetadata,
                     InputStream inputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetadata.ifPresent(metadata -> {
            if (metadata.size() >= 1) {
                metadata.forEach(objectMetadata::addUserMetadata);
            }
        });
        s3.putObject(path, fileName, inputStream, objectMetadata);
    }

    public byte[] download(String path, String key) {
        try {
            S3Object object = s3.getObject(path, key);
            InputStream objectContent = object.getObjectContent();
            return IOUtils.toByteArray(objectContent);
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

}

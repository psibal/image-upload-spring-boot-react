package com.example.demo.bucket;

public enum BucketName {
    PROFILE_IMAGE("testing-image-123-123");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}

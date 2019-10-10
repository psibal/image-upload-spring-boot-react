package com.example.demo.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

    @Bean
    public AmazonS3 s3() {
//        EnvironmentVariableCredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
//        AWSCredentials credentials = provider.getCredentials();
//        AWSCredentials creds = new BasicAWSCredentials("access_key", "secret_key");
        ProfileCredentialsProvider profileCredentialsProvider = new ProfileCredentialsProvider();
        AWSCredentials credentials = profileCredentialsProvider.getCredentials();
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

}

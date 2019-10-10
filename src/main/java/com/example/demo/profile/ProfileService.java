package com.example.demo.profile;

import com.amazonaws.services.s3.Headers;
import com.example.demo.bucket.BucketName;
import com.example.demo.filestore.FileStore;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
class ProfileService {

    private final FileStore filestore;
    private final ProfileDataAccessService profileDataAccessService;

    @Autowired
    ProfileService(FileStore filestore,
                   ProfileDataAccessService profileDataAccessService) {
        this.filestore = filestore;
        this.profileDataAccessService = profileDataAccessService;
    }

    void uploadProfileImage(UUID profileId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty profile image");
        }

        checkIfImage(file.getContentType());

        Optional<UserProfile> optionalUserProfile = profileDataAccessService.getUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(profileId))
                .findFirst();

        if (optionalUserProfile.isEmpty()) {
            throw new IllegalStateException("User profile not found " + profileId);
        }

        int indexOfUserProfile = profileDataAccessService.getUserProfiles().indexOf(optionalUserProfile.get());
        UserProfile userProfile = profileDataAccessService.getUserProfiles().get(indexOfUserProfile);

        final Map<String, String> metadata = ImmutableMap.<String, String>builder().
                put(Headers.CONTENT_TYPE, String.valueOf(file.getContentType())).
                put(Headers.CONTENT_LENGTH, String.valueOf(file.getSize())).
                build();

        String fileName = String.format("%s-%s", UUID.randomUUID(), file.getOriginalFilename());

        try {
            filestore.save(String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), profileId) , fileName, Optional.of(metadata), file.getInputStream());
            userProfile.setProfileImageKey(fileName);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void checkIfImage(String contentType) {
        if (!Lists.newArrayList("image/jpg", "image/jpeg", "image/ong").contains(contentType.toLowerCase())) {
            throw new IllegalStateException("Image to upload not an image");
        }
    }

    byte[] downloadProfileImage(UUID profileId) {
        Optional<UserProfile> optionalUserProfile = profileDataAccessService.getUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(profileId))
                .findFirst();

        if (optionalUserProfile.isEmpty()){
            throw new IllegalStateException("Cannot find user " + profileId);
        }
        return filestore.download(String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), profileId), optionalUserProfile.get().getProfileImageKey());
    }

    List<UserProfile> getUserProfiles() {
        return profileDataAccessService.getUserProfiles();
    }
}

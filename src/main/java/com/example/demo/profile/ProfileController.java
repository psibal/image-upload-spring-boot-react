package com.example.demo.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/profile")
@CrossOrigin("*")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            path = "{profileId}/image/upload"
    )
    public void uploadProfileImage(@PathVariable("profileId") UUID profileId,
                                    @RequestParam("file") MultipartFile file) {
        profileService.uploadProfileImage(profileId, file);
    }

    @GetMapping("{profileId}/image/download")
    public byte[] downloadProfileImage(@PathVariable("profileId") UUID profileId) {
        return profileService.downloadProfileImage(profileId);
    }

    @GetMapping
    public List<UserProfile> getUserProfiles() {
        return profileService.getUserProfiles();
    }

}

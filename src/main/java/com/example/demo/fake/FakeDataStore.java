package com.example.demo.fake;

import com.example.demo.profile.UserProfile;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeDataStore {
    private final static List<UserProfile> userProfiles = new ArrayList<>();

    static {
        userProfiles.add(new UserProfile(UUID.fromString("68dcb530-bc2e-4892-8704-39b771bafad2"), "Jane", null));
        userProfiles.add(new UserProfile(UUID.fromString("f59f1bcf-6481-496d-9649-3013f00f055b"), "Marco", null));
    }

    public List<UserProfile> getUserProfiles() {
        return Lists.newArrayList(
                userProfiles
        );
    }
}

package com.example.demo.profile;

import com.example.demo.fake.FakeDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfileDataAccessService {

    private final FakeDataStore fakeDataStore;

    @Autowired
    public ProfileDataAccessService(FakeDataStore fakeDataStore) {
        this.fakeDataStore = fakeDataStore;
    }

    List<UserProfile> getUserProfiles() {
        return fakeDataStore.getUserProfiles();
    }
}

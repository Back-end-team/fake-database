package com.backend.fakedb.repositories;

import com.backend.fakedb.entities.UserPreferencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserPreferencesRepository extends JpaRepository<UserPreferencesEntity, Integer> {

    @Modifying
    @Query("UPDATE UserPreferencesEntity u set u.subscribed = ?3 where u.userId = ?1 and u.providerId = ?2")
    void update(Integer userId, Integer providerId, boolean subscribed);
}

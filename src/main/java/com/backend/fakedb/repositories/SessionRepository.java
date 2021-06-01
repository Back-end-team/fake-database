package com.backend.fakedb.repositories;

import com.backend.fakedb.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<SessionEntity, Integer> {
}

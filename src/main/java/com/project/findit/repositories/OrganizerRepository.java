package com.project.findit.repositories;

import com.project.findit.models.OrganizerModel;
import com.project.findit.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganizerRepository extends JpaRepository<OrganizerModel, UUID> {
    Optional<OrganizerModel> findByEmail(String email);
}

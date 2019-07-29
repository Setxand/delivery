package com.delivery.repository;

import com.delivery.model.LoginSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginSessionRepository extends JpaRepository<LoginSession, String> {
}

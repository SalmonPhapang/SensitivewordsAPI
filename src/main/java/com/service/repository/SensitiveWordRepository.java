package com.service.repository;

import com.service.model.SensitiveWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensitiveWordRepository extends JpaRepository<SensitiveWord, Long> {
}

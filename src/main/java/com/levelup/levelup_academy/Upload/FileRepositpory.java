package com.levelup.levelup_academy.Upload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepositpory extends JpaRepository<PdfFile,Long> {
}

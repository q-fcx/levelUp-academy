package com.levelup.levelup_academy.Upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    @Autowired
    private FileRepositpory repository;

    public PdfFile uploadPdf(MultipartFile file) throws IOException {
        PdfFile pdf = new PdfFile();
        pdf.setFileName(file.getOriginalFilename());
        pdf.setContentType(file.getContentType());
        pdf.setData(file.getBytes());
        return repository.save(pdf);
    }

    public PdfFile getPdf(Long id) {
        return repository.findById(id).orElse(null);
    }
}

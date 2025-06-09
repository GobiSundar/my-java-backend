package com.example.demo.controller;

import com.example.demo.model.ServiceContent;
import com.example.demo.repository.ServiceContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/gobi/website_services")

public class ServiceController {

    @Autowired
    private ServiceContentRepository repository;

    @GetMapping
    public List<ServiceContent> getAllServices() {
        return repository.findAll();
    }

    @GetMapping("/{type}")
    public List<ServiceContent> getByType(@PathVariable String type) {
        return repository.findByServiceType(type);
    }

    @PostMapping
    public ServiceContent createService(@RequestBody ServiceContent serviceContent) {
        return repository.save(serviceContent);
    }

    @PutMapping("/{id}")
    public ServiceContent updateService(@PathVariable Long id, @RequestBody ServiceContent updated) {
        updated.setId(id);
        return repository.save(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "http://localhost:8080/uploads/" + filename;
    }
}

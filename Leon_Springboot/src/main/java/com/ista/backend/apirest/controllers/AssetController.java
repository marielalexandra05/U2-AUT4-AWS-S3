package com.ista.backend.apirest.controllers;
import com.ista.backend.apirest.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ista.backend.apirest.model.vm.Asset;
import com.ista.backend.apirest.service.S3Service;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequestMapping("/api/assets")
public class AssetController {
    @Autowired
    private S3Service s3service;

    @PostMapping("/uploads")
    Map<String, String> upload(@RequestParam MultipartFile file){
        String key = s3service.putObject(file);

        Map<String,String> resutl = new HashMap<>();
        resutl.put("key", key);
        resutl.put("url", s3service.getObjectUrl(key));

        return resutl;
    }

    @GetMapping(value = "/get-object", params = "key")
    ResponseEntity<ByteArrayResource> got0nject(@RequestParam String key) {
        Asset asset = s3service.getObject(key);
        ByteArrayResource resource = new ByteArrayResource(asset.getContent());

        return ResponseEntity
                .ok().header("Content-Type", asset.getContentType())
                .contentLength(asset.getContent().length)
                .body(resource);
    }

    @DeleteMapping(value = "/delete-object", params = "key")
    void deLetedbject(@RequestParam String key) {
        s3service.deleteObject(key);

    }
}
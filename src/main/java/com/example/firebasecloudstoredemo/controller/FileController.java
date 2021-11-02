package com.example.firebasecloudstoredemo.controller;

import com.example.firebasecloudstoredemo.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Project firebase-cloudstore-demo
 * @Author mave on 11/1/21
 */
@RestController
@RequestMapping(value = "/profile")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/pic/upload")
    public Object upload(@RequestParam("file") MultipartFile multipartFile) {
        return fileService.upload(multipartFile);
    }

    @PostMapping("/download/{fileName}")
    public Object download(@PathVariable String fileName) throws IOException {
        return fileService.download(fileName);
    }
}

package com.example.firebasecloudstoredemo.service;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @Project firebase-cloudstore-demo
 * @Author mave on 11/1/21
 */


@Service
@RequiredArgsConstructor
public class FileService {

    private static String downloadURI = "gs://cloud-storage-demo-ca14d.appspot.com";
    private static String bucketName = "cloud-storage-demo-ca14d.appspot.com";
    private static String adminSDK = "src/main/resources/cloud-storage-demo-ca14d-firebase-adminsdk-ml6a4-2f6d909528.json";
    private static String downloadPath = "/home/mave/Downloads/";


    public Object upload(MultipartFile multipartFile) {

        try {
            String fileName = multipartFile.getOriginalFilename();
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));

            File file = this.convertToFile(multipartFile, fileName);
            String TEMP_URL = this.uploadFile(file, fileName);
            file.delete();
            return "Successfully Uploaded ! " + TEMP_URL;
        } catch (Exception e) {
            e.printStackTrace();
            return "500" + e + "Unsuccessfully Uploaded!";
        }

    }

    public Object download(String fileName) throws IOException {
        String destFileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
        String destFilePath = downloadPath + destFileName;
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(adminSDK));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        blob.downloadTo(Paths.get(destFilePath));
        return "200" + "Successfully Downloaded!";
    }

    private static String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(adminSDK));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(downloadURI, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private static File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}

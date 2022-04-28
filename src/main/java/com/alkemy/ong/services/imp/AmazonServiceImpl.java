package com.alkemy.ong.services.imp;

import com.alkemy.ong.auth.config.AmazonS3ClientConfig;
import com.alkemy.ong.services.IAmazonService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;


@Service
public class AmazonServiceImpl implements IAmazonService {
    private final AmazonS3ClientConfig amazonS3Client;
    private final AmazonS3 amazonS3;

    private static final String DEFAULT_FILE_NAME = "imgOT186-";
    @Autowired
    public AmazonServiceImpl(AmazonS3ClientConfig amazonS3Client, AmazonS3 amazonS3) {
        this.amazonS3Client = amazonS3Client;
        this.amazonS3 = amazonS3;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String fileName = this.generateFileName();
        this.uploadFileToBucket(fileName, multipartFile.getContentType(), multipartFile.getInputStream());
        return amazonS3.getUrl(amazonS3Client.getBUCKET_NAME(), fileName).toString();
    }

    @Override
    public String deleteFile(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        this.deleteFileToBucket(fileName);
        return "Successfully deleted";
    }

    private void uploadFileToBucket(String fileName, String contentType, InputStream inputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        amazonS3.putObject(new PutObjectRequest(amazonS3Client.getBUCKET_NAME(),fileName,inputStream,objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));

    }

    private String generateFileName() {
        return DEFAULT_FILE_NAME + new Date().getTime();
    }

    private void deleteFileToBucket(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(amazonS3Client.getBUCKET_NAME(), fileName));
    }
}
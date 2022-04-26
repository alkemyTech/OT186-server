package com.alkemy.ong.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface IAmazonService {

    String uploadFile(MultipartFile multipartFile) throws IOException;
    String deleteFile(String fileUrl);


}

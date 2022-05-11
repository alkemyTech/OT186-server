package com.alkemy.ong.auth.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


@Service
public class Base64DecodedMultiPartFile implements MultipartFile {

    private final byte[] imgContent;
    private final String myId;

    public Base64DecodedMultiPartFile(byte[] imgContent) {
        this.imgContent = imgContent;
        this.myId = "ong-alkemy";
    }

    @Override
    public String getName() {
        return myId;
    }

    @Override
    public String getOriginalFilename() {
        return myId + ".jpeg";
    }

    @Override
    public String getContentType() {
        // TODO - implementation depends on your requirements
        return null;
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {

        new FileOutputStream(dest).write(imgContent);

    }
}